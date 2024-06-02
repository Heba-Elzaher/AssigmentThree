package com.example.assigmentthree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicationController {

    @GetMapping("/")
    public String getPage() {
        return "HomePage";
    }

    @GetMapping("/signup")
    public String signup() {
        return "SignUpPage";
    }

    @GetMapping("/login")
    public String login() {
        return "LoginPage";
    }

    @GetMapping("/twoFactorAuthentication")
    public String twoFactorAuthentication(@RequestParam(name = "email") String email, Model model) {
        model.addAttribute("email", email);
        return "TwoFactorAuthPage";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "DashboardPage";
    }

    @GetMapping("/upload")
    public String upload() {
        return "UploadPage";
    }

    @GetMapping("/history")
    public String history() {
        return "HistoryPage";
    }

    @GetMapping("/ai")
    public String ai() {
        return "AiPage";
    }

    @GetMapping("/analysis")
    public String analysis(@RequestParam(name = "id") String id_str, Model model) {
        long id = Long.parseLong(id_str);
        model.addAttribute("id", id);
        return "AnalysisResultPage";
    }
}