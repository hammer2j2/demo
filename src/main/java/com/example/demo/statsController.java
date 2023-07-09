package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.demo.service.StatsService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class StatsController {
    @Autowired
    private final StatsService statsService; 


    public StatsController(StatsService statsService){
        this.statsService = statsService;
    }

    @GetMapping(value="/stats", produces="text/plain")
    public String getStats(@RequestParam(name="bucketName") String bucketName) {
        // return "This will be a listing from S3 or DynamoDB";
        return this.statsService.getS3Bucket(bucketName);
    }


}