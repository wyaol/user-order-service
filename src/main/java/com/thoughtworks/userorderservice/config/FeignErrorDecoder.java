package com.thoughtworks.userorderservice.config;

import com.thoughtworks.userorderservice.exception.ThirdServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return new ThirdServiceException();
    }
}
