package com.github.ildus_akhmadiev.audiocomparison.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class AudioController {
    @GetMapping("")
    public String showMain() {
        return "Main";
    }

}
