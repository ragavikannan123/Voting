package Servlet.Admin;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
@WebServlet("/AddElection")
public class AddElection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = new Loggers(SignUpForAdmin.class).getLogger();

    /**
     * Default constructor. 
     */
    public AddElection() {
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
		
		String orgId = "1";
		String id = null; 
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	        	if(cookie.getName().equals("OrgId")) {
	        		orgId = cookie.getValue();
	        	}
	        	if(cookie.getName().equals("Id")) {
	        		id = cookie.getValue();
	        	}
	        }
	    }
		
		JSONObject jsonObject = (JSONObject) request.getAttribute("object");
		
		String name = null;
		String decription = null;
		String startDate = null;
		String endDate = null;
		
		
		try {
			name = jsonObject.getString("name");
			decription = jsonObject.getString("description");
			startDate = jsonObject.getString("startDate");
			endDate = jsonObject.getString("endDate");
		} 
		
		catch (JSONException e) {
			e.printStackTrace();
			logger.error("Admin : "+id+" "+ e.getMessage());
		}
	   
		Date start = null;
		Date end = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            java.util.Date utilStart = dateFormat.parse(startDate);
            java.util.Date utilEnd = dateFormat.parse(endDate);
            start = new Date(utilStart.getTime());
            end = new Date(utilEnd.getTime());
        } catch (ParseException e) {
        	logger.error("Admin : "+id+" "+ e.getMessage());
        }
        
	    String addElectionOutput = null;
	    AdminManagement adminManagement = new AdminManagement();
	    
		try {
			addElectionOutput = adminManagement.addElection(Integer.parseInt(orgId), name, decription, start, end);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Admin : "+id+" "+ e.getMessage());
		}
	    JSONObject json = new JSONObject(); 
	    if(addElectionOutput.equals("Election added successfully")) {
	    	
	        try {
				json.put("statusCode", 200);
				json.put("message", addElectionOutput);
			} 
	        catch (JSONException e) {
	        	logger.error("Admin : "+id+" "+ e.getMessage());
			}
	        
//	        response.getWriter().write(json.toString());
	    }
	    else {
			try {
				json.put("statusCode", 500);
				json.put("message",addElectionOutput);
			} catch (JSONException e) {
				logger.error("Admin : "+id+" "+ e.getMessage());
			}
		}
	    response.getWriter().write(json.toString());
	}

}
