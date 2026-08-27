package com.example.velora_ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiscPageController {
    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/support")
    public String supportPage() {
        return "support";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/privacy")
    public String privacyPage() {
        return "privacy";
    }

    @GetMapping("/terms")
    public String termsPage() {
        return "terms";
    }
}
