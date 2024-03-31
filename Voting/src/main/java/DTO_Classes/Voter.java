package DTO_Classes;

import java.sql.Date;

import Common.Gender;
import Common.Role;
import Common.User;

public class Voter extends User {

	private int voterId;
	private String phone;
	private int organizationId;
	private Date dob;
	private Gender gender;

	public Voter(String phone,int organizationId,int voterId, String name, String email, Date dob,Gender gender, String password,String Organization,Role role) {
		super(name, email, password, role);
		this.organizationId = organizationId;
		this.phone = phone;
		this.voterId = voterId;
		this.dob = dob;
		this.gender = gender;
	}

	public int getVoterId() {
		return voterId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public void setVoterId(int voterId) {
		this.voterId = voterId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}