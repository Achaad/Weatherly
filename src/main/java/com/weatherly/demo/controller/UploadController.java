package com.weatherly.demo.controller ;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://temporary//";

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes){

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
        String nimi = file.getOriginalFilename();
        String extension = nimi.substring(nimi.lastIndexOf("."));
        System.out.println(extension);
        if(!extension.equals(".jpg") && !extension.equals(".png") && !extension.equals(".gif")){
            redirectAttributes.addFlashAttribute("message", "File is of wrong type, please try again");
            return "redirect:uploadStatus";
        }
        try {
            // Get the file and save it somewhere
            System.out.println(nimi);
            Path path = Paths.get(UPLOADED_FOLDER + nimi.substring(nimi.lastIndexOf("\\")));
            file.transferTo(path.toFile());

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
        }
        catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "There was a problem with uploading your file");
            return "redirect:uploadStatus";
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}