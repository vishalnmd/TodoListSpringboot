//package com.springboot.login.stub;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class DeleteMe implements CommandLineRunner {
//
//    Logger logger = LoggerFactory.getLogger(DeleteMe.class);
//
//    @Value("${spring.datasource.username}")
//    private String url;
//
//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//
//    public static void main(String args[]){
//        SpringApplication.run(DeleteMe.class,args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println(url);
//        logger.info(url);
//        logger.info(dbUrl);
//    }
//}
