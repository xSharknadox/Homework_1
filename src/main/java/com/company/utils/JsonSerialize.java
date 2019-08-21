package com.company.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.io.File;
import java.io.IOException;

public class JsonSerialize {
    public static String objectToJSON(Object object, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.setVisibility(mapper.getVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        String data = "";
        try {
            data = mapper.writeValueAsString(object);
            mapper.writeValue(new File(filePath), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static <T> T jsonToObject(String filePath, Class<T> classType) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);
        try {
            return mapper.readValue(file, classType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
