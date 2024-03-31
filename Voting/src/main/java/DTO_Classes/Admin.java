package DTO_Classes;

import Common.Role;
import Common.User;

public class Admin extends User {

	private int adminId;
	private Organization organization;

	public Admin(String name, String email,String password,int adminId, Organization organization) {
		super(name, email, password,Role.ADMIN);
		this.adminId = adminId;
		this.organization = organization;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}
