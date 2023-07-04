package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class statsController {
    @GetMapping("/stats")
    public String index() {
        return "This will be a list from S3 or DynamoDB";
    }
}