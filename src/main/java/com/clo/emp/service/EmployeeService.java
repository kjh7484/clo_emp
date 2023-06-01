package com.clo.emp.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import com.clo.emp.mapper.EmployeeMapper;
import com.clo.emp.model.Employee;

@Service
public class EmployeeService {

	@Autowired
	public EmployeeMapper employeeMapper;
	
	//전체직원수 조회
	public int getEmployeeCount() {
        return employeeMapper.getEmployeeCount();
    }
	
	//직원수 조회(name)
	public int getEmployeeNameCount() {
        return employeeMapper.getEmployeeNameCount();
    }
	
	//페이지 표시할 직원 조회
	public List<Employee> getEmployeesByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return employeeMapper.getEmployeesByPage(offset, pageSize);
    }

	//직원 조회(name)
	public List<Employee> getEmployeeByName(String name) {
        return employeeMapper.getEmployeeByName(name);
    }
	
	//직원 등록
	public void addEmployees(List<Employee> employees) {
        employeeMapper.addEmployees(employees);
    }
	
}
