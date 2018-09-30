package com.weatherly.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableOAuth2Client
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  @Qualifier("oauth2ClientContext")
  private OAuth2ClientContext oauth2ClientContext;

  @Bean
  @ConfigurationProperties("security.oauth2.google.client")
  public AuthorizationCodeResourceDetails google() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @ConfigurationProperties("security.oauth2.google.resource")
  public ResourceServerProperties googleResource() {
    return new ResourceServerProperties();
  }

  private Filter ssoGoogleFilter() {
    OAuth2ClientAuthenticationProcessingFilter googleFilter =
        new OAuth2ClientAuthenticationProcessingFilter("/login/google");
    OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
    googleFilter.setRestTemplate(googleTemplate);
    googleFilter.setTokenServices(new UserInfoTokenServices(googleResource().getUserInfoUri(),
        google().getClientId()));
    return googleFilter;
  }

  @Bean
  @ConfigurationProperties("security.oauth2.facebook.client")
  public AuthorizationCodeResourceDetails facebook() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @ConfigurationProperties("security.oauth2.facebook.resource")
  public ResourceServerProperties facebookResource() {
    return new ResourceServerProperties();
  }

  private Filter ssoFacebookFilter() {
    OAuth2ClientAuthenticationProcessingFilter facebookFilter =
        new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
    OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
    facebookFilter.setRestTemplate(facebookTemplate);
    facebookFilter.setTokenServices(new UserInfoTokenServices(facebookResource().getUserInfoUri()
        , facebook().getClientId()));
    return facebookFilter;
  }

  private Filter ssoFilter() {
    List<Filter> filters = new ArrayList<>();
    filters.add(ssoGoogleFilter());
    filters.add(ssoFacebookFilter());

    CompositeFilter filter = new CompositeFilter();
    filter.setFilters(filters);
    return filter;
  }

  @Bean
  public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(filter);
    registration.setOrder(-100);
    return registration;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/**").addFilterBefore(ssoFilter(),
        BasicAuthenticationFilter.class)
        .authorizeRequests()
          .antMatchers("/user").authenticated()
        .and().authorizeRequests().anyRequest().permitAll()
          .and().formLogin().loginPage("/login").and().logout().logoutSuccessUrl("/").permitAll().and()
        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }
}
