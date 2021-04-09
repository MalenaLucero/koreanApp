package com.koreanApp.payload;

import java.util.Set;

public class SetRolesRequest {
	private String username;
	private Set<String> roles;
	
	public SetRolesRequest() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}
