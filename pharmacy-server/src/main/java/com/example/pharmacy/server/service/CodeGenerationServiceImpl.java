package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.common.exception.BusinessException;
import com.example.pharmacy.server.repository.CodeSequenceDefinition;
import com.example.pharmacy.server.repository.CodeSequenceRepository;
import com.example.pharmacy.server.transaction.TransactionManager;

import java.util.Objects;

public class CodeGenerationServiceImpl implements CodeGenerationService {
    private final CodeSequenceRepository codeSequenceRepository;
    private final TransactionManager transactionManager;

    public CodeGenerationServiceImpl(CodeSequenceRepository codeSequenceRepository, TransactionManager transactionManager) {
        this.codeSequenceRepository = Objects.requireNonNull(codeSequenceRepository, "codeSequenceRepository must not be null");
        this.transactionManager = Objects.requireNonNull(transactionManager, "transactionManager must not be null");
    }

    @Override
    public String nextCode(BusinessCodeType codeType) {
        return transactionManager.execute(() -> {
            CodeSequenceDefinition definition = codeSequenceRepository.lockByCodeType(codeType)
                    .orElseThrow(() -> new BusinessException("Chua co code_sequence cho " + codeType.getCodeType()));
            long nextValue = definition.currentValue() + 1;
            codeSequenceRepository.updateCurrentValue(codeType, nextValue);
            return definition.prefix() + String.format("%0" + definition.padding() + "d", nextValue);
        });
    }

    @Override
    public void syncIfBehind(BusinessCodeType codeType, long maxKnownValue) {
        transactionManager.execute(() -> {
            codeSequenceRepository.lockByCodeType(codeType).ifPresent(def -> {
                if (def.currentValue() < maxKnownValue) {
                    codeSequenceRepository.updateCurrentValue(codeType, maxKnownValue);
                }
            });
            return null;
        });
    }
}
