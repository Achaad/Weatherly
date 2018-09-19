package com.weatherly.demo.services;

import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;





public class XML {

    /*
    //Klass mis sisaldab abifunktsioone XML andmete töötlemiseks
     *Kasutame XML andmete lugemiseks DOM
     */

    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document document;


    public XML(URL inputURL) {

        super();


        dbFactory = DocumentBuilderFactory.newInstance();
        try {

            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(inputURL.openStream());

        } catch (Exception e) {

            StackTraceElement[] exception = e.getStackTrace();
        }
    }


    //<temperatuur>45</temperatuur>     korral tagastatakse 45.
    public String getUnNestedTagContent(String tagName) {

        NodeList nList = document.getElementsByTagName(tagName);
        Node nNode = nList.item(0);
        return nNode.getTextContent();


    }


    //<temperatuur v��rtus="45"/>        korral tagastatakse 45, kus temperatuur on "tagName" ja v��rtus on "attributeName".
    public String getTagContentValue(String tagName, String attributeName) {

        NodeList nList = document.getElementsByTagName(tagName);

        Node nNode = nList.item(0);
        Element e = (Element)nNode;

        return e.getAttribute(attributeName);
    }

    //Tagastab massiivi kõikide tagide attribuutide väärtustega.
    public String[] getAllTagContentValues(String tagName, String attributeName) {

        ArrayList<String> allContentValues = new ArrayList<>();

        NodeList nList = document.getElementsByTagName(tagName);

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            Element e = (Element)nNode;

            allContentValues.add(e.getAttribute(attributeName));
        }

        return allContentValues.toArray(new String[allContentValues.size()]);
    }

    //Tagastab massiivi mingite kindlate attribuut väärtustega elementidest.
    public String[] getAllTagContentValues(String tagName, String attributeName,
                                           String limitingAttribute, String limitingValue) {

        ArrayList<String> allContentValues = new ArrayList<>();

        NodeList nList = document.getElementsByTagName(tagName);

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            Element e = (Element)nNode;

            if(e.getAttribute(limitingAttribute).equals(limitingValue)) {
                allContentValues.add(e.getAttribute(attributeName));
            }

        }

        return allContentValues.toArray(new String[allContentValues.size()]);
    }

}