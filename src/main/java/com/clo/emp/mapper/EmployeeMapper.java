package com.clo.emp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.clo.emp.model.Employee;

@Mapper
public interface EmployeeMapper {
	//전체직원수 조회
	int getEmployeeCount();
	
	//직원수 조회(name)
	int getEmployeeNameCount();
	
	//페이지 표시할 직원 조회
	List<Employee> getEmployeesByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);
	
	//직원 조회(name)
	List<Employee> getEmployeeByName(@Param("name") String name);
	
	//직원 등록
	int addEmployees(List<Employee> employees);
}
