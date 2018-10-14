package com.silence.mini.program.life.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 自定义ObjectMapper
 */
public class MyObjectMapper extends ObjectMapper {

    public MyObjectMapper() {
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
