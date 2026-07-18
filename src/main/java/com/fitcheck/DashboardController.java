package com.fitcheck;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class DashboardController {

    private final UploadedImageRepository imageRepository;

    public DashboardController(UploadedImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        String email = principal.getName();
        model.addAttribute("email", email);
        model.addAttribute("personPhotos", imageRepository.findByOwnerEmailAndType(email, "PERSON"));
        model.addAttribute("clothingPhotos", imageRepository.findByOwnerEmailAndType(email, "CLOTHING"));
        return "dashboard";
    }
}