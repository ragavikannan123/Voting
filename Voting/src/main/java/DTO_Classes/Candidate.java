package DTO_Classes;

import java.sql.Date;

import Common.Gender;
import Common.Role;

public class Candidate extends Voter {

	private int candidateId;
	private int electionId;
	private byte[] logo;
	private int votes;

	public Candidate(String phone, int organizationId, int voterId, String name, String email, Date dob, Gender gender,
			String password, String Organization, Role role, int candidateId, int electionId,
			byte[] logo) {
		super(phone, organizationId, voterId, name, email, dob, gender, password, Organization, role);
		this.candidateId = candidateId;
		this.electionId = electionId;
		this.logo = logo;
	}
	
	
	public int getElectionId() {
		return electionId;
	}


	public void setElectionId(int electionId) {
		this.electionId = electionId;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public int getVotes() {
		return votes;
	}

	public void addVotes(int votes) {
		this.votes = votes;
	}
}
