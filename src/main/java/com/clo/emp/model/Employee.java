package com.clo.emp.model;

public class Employee {
	private Long id;
    private String name;
    private String email;
    private String tel;
    private String joined;
    
    public Employee() {
    }
    
    public Employee(String name, String email, String tel, String joined) {
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.joined = joined;
    }
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getJoined() {
		return joined;
	}
	public void setJoined(String joined) {
		this.joined = joined;
	}
}
