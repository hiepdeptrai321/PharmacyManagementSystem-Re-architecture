package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.CreateEmployeeRequest;

public interface EmployeeManagementService {
    String createEmployee(CreateEmployeeRequest request, UserContext actor);
}
