package Authentication;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.log4j.Logger;

import Common.Loggers;
import Common.Voting_DB;

public class SignUp {
	
	Logger logger = new Loggers(SignUp.class).getLogger();

	public String signUpForAdmin(String name,String email,String password,String organization,String photo) throws SQLException {
		
		Voting_DB db = Voting_DB.getDB();
		Connection connection = db.getConnection();
		password = Login.hashString(password);
		
		String query = "SELECT * FROM Users WHERE email = ? AND name = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, email);
		preparedStatement.setString(2, name);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next()) {
			return "User already exists";
		}
		
	    int id = addUser(name,email,photo,password, "Admin");
	    if(id == 0) {
	    	logger.info(email+": "+"Failed to add user");
	    	return "Failed to add user";
	    }
	    
	    String checkOrg = "select id from Organizations where name = ?";
		preparedStatement = connection.prepareStatement(checkOrg);
		preparedStatement.setString(1, organization);
		resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			return "Organization already exists";
		}
	    
	    
	   	String addOrganization = "insert into Organizations (name) values (?)";
	    preparedStatement = connection.prepareStatement(addOrganization);
	    preparedStatement.setString(1, organization);
	    int isOrgAdded  = preparedStatement.executeUpdate();
	    
	    if(isOrgAdded <= 0){
	    	logger.info(email+": "+"Failed to add organization");
	    	return "Failed to add organization";
	    }
	        	        
	    String getOrg = "select id from Organizations where name = ?";
	    preparedStatement = connection.prepareStatement(getOrg);
	    preparedStatement.setString(1, organization);
	    int OrgId = 0;
	    ResultSet resultSet2 = preparedStatement.executeQuery();
	    while(resultSet2.next()) {
	    	OrgId = resultSet2.getInt("id");
	    }
	        
	   	String addAdmin = "insert into Admin (user_id,organization_id) values (?, ?)";
	   	preparedStatement = connection.prepareStatement(addAdmin);
	    preparedStatement.setInt(1, id);
	    preparedStatement.setInt(2, OrgId);
	    int isAdminAdded = preparedStatement.executeUpdate();
	    
	    if(isAdminAdded <= 0) {
	    	
	    	String removeOrganization = "delete from Organizations where id = ?";
		   	preparedStatement = connection.prepareStatement(removeOrganization);
		    preparedStatement.setInt(1, OrgId);
		    preparedStatement.executeUpdate();
		    logger.info(email+": "+"Failed to add admin");
	    	return "Failed to add Admin";
	    }
	        
	    logger.info(email+": "+"user added successfully");
	    return "user added successfully";
	
	}
	
	public int addUser(String name, String email, String photo, String password, String role) throws SQLException {
		
		Voting_DB db = Voting_DB.getDB();
		Connection connection = db.getConnection();
		
        password = Login.hashString(password);
		
		String addUser = "insert into Users (name,email,photo,role,password) values (?, ?, ?, ?, ?)";
        
       	PreparedStatement preparedStatement = connection.prepareStatement(addUser);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, email);
	    preparedStatement.setString(3, photo); 
       	preparedStatement.setString(4, role);
       	preparedStatement.setString(5, password);
       	preparedStatement.executeUpdate();
		 
	    String getUser = "select id from Users where email = ? and name = ?";
        preparedStatement = connection.prepareStatement(getUser);
        preparedStatement.setString(1, email);
	    preparedStatement.setString(2, name);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    int id = 0;
	    if (resultSet.next()) {
	        id = resultSet.getInt("id");
	    }
       
        return id;
	}
	
	public String signUpForUsers(String email, String name, String photo, String organization, String password, String dob, String gender, String phone) throws SQLException {
		
		
		LocalDate date = LocalDate.parse(dob);
		Date sqlDate = Date.valueOf(date);
		Voting_DB db = Voting_DB.getDB();
		Connection connection = db.getConnection();
		password = Login.hashString(password);
		
		String query = "SELECT * FROM Users WHERE email = ? AND name = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, email);
		preparedStatement.setString(2, name);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next()) {
			return "User already exists";
		}
		
	    int id = addUser(name,email,photo,password, "Admin");
	    if(id == 0) {
	    	logger.info(email+": "+"Failed to add user");
	    	return "Failed to add user";
	    }
	 
	    String getOrg = "select id from Organizations where name = ?";
	    preparedStatement = connection.prepareStatement(getOrg);
	    preparedStatement.setString(1, organization);
	    int OrgId = 0;
	    ResultSet resultSet2 = preparedStatement.executeQuery();
	    while(resultSet2.next()) {
	    	OrgId = resultSet2.getInt("id");
	    }
	        
	   	String addAdmin = "insert into Voters (user_id,dob,gender,phone,organization_id) values (?, ?, ?, ?, ?)";
	   	preparedStatement = connection.prepareStatement(addAdmin);
	    preparedStatement.setInt(1, id);
	    preparedStatement.setDate(2, sqlDate);
	    preparedStatement.setString(3, gender);
	    preparedStatement.setString(4, phone);
	    preparedStatement.setInt(5, OrgId);
	    int isAdminAdded = preparedStatement.executeUpdate();
	    
	    if(isAdminAdded <= 0) {
	    	
	    	String removeOrganization = "delete from Organizations where id = ?";
		   	preparedStatement = connection.prepareStatement(removeOrganization);
		    preparedStatement.setInt(1, OrgId);
		    preparedStatement.executeUpdate();
		    logger.info(email+": "+"Failed to add admin");
	    	return "Failed to add Admin";
	    }
	        
	    logger.info(email+": "+"user added successfully");
	    return "user added successfully";
		
	}
}
