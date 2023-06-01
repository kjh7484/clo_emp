package com.clo.emp.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clo.emp.model.Employee;
import com.clo.emp.service.EmployeeService;

@Controller
@RequestMapping("/api/employee")
public class EmployeeController {
	
	private static final int PAGE_SIZE = 10; // 페이지 당 표시할 직원 수
	
	private final EmployeeService employeeService;
	
	 private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
	
    //직원전체조회
	@GetMapping
    public String getAllEmployees(@RequestParam(defaultValue = "1") int page, Model model) {
		logger.info("getAllEmployees START");
		int totalCount = employeeService.getEmployeeCount();
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);
        int startPage = Math.max(1, page - 5);
        int endPage = Math.min(startPage + 9, totalPages);
        
        List<Employee> employees = employeeService.getEmployeesByPage(page, PAGE_SIZE);
        
        model.addAttribute("employees", employees);
        model.addAttribute("currentPage", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);
        logger.info("getAllEmployees END");
        return "employee";
    }
	
	//직원조회(name)
	@GetMapping("/{name}")
    public String getEmployeeByName(@PathVariable String name, Model model) {
		logger.info("getEmployeeByName START");
		List<Employee> employees = employeeService.getEmployeeByName(name);
		model.addAttribute("employees", employees);
		logger.info("getEmployeeByName END");
        return "employeeNm";
    }
	
	//직원등록
	@PostMapping
    public ResponseEntity<String> addEmployee(@RequestParam(required = false) MultipartFile file, @RequestBody(required = false) String requestBody) {
        try {
        	logger.info("addEmployee START");
            if (file != null && !file.isEmpty()) {
                String filename = file.getOriginalFilename();
                if (filename != null && (filename.endsWith(".json") || filename.endsWith(".JSON"))) {
                    // Handle JSON file
                	logger.info("Handle JSON file");
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Employee> employees = objectMapper.readValue(file.getInputStream(), new TypeReference<List<Employee>>() {});
                    logger.info("employees size : "+ employees.size());
                    employeeService.addEmployees(employees);
                } else if (filename != null && (filename.endsWith(".csv") || filename.endsWith(".CSV"))) {
                    // Handle CSV file
                	logger.info("Handle CSV file");
                    List<Employee> employees = readEmployeesFromCsv(file.getInputStream());
                    employeeService.addEmployees(employees);
                } else {
                    return ResponseEntity.badRequest().body("Unsupported file format");
                }
            } else {
                // Handle JSON or CSV data in request body
            	logger.info("Handle JSON or CSV data in request body");
                if (requestBody.startsWith("{") || requestBody.startsWith("[")) {
                    // JSON format
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Employee> employees = objectMapper.readValue(requestBody, new TypeReference<List<Employee>>() {});
                    employeeService.addEmployees(employees);
                } else {
                    // CSV format
                    List<Employee> employees = readEmployeesFromCsv(new ByteArrayInputStream(requestBody.getBytes()));
                    employeeService.addEmployees(employees);
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("Success to add employee");
        } catch (Exception e) {
        	logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add employee");
        } finally {
        	logger.info("addEmployee END");
		}
    }

	//CSV 파일처리
    private List<Employee> readEmployeesFromCsv(InputStream inputStream) throws IOException {
        List<Employee> employeeList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        	String line;
            while ((line = reader.readLine()) != null) {
            	String[] fields = line.split(",");
                if (fields.length == 4) {
                    String name = fields[0].trim();
                    String email = fields[1].trim();
                    String tel = fields[2].trim();
                    String joined = fields[3].trim();

                    Employee employee = new Employee(name, email, tel, joined);
                    employeeList.add(employee);
                }
            }
        }

        return employeeList;
    }
}
