package com.example.ncpl_dem0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/main")
public class NcplDem0Application {
    //localhost:9988/main/home
    public static void main(String[] args) {
        SpringApplication.run(NcplDem0Application.class, args);
    }

    @GetMapping("/home")
    public String homePage() {
        return "sdlfhvaspdiuhfpo'[jfcdausidfsjdlfcsap]jfsdpjfsdf";
    }

    @GetMapping("/demo1")
    public String demo(){
        return "Version:2.0";
    }

    @GetMapping("/demo/home")
    public String demo1(){
        return "Version:3.0";
    }
}
