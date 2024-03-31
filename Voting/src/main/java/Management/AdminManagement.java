package Management;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Common.Voting_DB;


// Admin management class

public class AdminManagement implements Management{
	
	// For election management
	Voting_DB db = Voting_DB.getDB();
	Connection connection = db.getConnection();
	
	@Override
	public JSONObject viewProfile(int id) throws SQLException, JSONException {
		
		JSONObject json = new JSONObject();
		String query = "select * from Users join Admin on Admin.user_id = Users.id where Users.id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setInt(1, id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while (resultSet.next()) {
            json.put("name", resultSet.getString("name"));
    	    json.put("email", resultSet.getString("email"));
    	    json.put("role", resultSet.getString("role"));
    	    json.put("photo", resultSet.getString("photo"));
        }
	    return json;
	
	}
	
	public String addElection(int orgId, String name, String description, Date startDate, Date endDate) throws SQLException {
	    String query = "SELECT * FROM Elections WHERE name = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setString(1, name);
	    ResultSet resultSet = preparedStatement.executeQuery();

	    if (resultSet.next()) {
	        return "Election already exists";
	    }

	    query = "INSERT INTO Elections (name, organization_id, start_date, end_date, description) VALUES (?, ?, ?, ?, ?)";
	    preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setString(1, name);
	    preparedStatement.setInt(2, orgId);
	    preparedStatement.setDate(3, startDate);
	    preparedStatement.setDate(4, endDate);
	    preparedStatement.setString(5, description);
	    int affectedRows = preparedStatement.executeUpdate();

	    if (affectedRows > 0) {
	        return "Election added successfully";
	    }

	    return "Failed to add Election";
	}

	@Override
	public JSONArray getElections(int orgId) throws SQLException, JSONException {
		
		JSONArray jsonArray = new JSONArray();
		
		String query = "select * from Elections where organization_id = ?";
		PreparedStatement preparedStatement = connection.prepareCall(query);
		preparedStatement.setInt(1, orgId);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",resultSet.getInt("id"));
			jsonObject.put("name",resultSet.getString("name"));
			jsonObject.put("start_date",resultSet.getDate("start_date"));
			jsonObject.put("end_date",resultSet.getDate("end_date"));
			jsonObject.put("description",resultSet.getString("description"));
			
			jsonArray.put(jsonObject);
		}
		
		return jsonArray;
	
	}
	
	public boolean removeElection(int elecId) throws SQLException {
		
		String query = "delete from Elections where id = ?";
		PreparedStatement preparedStatement= connection.prepareStatement(query);
		preparedStatement.setInt(1, elecId);
		int affectedRows = preparedStatement.executeUpdate();
		
		return affectedRows > 0;
	
	}
	
	public boolean editElection(String name, String description, Date endDate, Date startDate, int id) throws SQLException {
	
		String query = "update Elections set name=?, start_date=?, end_date=?, description=? where id = ?"; 
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, name);
		preparedStatement.setDate(1, startDate);
		preparedStatement.setDate(3, endDate);
		preparedStatement.setString(4, description);
		preparedStatement.setInt(5, id);
		int affectedRows = preparedStatement.executeUpdate();
		
		return affectedRows > 0;
	
	}
	
	// For applicants
	
	public JSONArray getApplicants(int orgId) throws SQLException, JSONException {
		
		String query = "select Apply_Candidate.*, Voters.*, Users.*, Elections.name from Apply_Candidate join Elections on Elections.id = Apply_Candidate.election_id join Voters on Apply_Candidate.voter_id = Voters.id join Users on Users.id = Voters.user_id where Elections.organization_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, orgId);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		JSONArray jsonArray = new JSONArray();
		while(resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("apply_id",resultSet.getInt("Apply_Candidate.id"));
			jsonObject.put("name",resultSet.getString("Users.name"));
			jsonObject.put("email",resultSet.getString("Users.email"));
			jsonObject.put("dob",resultSet.getDate("Voters.dob"));
			jsonObject.put("gender",resultSet.getString("Voters.gender"));
			jsonObject.put("phone",resultSet.getString("Voters.phone"));
			jsonObject.put("photo",resultSet.getString("Users.photo"));
			jsonObject.put("election",resultSet.getString("Elections.name"));
			jsonObject.put("logo",resultSet.getString("Apply_Candidate.logo"));
			
			jsonArray.put(jsonObject);
		}
		return jsonArray;
	
	}
	
	public boolean removeApplicant(int applicantId) throws SQLException {
	
		String query = "delete from Apply_Candidate where id = ?";
		PreparedStatement preparedStatement= connection.prepareStatement(query);
		preparedStatement.setInt(1, applicantId);
		int affectedRows = preparedStatement.executeUpdate();
		
		return affectedRows > 0;
	
	}
	
	
	// For Candidate
	
	public String addCandidate(int applyId) throws SQLException {
		
		String query = "select * from Candidates where apply_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, applyId);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			return "Candidate already exits";
		}
		
		query = "insert into Candidates (apply_id) values (?)";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, applyId);
		int affectedRows = preparedStatement.executeUpdate();
		
		if(affectedRows > 0) {
			return "Candidate added successfully";
		}
		return "Failed to add candidate";
	
	}
	
	public boolean removeCandidate(int candidateId) throws SQLException {
		
		String query = "delete from Candidates where id = ?";
		PreparedStatement preparedStatement= connection.prepareStatement(query);
		preparedStatement.setInt(1, candidateId);
		int affectedRows = preparedStatement.executeUpdate();
		
		return affectedRows > 0;
	
	}
	
	@Override
	public JSONArray getCandidates(int elecId) throws SQLException, JSONException {
	
	    String query1 = "select Apply_Candidate.*, Voters.*, Elections.name as election_name, Users.photo, Candidates.id as candidate_id from Candidates join Apply_Candidate on Apply_Candidate.id = Candidates.apply_id join Elections on Elections.id = Apply_Candidate.election_id join Voters on Apply_Candidate.voter_id = Voters.id join Users on Users.id = Voters.user_id where Elections.id = ?";
	    PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
	    preparedStatement1.setInt(1, elecId);
	    ResultSet resultSet1 = preparedStatement1.executeQuery();

	    JSONArray jsonArray = new JSONArray();

	    while (resultSet1.next()) {
	        JSONObject jsonObject = new JSONObject();
	        int candidateId = resultSet1.getInt("candidate_id");
	        jsonObject.put("id", candidateId);
	        jsonObject.put("voter_name", resultSet1.getString("Users.name"));
	        jsonObject.put("email", resultSet1.getString("Users.email"));
	        jsonObject.put("dob", resultSet1.getDate("Voters.dob"));
	        jsonObject.put("gender", resultSet1.getString("Voters.gender"));
	        jsonObject.put("phone", resultSet1.getString("Voters.phone"));
	        jsonObject.put("photo", resultSet1.getString("Users.photo"));
	        jsonObject.put("election_name", resultSet1.getString("election_name"));
	        jsonObject.put("logo", resultSet1.getString("Apply_Candidate.logo"));

	        String query2 = "select count(*) as vote_count from Votes where candidate_id = ? and election_id = ?";
	        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
	        preparedStatement2.setInt(1, candidateId);
	        preparedStatement2.setInt(2, elecId);
	        ResultSet resultSet2 = preparedStatement2.executeQuery();

	        if (resultSet2.next()) {
	            jsonObject.put("votes", resultSet2.getInt("vote_count"));
	        }

	        jsonArray.put(jsonObject);
	    }

	    return jsonArray;
	
	}
	
	// For voters
	
	public JSONArray getVoters(int orgId) throws SQLException, JSONException {
		
		String query = "select Users.*, Voters.* from Voters join Users on Users.id = Voters.user_id where Voters.organization_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, orgId);
		ResultSet resultSet = preparedStatement.executeQuery();
		JSONArray jsonArray = new JSONArray();
		
		while(resultSet.next()) {
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", resultSet.getString("Users.name"));
			jsonObject.put("email", resultSet.getString("Users.email"));
			jsonObject.put("photo", resultSet.getString("Users.photo"));
			jsonObject.put("dob", resultSet.getDate("Voters.dob"));
			jsonObject.put("voter_id", resultSet.getInt("Voters.id"));
			jsonObject.put("gender", resultSet.getString("Voters.gender"));
			jsonObject.put("phone", resultSet.getString("Voters.phone"));
			
			jsonArray.put(jsonObject);
		}
		
		return jsonArray;
		
	}
	
	public boolean removeVoters(int voterId) throws SQLException {
		String query = "delete from Voters where id = ?";
		PreparedStatement preparedStatement= connection.prepareStatement(query);
		preparedStatement.setInt(1, voterId);
		int affectedRows = preparedStatement.executeUpdate();
		
		return affectedRows > 0;
	
	}
	
	// For dashboard
	
	// current elections
	
	public JSONArray getCurrentElections(int orgId) throws SQLException, JSONException {
		
		JSONArray jsonArray = new JSONArray();
		
		String query = "select * from Elections where organization_id = ? and start_date <= current_date and end_date >= current_date";		PreparedStatement preparedStatement = connection.prepareCall(query);
		preparedStatement.setInt(1, orgId);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",resultSet.getInt("id"));
			jsonObject.put("name",resultSet.getString("name"));
			jsonObject.put("start_date",resultSet.getDate("start_date"));
			jsonObject.put("end_date",resultSet.getDate("end_date"));
			jsonObject.put("description",resultSet.getString("description"));
			
			jsonArray.put(jsonObject);
		}
		
		return jsonArray;
	
	}
	
	// Graph
	
	public JSONObject electionsInYear(int orgId) throws SQLException, JSONException {
	
		JSONObject jsonObject = new JSONObject();
		
		String query = "select year(start_date) as year, count(*) as count from Elections where organization_id = ? group by year(start_date)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, orgId);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			jsonObject.put(resultSet.getInt("year")+"", resultSet.getInt("count"));
		}
		
		return jsonObject;
		
	}
	
	public JSONObject votersInElection(int orgId) throws SQLException, JSONException {
	
		JSONObject jsonObject = new JSONObject();
		
		String query = "select Election.name, count(*) from Votes join Elections on Elections.id = Votes.election_id where Elections.organization_id = ? group by Votes.election_id";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			jsonObject.put(resultSet.getString("Election.name"), resultSet.getInt("count(*)"));
		}
		
		return jsonObject;
		
	}
	
	public JSONObject candidatesInElection(int orgId) throws SQLException, JSONException {
		
		JSONObject jsonObject = new JSONObject();
		
		String query = "select Elections.name, count(distinct Apply_Candidate.id), count(Candidates.id) from Elections left join Apply_Candidate on Elections.id = Apply_Candidate.election_id left join Candidates on Apply_Candidate.id = Candidates.apply_id group by Elections.id";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			jsonObject.put(resultSet.getString("Election.name"), resultSet.getInt("count(distinct Apply_Candidate.id)")+","+resultSet.getInt("count(Candidates.id)"));
		}
		
		return jsonObject;
		
	}
	
	public JSONObject votesInElection(int elecid) throws SQLException, JSONException {
		
		JSONObject jsonObject = new JSONObject();
		
		String query = "SELECT Users.name, COUNT(Votes.id) " +
	               "FROM Candidates " +
	               "LEFT JOIN Apply_Candidate ON Candidates.apply_id = Apply_Candidate.id " +
	               "LEFT JOIN Voters ON Apply_Candidate.voter_id = Voters.id " +
	               "LEFT JOIN Users ON Voters.user_id = Users.id " +
	               "LEFT JOIN Votes ON Candidates.id = Votes.candidate_id " +
	               "WHERE Apply_Candidate.election_id = ? " +
	               "GROUP BY Candidates.id";

		
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, elecid);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			jsonObject.put(resultSet.getString("Users.name"), resultSet.getInt("COUNT(Votes.id)"));
		}
		
		return jsonObject;
		
	}
	
	
}
