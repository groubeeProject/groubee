package com.gig.groubee.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping("login")
    public String login() {
        return "login";
    }
}
