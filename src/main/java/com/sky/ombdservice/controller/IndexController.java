package com.sky.ombdservice.controller;


import com.sky.ombdservice.constant.Path;
import com.sky.ombdservice.constant.Template;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = { Path.INDEX, "/" })
    public String indexHandler() {
        return Template.INDEX;
    }

}
