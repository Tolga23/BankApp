package com.thardal.bankapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thardal.bankapp.global.dto.RestResponse;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest {

    protected
    ObjectMapper objectMapper;

    protected RestResponse getRestResponse(MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RestResponse.class);
    }

    protected static boolean isSuccess(RestResponse restResponse) {
        boolean isSuccess = restResponse.isSuccess();
        return isSuccess;
    }

    protected boolean isSuccess(MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        RestResponse restResponse = getRestResponse(mvcResult);

        return isSuccess(restResponse);
    }

}
