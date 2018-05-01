package com.prs.business.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	@Column(name = "isReviewer")
	private Boolean reviewer;
	@Column(name = "isAdmin")
	private Boolean admin;

	public User() {

		userName = "";
		password = "";
	}

	public User(int iD, String userName, String password, String firstName, String lastName, String phoneNumber,
			String email, Boolean reviewer, Boolean admin) {
		super();
		id = iD;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.reviewer = reviewer;
		this.admin = admin;
	}

	public User(String un, String pw, String fn, String ln, String pn, String e, Boolean m, Boolean a) {
		setUserName(un);
		setPassword(pw);
		setFirstName(fn);
		setLastName(ln);
		setPhoneNumber(pn);
		setEmail(e);
		setReviewer(m);
		setAdmin(a);

	}

	public int getId() {
		return id;
	}

	public void setId(int iD) {
		id = iD;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phone) {
		this.phoneNumber = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getReviewer() {
		return reviewer;
	}

	public void setReviewer(Boolean reviewer) {
		this.reviewer = reviewer;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", email=" + email + ", reviewer=" + reviewer
				+ ", admin=" + admin + "]";
	}

}
