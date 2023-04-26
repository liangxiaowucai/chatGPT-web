package com.chat.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class ChatGptDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatGptDemoApplication.class, args);
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:index.html";
    }
}
