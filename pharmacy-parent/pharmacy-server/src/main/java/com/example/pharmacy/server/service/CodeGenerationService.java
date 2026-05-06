package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;

public interface CodeGenerationService {
    String nextCode(BusinessCodeType codeType);
}
