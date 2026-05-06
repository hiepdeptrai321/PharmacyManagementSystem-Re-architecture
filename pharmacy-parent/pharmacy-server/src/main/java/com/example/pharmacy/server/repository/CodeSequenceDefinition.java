package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.enums.BusinessCodeType;

public record CodeSequenceDefinition(
        BusinessCodeType codeType,
        String prefix,
        long currentValue,
        int padding
) {
}
