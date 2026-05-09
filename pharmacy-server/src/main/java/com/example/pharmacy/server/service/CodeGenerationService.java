package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;

public interface CodeGenerationService {
    String nextCode(BusinessCodeType codeType);

    void syncIfBehind(BusinessCodeType codeType, long maxKnownValue);
}
