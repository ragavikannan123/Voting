package Servlet.Admin;

import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import Common.Loggers;
import Management.AdminManagement;
import Servlet.Authentication.SignUpForAdmin;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddElection
 */
@WebServlet("/RemoveCandidate")
public class RemoveCandidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = new Loggers(SignUpForAdmin.class).getLogger();

    /**
     * Default constructor. 
     */
    public RemoveCandidate() {
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
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		String id = null; 
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	        	if(cookie.getName().equals("Id")) {
	        		id = cookie.getValue();
	        	}
	        }
	    }
		
		JSONObject jsonObject = (JSONObject) request.getAttribute("object");
		String candidateId = null;
		
		try {
			candidateId = jsonObject.getString("candidateId");
		} 
		
		catch (JSONException e) {
		
			logger.error("Admin : "+id+" "+ e.getMessage());
		}
		Boolean isRemoved = false;
	    AdminManagement adminManagement = new AdminManagement();
	    
		try {
			isRemoved = adminManagement.removeCandidate(Integer.parseInt(candidateId));
		} catch (SQLException e) {
			logger.error("Admin : "+id+" "+ e.getMessage());
		}
	    JSONObject json = new JSONObject(); 
	    if(isRemoved) {
	    	
	        try {
				json.put("statusCode", 200);
				json.put("message", "The candidate was removed");
			} 
	        catch (JSONException e) {
	        	logger.error("Admin : "+id+" "+ e.getMessage());
			}
	        
	        response.getWriter().write(json.toString());
	    }
	    else {
			try {
				json.put("statusCode", 500);
				json.put("message", "Failed to remove candidate");
			} catch (JSONException e) {
				logger.error("Admin : "+id+" "+ e.getMessage());
			}
			response.getWriter().write(json.toString());
		}
	}

}
