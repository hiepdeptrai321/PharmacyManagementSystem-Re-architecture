package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.enums.BusinessCodeType;

import java.util.Optional;

public interface CodeSequenceRepository {
    Optional<CodeSequenceDefinition> lockByCodeType(BusinessCodeType codeType);

    void updateCurrentValue(BusinessCodeType codeType, long currentValue);
}
