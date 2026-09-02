package com.example.velora_ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiscPageController {
    @GetMapping("/deals")
    public String dealsPage() {
        return "deals";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/support")
    public String supportPage() {
        return "support";
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
