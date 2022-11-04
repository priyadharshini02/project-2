package com.priya.project2;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="User")
public class User {

	@NotEmpty(message="first name cannot be empty")
	@NotNull(message="first name cannot be empty")
	private String firstName;
	private String lastName;
	@Email(message="please enter a valid email")
	@NotNull(message="email cannot be empty")
	@Id
	@NotEmpty(message="email cannot be empty")
	private String email;
	private long phone;
	@NotNull
	@Size(min=8, message="password must contain atleast 8 characters")
	private String password;
	@NotNull
	private String confPassword;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String firstName, String lastName, String email, long phone, String password, String confPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.confPassword = confPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfPassword() {
		return confPassword;
	}

	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
	}
	
	
	
	
}
