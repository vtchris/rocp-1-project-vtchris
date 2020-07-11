package com.changeBank.repo;
/**
 * 
 * @author vtchr
 *
 */
public class UserLoginDTO {
	
	private String username;
	private String password;
			
	public UserLoginDTO() {
		super();
	}	
	public UserLoginDTO(String username, String password) {
		super();
		this.username = username.toLowerCase();
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
