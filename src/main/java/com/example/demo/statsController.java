package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.service.StatsService;

@RestController
public class StatsController {
    @Autowired
    private final StatsService statsService; 


    public StatsController(StatsService statsService){
        this.statsService = statsService;
    }

    @GetMapping("/stats")
    public String index() {
        // return "This will be a list from S3 or DynamoDB";
        // TODO: get s3 bucketname from query param, bucketName
        final String bucketName = "slamgarden.com";
        return this.statsService.getS3Bucket(bucketName);
    }
}