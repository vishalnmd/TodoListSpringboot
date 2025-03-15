package com.springboot.login.stub;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeleteMe {
    public static void main(String args[]){
        String req = "{\"request\":\"{\\\"id\\\":1,\\\"task\\\":\\\"play cricket\\\"}\"}";

        try{

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(req);
            JsonNode jsonNode1 = objectMapper.readTree(jsonNode.get("request").asText());

            System.out.println(jsonNode.get("request").asText());
            System.out.println(jsonNode1.get("task").asText());

        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
