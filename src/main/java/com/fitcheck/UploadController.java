package com.fitcheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

@Controller
public class UploadController {

    private final UploadedImageRepository imageRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public UploadController(UploadedImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleUpload(@RequestParam MultipartFile file,
                               @RequestParam String type,
                               Principal principal) throws IOException {

        if (file.isEmpty()) {
            return "redirect:/upload?error";
        }

        Path dir = Paths.get(uploadDir);
        Files.createDirectories(dir);

        String original = file.getOriginalFilename();
        String extension = "";
        if (original != null && original.contains(".")) {
            extension = original.substring(original.lastIndexOf("."));
        }
        String storedName = UUID.randomUUID() + extension;

        Files.copy(file.getInputStream(), dir.resolve(storedName));

        UploadedImage image = new UploadedImage();
        image.setFileName(storedName);
        image.setOriginalName(original);
        image.setType(type);
        image.setOwnerEmail(principal.getName());
        imageRepository.save(image);

        return "redirect:/dashboard";
    }
}