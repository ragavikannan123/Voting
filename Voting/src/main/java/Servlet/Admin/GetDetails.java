package Servlet.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Year;

import org.apache.log4j.Logger;
import org.json.JSONArray;
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
@WebServlet("/GetDetails")
public class GetDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = new Loggers(GetDetails.class).getLogger();

    /**
     * Default constructor. 
     */
    public GetDetails() {
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
		JSONObject responseJson = new JSONObject();
		String id = null; 
		String orgId = "1";
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	        	if(cookie.getName().equals("Id")) {
	        		id = cookie.getValue();
	        	}
	        	if(cookie.getName().equals("OrgId")) {
	        		orgId = cookie.getValue();
	        	}
	        }
	    }
		
	    logger.info("Id : "+id);
	    logger.info("orgID:"+orgId);

	    
	    JSONObject json = new JSONObject();
	    AdminManagement adminManagement = new AdminManagement();
	    JSONArray election = new JSONArray();
	    JSONArray elections = new JSONArray();
	    JSONArray voters = new JSONArray();
	    JSONArray currentElections = new JSONArray();
	    JSONObject eleInYear = new JSONObject();
	    JSONArray applicants = new JSONArray();
	    
	    logger.info("json inits!");
 	    
	    
		try {
			election = adminManagement.getElections(Integer.parseInt(orgId));
			
			logger.info("Election: "+election);
			
			for (int j = 0; j < election.length(); j++) {
    	        JSONObject elec = election.getJSONObject(j);
    	        int electionId = elec.getInt("id");
    	        JSONArray candidates = adminManagement.getCandidates(electionId);
    	        JSONObject openingObject = new JSONObject(elec.toString());
    	        openingObject.put("candidate", candidates);
    	        openingObject.put("votes", adminManagement.votesInElection(electionId));
    	        elections.put(openingObject);
    	    }
			
			logger.info("for loop mudinchu..");
			voters = adminManagement.getVoters(Integer.parseInt(orgId));
			currentElections = adminManagement.getCurrentElections(Integer.parseInt(orgId));
			eleInYear = adminManagement.electionsInYear(Integer.parseInt(orgId));
			applicants = adminManagement.getApplicants(Integer.parseInt(orgId));
			
			
			
			logger.info("elections: " + elections);
			logger.info("voters: " + voters);
			logger.info("currentElections: " + currentElections);
			logger.info("eleInYear: " + eleInYear);
			logger.info("applicants: " + applicants);
			
			json.put("elections", elections);
			json.put("voters",voters);
			json.put("current", currentElections);
			json.put("year", eleInYear);
			json.put("applicants", applicants);

			logger.info("put ku pinnadi..");

			responseJson.put("statusCode", 200);
			responseJson.put("message", json);
		}
		catch (SQLException | JSONException e) {
			e.printStackTrace();
			logger.error("Admin : "+id+" "+ e.getMessage());
		}
	
		response.getWriter().write(responseJson.toString());
	}

}
