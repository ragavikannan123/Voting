package Authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import Common.Loggers;
import Common.Voting_DB;

public class Login {
	
	Logger logger = new Loggers(Login.class).getLogger();
	
	public boolean login(String email,String password) throws SQLException{
		
		Voting_DB db = Voting_DB.getDB();
		Connection connection = db.getConnection();
		password = hashString(password);
		System.out.println(password);
		
		String query = "select role from Users where email = ? and password = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setString(1, email);
	    preparedStatement.setString(2, password);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    if(resultSet.next()) {
	    	logger.info(email+" successfully logged in");
	    	return true;
	    }
	   return false;
	
	}
	
	public JSONObject getDetails(String email) throws SQLException, JSONException {
		System.out.println(email);
		Voting_DB db = Voting_DB.getDB();
		Connection connection = db.getConnection();
		String role = null;
		JSONObject jsonObject = new JSONObject();
		
		String query = "select role from Users where email = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setString(1, email);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if(resultSet.next()) {
	    	role = resultSet.getString("role");
	    	jsonObject.put("Role", role);
	    }
	    
	    if(role.equalsIgnoreCase("Admin")) {
	    	query = "select organization_id, Admin.id as id from Admin join Users on Admin.user_id = Users.id where Users.email = ?";
	    }
	    else {
		    query = "select organization_id, Voters.id as id from Voters join Users on Voters.user_id = Users.id where Users.email = ?";
		}
		preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setString(1, email);
	    resultSet = preparedStatement.executeQuery();
	    while(resultSet.next()) {
	    	jsonObject.put("OrgId", resultSet.getInt("organization_id"));
	    	jsonObject.put("Id", resultSet.getInt("id"));
	    }
	   return jsonObject;
	
	}
	
	public static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
