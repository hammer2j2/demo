package com.example.demo.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;
import software.amazon.awssdk.services.s3.model.S3Object;
import java.util.List;

@Service
public class StatsService {
    ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
    final Region region = Region.US_WEST_2;

    S3Client s3 = S3Client.builder()
            .region(region)
            .credentialsProvider(credentialsProvider)
            .build();
    
    public String getS3Bucket(String bucketName){
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        s3 = S3Client.builder()
             .region(this.region)
             .credentialsProvider(credentialsProvider)
             .build();
        // List buckets
        // String bucketName = "slamgarden.com";

        return listBucketObjects(s3, bucketName);
    } 

    public static String listBucketObjects(S3Client s3, String bucketName ) {

        String output = "Going to get bucket list for " + bucketName + ".<br><br>";
        output += "<ul>";
        try {
            ListObjectsV2Request listReq = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .maxKeys(10)
                .delimiter("/")
                .prefix("")
                .build();
            System.out.println("Got listReq");
            ListObjectsV2Iterable responses = s3.listObjectsV2Paginator(listReq);
            System.out.println("Got responses");
            for (software.amazon.awssdk.services.s3.model.ListObjectsV2Response response : responses) {
                System.out.println("Getting each response");
                for(S3Object item : response.contents() ) {
                    System.out.println("Getting each key: " + item.key() +  "   " + item.size());
                    output += "<li>" + item.key() + "...." + item.size() + " bytes</li>\n";
                }
            }
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return(output + "\n" + e.awsErrorDetails().errorMessage());
        }
        output += "</ul>";
        return output;
    }

    //convert bytes to kbs.
    private static long calKb(Long val) {
        return val/1024;
    }


}