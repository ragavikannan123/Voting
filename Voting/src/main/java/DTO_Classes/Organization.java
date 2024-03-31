package DTO_Classes;

import java.util.ArrayList;

public class Organization {

	private String name;
	private int organizationId;
	private ArrayList<Election> elections;

	public Organization(String name, int organizationId) {
		this.name = name;
		this.organizationId = organizationId;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Election> getElections() {
		return elections;
	}

	public void addElection(Election election) {
		this.elections.add(election);
	}

	public void removeElection(Election election) {
		this.elections.remove(election);
	}
}
