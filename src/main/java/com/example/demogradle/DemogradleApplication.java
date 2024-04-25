package com.example.demogradle;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.securitytoken.internal.STSProfileCredentialsService;

import java.util.List;

@SpringBootApplication
@RestController
public class DemogradleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemogradleApplication.class, args);
    }

    @GetMapping("/")
    public String hello(){

        /*Step 1 : Direct Method*/
        AWSCredentials credentials = new BasicAWSCredentials(
                "ASIAZQ3DODWCFWT5P27E",
                "3AR0SP9S8EJUC2Glgco+cnc5esdA3tG2YfZg+zz/"
        );

        //Step 2:  Specify the profile to use and create an S3 client
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider("ProjectIAMProfile");
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_WEST_2) // Update the region appropriately
                .build();

        // Example: Listing all S3 buckets using the specified profile
        System.out.println("==========Your Amazon S3 buckets are:===========");
        System.out.println("   ,.--'`````'--., \n" +
                "    (\\'-.,_____,.-'/)\n" +
                "     \\\\-.,_____,.-//\n" +
                "     ;\\\\         //|\n" +
                "     | \\\\  ___  // |\n" +
                "     |  '-[___]-'  |\n" +
                "     |             |\n" +
                "     |             |\n" +
                "     |             |\n" +
                "    `'-.,_____,.-''");

        System.out.print("      ___________\n" +
                "     /=//==//=/  \\\n" +
                "    |=||==||=|    |\n" +
                "    |=||==||=|~-, |\n" +
                "    |=||==||=|^.`;|\n" +
                "     \\=\\\\==\\\\=\\`=.:\n" +
                "      `\"\"\"\"\"\"\"`^-,`.\n" +
                "               `.~,'\n" +
                "              ',~^:,\n" +
                "              `.^;`.\n" +
                "               ^-.~=;.\n" +
                "                  `.^.:`.");
        s3Client.listBuckets().forEach((bucket) -> {
            System.out.println("\n* " + bucket.getName() + "::owned by -> " + bucket.getOwner().getDisplayName());
        });

        /*final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_2)
                .build();
        List<Bucket> buckets = s3.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
        }*/
        return "Hello";
    }
}
