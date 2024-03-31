package Servlet.Authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Authentication.Login;
import Authentication.SignUp;
import Common.Loggers;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpUser")
public class SignUpForUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = new Loggers(SignUpForAdmin.class).getLogger();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpForUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		JSONObject jsonObject = (JSONObject) request.getAttribute("object");
		
		String username = null;
		String password = null;
		String email = null;
		String organization = null;
		String photo = null;
		String dob = null;
		String gender = null;
		String phone = null;
		
		try {
			username = jsonObject.getString("username");
			password = jsonObject.getString("password");
			email = jsonObject.getString("email");
			organization = jsonObject.getString("organization");
			photo = jsonObject.getString("photo");
			dob = jsonObject.getString("dob");
			gender = jsonObject.getString("gender");
			phone = jsonObject.getString("phone");
		} 
		
		catch (JSONException e) {
		
			logger.error("Admin : "+email+" "+ e.getMessage());
			e.printStackTrace();
		
		}
	   
	    String signedUpOutput = null;
	    SignUp signup = new SignUp();
	    
		try {
			signedUpOutput = signup.signUpForUsers(email, username, photo, organization, password, dob, gender, phone);
		} catch (SQLException e) {
			logger.error(email+": "+e.getMessage());
		}
	    JSONObject json = new JSONObject(); 
	    if(signedUpOutput.equals("user added successfully")) {
	    	
	        try {
	        	Login login = new Login();
		    	JSONObject org = login.getDetails(email);
		         JSONArray keys = org.names();
		         if (keys != null) {
		             for (int i = 0; i < keys.length(); i++) {
		                 String key = keys.getString(i);
		                 Cookie cookie = new Cookie(key, org.get(key).toString());
		                 cookie.setMaxAge(86400);
		                 response.addCookie(cookie);
		             }
		         }
				json.put("statusCode", 200);
				json.put("message", "Welcome, you have successfully signed up!");
			} 
	        catch (JSONException | SQLException e) {
	        	logger.error(email+": "+e.getMessage());
			}
	        
	        response.getWriter().write(json.toString());
	    }
	    else {
			try {
				json.put("statusCode", 500);
				json.put("message",signedUpOutput);
			} catch (JSONException e) {
				logger.error(email+": "+e.getMessage());
			}
			response.getWriter().write(json.toString());
		}
	     
	}

}
