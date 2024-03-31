package Management;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Common.Voting_DB;

public class VoterManagement implements Management{
	
	Voting_DB db = Voting_DB.getDB();
	Connection connection = db.getConnection();
	
	@Override
	public JSONObject viewProfile(int id) throws SQLException, JSONException {
		
		JSONObject json = new JSONObject();
		System.out.println(id);
		String query = "select Users.*, Voters.* from Users join Voters on Voters.user_id = Users.id where Voters.id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setInt(1, id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while (resultSet.next()) {
	    	System.out.println("IN profile");
            json.put("name", resultSet.getString("Users.name"));
    	    json.put("email", resultSet.getString("Users.email"));
    	    json.put("role", resultSet.getString("Users.role"));
    	    json.put("photo", resultSet.getString("Users.photo"));
    	    json.put("dob", resultSet.getDate("dob"));
    	    json.put("gender", resultSet.getString("gender"));
    	    json.put("phone", resultSet.getString("phone"));
        }
	    return json;
	
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
	
	@Override
	public JSONArray getCandidates(int elecId) throws SQLException, JSONException {
	
	    String query1 = "select Apply_Candidate.*, Voters.*, Elections.name as election_name, Users.photo, Users.name, Candidates.id as candidate_id from Candidates join Apply_Candidate on Apply_Candidate.id = Candidates.apply_id join Elections on Elections.id = Apply_Candidate.election_id join Voters on Apply_Candidate.voter_id = Voters.id join Users on Users.id = Voters.user_id where Elections.id = ?";
	    PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
	    preparedStatement1.setInt(1, elecId);
	    ResultSet resultSet1 = preparedStatement1.executeQuery();

	    JSONArray jsonArray = new JSONArray();

	    while (resultSet1.next()) {
	        JSONObject jsonObject = new JSONObject();
	        int candidateId = resultSet1.getInt("candidate_id");
	        jsonObject.put("id", candidateId);
	        jsonObject.put("voter_name", resultSet1.getString("Users.name"));
	        jsonObject.put("dob", resultSet1.getDate("Voters.dob"));
	        jsonObject.put("gender", resultSet1.getString("Voters.gender"));
	        jsonObject.put("phone", resultSet1.getString("Voters.phone"));
	        jsonObject.put("photo", resultSet1.getString("Users.photo"));
	        jsonObject.put("election_name", resultSet1.getString("election_name"));
	        jsonObject.put("logo", resultSet1.getString("Apply_Candidate.logo"));
	        jsonArray.put(jsonObject);
	    }

	    return jsonArray;
	
	}


	
	public boolean giveVote(int userId,int eleId,int canId) throws SQLException {
		
		String query = "SELECT * FROM Votes WHERE voter_id = ? AND election_id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setInt(1, userId);
	    preparedStatement.setInt(2,eleId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    if (resultSet.next()) {
	    	return false;
	    }
	    else {
	    	query = "insert into Votes (voter_id,candidate_id,election_id) values (?, ?, ?)";
		    preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setInt(1, userId);
		    preparedStatement.setInt(2, canId);
		    preparedStatement.setInt(3,eleId);
		    int affectedRows = preparedStatement.executeUpdate();
	    }
	    return true;
	}
	public boolean applyCandidate(int userId, String image, int elecId) throws SQLException {
	
	    String query = "SELECT id FROM Voters WHERE user_id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setInt(1, userId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    int voter_id = 0;
	    while(resultSet.next()) {
	    	voter_id = resultSet.getInt("id");
	    }
	    System.out.println("voter id:"+userId);
	    System.out.println("elec id:"+elecId);
	    query = "INSERT INTO Apply_Candidate (voter_id, election_id, logo) VALUES (?, ?, ?)";
	    preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setInt(1, userId);
	    preparedStatement.setInt(2, elecId);
	    preparedStatement.setString(3, image);
	    int rowsAffected = preparedStatement.executeUpdate();
	    return rowsAffected > 0;
	}
	
	public String votedPerson(int userId, int elecId) throws SQLException {
		
		String  query = "SELECT Users.name FROM Votes join Candidates on Candidates.id = Votes.candidate_id join Apply_Candidate on Apply_Candidate.id = Candidates.apply_id join Voters on Voters.id = Apply_Candidate.voter_id join Users on Users.id = Voters.user_id where Votes.voter_id = ? AND Votes.election_id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setInt(1, userId);
	    preparedStatement.setInt(2, elecId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    if (resultSet.next()) {
	    	return resultSet.getString("Users.name");
	    }
	    return "none";
	}
	
	public String isCandidate(int userId, int elecId) throws SQLException {
		
		String query = "select * from Candidates join Apply_Candidate on Apply_Candidate.id = Candidates.apply_id where Apply_Candidate.voter_id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setInt(1, userId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    if (resultSet.next()) {
	    	return "You are candidate of this election";
	    }
	    
	    query = "select * from Apply_Candidate where voter_id = ?";
	    preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setInt(1, userId);
	    resultSet = preparedStatement.executeQuery();
	    if (resultSet.next()) {
	    	return "You are already applied for this election";
	    }
	    
	    return "none";
		
	}
	
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

	public JSONObject electionsStatus(int orgId) throws SQLException, JSONException {
		
	    JSONObject jsonObject = new JSONObject();
	    Date currentDate = new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String formattedDate = dateFormat.format(currentDate);
	   
	    String query = "SELECT "
	                 + "SUM(CASE WHEN start_date > ? THEN 1 ELSE 0 END) AS upcoming, "
	                 + "SUM(CASE WHEN start_date <= ? AND end_date >= ? THEN 1 ELSE 0 END) AS ongoing, "
	                 + "SUM(CASE WHEN end_date < ? THEN 1 ELSE 0 END) AS finished "
	                 + "FROM Elections WHERE organization_id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setString(1, formattedDate);
	    preparedStatement.setString(2, formattedDate);
	    preparedStatement.setString(3, formattedDate);
	    preparedStatement.setString(4, formattedDate);
	    preparedStatement.setInt(5, orgId);
	    ResultSet resultSet = preparedStatement.executeQuery();

	    if (resultSet.next()) {
	        int upcomingCount = resultSet.getInt("upcoming");
	        int ongoingCount = resultSet.getInt("ongoing");
	        int finishedCount = resultSet.getInt("finished");
	        
	        jsonObject.put("Upcoming", upcomingCount);
	        jsonObject.put("Ongoing", ongoingCount);
	        jsonObject.put("Finished", finishedCount);
	    }
	    
	    return jsonObject;
	}
	public JSONObject getOrgDetails(int ordId) throws SQLException, JSONException {
		String query = "select Users.*, Organizations.* from Organizations join Admin on Admin.organization_id = Organizations.id join Users on Users.id = Admin.user_id where Organizations.id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, ordId);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		JSONObject jsonObject = new JSONObject();
		while(resultSet.next()) {
			jsonObject.put("Title", resultSet.getString("Organizations.name"));
			jsonObject.put("Name", resultSet.getString("Users.name"));
			jsonObject.put("Email", resultSet.getString("Users.email"));
		}
		return jsonObject;
	}
}
