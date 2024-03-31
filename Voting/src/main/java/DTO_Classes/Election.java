package DTO_Classes;

import java.sql.Date;
import java.util.ArrayList;

public class Election {

	private int electionId;
	private String name;
	private Date startDate;
	private Date endDate;
	private ArrayList<Voter> voters;
	private ArrayList<Candidate> candidates;

	public Election(String name, Date startDate, Date endDate, int electionId) {
		this.electionId = electionId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getElectionId() {
		return electionId;
	}

	public void setElectionId(int electionId) {
		this.electionId = electionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ArrayList<Voter> getVoters() {
		return voters;
	}

	public void addVoters(Voter voter) {
		this.voters.add(voter);
	}

	public void removeVoters(Voter voter) {
		this.voters.remove(voter);
	}

	public ArrayList<Candidate> getCandidates() {
		return candidates;
	}

	public void addCandidate(Candidate candidate) {
		this.candidates.add(candidate);
	}

	public void removeCandidate(Candidate candidate) {
		this.candidates.remove(candidate);
	}
}
