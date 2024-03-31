package Servlet.User;

import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Common.Loggers;
import Management.VoterManagement;
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
@WebServlet("/GetDetailsForUser")
public class GetDetailsForUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = new Loggers(SignUpForAdmin.class).getLogger();

    /**
     * Default constructor. 
     */
    public GetDetailsForUser() {
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
		
		String id = "1"; 
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
	    
	    JSONObject jsonObject = (JSONObject) request.getAttribute("object");
		
		String elecId = null;
		
//		try {
//			elecId = jsonObject.getString("elecId");
//		} 
//		
//		catch (JSONException e) {
//		
//			logger.error("User : "+id+" "+ e.getMessage());
//		}
		
	    JSONObject json = new JSONObject();
	    VoterManagement voterManagement = new VoterManagement();
	    JSONArray election = new JSONArray();
	    JSONArray elections = new JSONArray();
	    JSONArray currentElections = new JSONArray();
	    JSONObject canInElec = new JSONObject();
	    JSONObject profile = new JSONObject();
	    JSONObject chart = new JSONObject();
	    JSONObject org = new JSONObject();
 	    
	    
		try {
			election = voterManagement.getElections(Integer.parseInt(orgId));
			
			for (int j = 0; j < election.length(); j++) {
    	        JSONObject elec = election.getJSONObject(j);
    	        int electionId = elec.getInt("id");
    	        JSONArray candidates = voterManagement.getCandidates(electionId);
    	        JSONObject openingObject = new JSONObject(elec.toString());
    	        openingObject.put("candidate", candidates);
    	        openingObject.put("votes", voterManagement.votesInElection(electionId));
    	        openingObject.put("isCandidate", voterManagement.isCandidate(Integer.parseInt(id),electionId));
    	        openingObject.put("giveVote", voterManagement.votedPerson(Integer.parseInt(id), electionId));
    	        elections.put(openingObject);
    	    }
    	    profile = voterManagement.viewProfile(Integer.parseInt(id));
			currentElections = voterManagement.getCurrentElections(Integer.parseInt(orgId));
			canInElec = voterManagement.votesInElection(Integer.parseInt(orgId));
			chart = voterManagement.electionsStatus(Integer.parseInt(orgId));
			org = voterManagement.getOrgDetails(Integer.parseInt(orgId));
			
			json.put("profile", profile);
			json.put("elections", elections);
			json.put("current", currentElections);
			System.out.println(profile);
			json.put("chart", chart);
			json.put("org", org);
			json.put("elections", elections);
			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put("message", json);
			response.getWriter().write(responseJsonObject.toString());
		}
		catch (SQLException | JSONException e) {
			e.printStackTrace();
			logger.error("user : "+id+" "+ e.getMessage());
		}
	
		
	}

}
