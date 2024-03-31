package Servlet.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
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
@WebServlet("/GiveVote")
public class GiveVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = new Loggers(SignUpForAdmin.class).getLogger();

    /**
     * Default constructor. 
     */
    public GiveVote() {
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
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	        	if(cookie.getName().equals("Id")) {
	        		id = cookie.getValue();
	        	}
	        }
	    }
	    
//	    JSONObject jsonObject = (JSONObject) request.getAttribute("object");
		
		int elecId = 0;
		int canId = 0;
		
		try {
			
			StringBuilder sb = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    String line;
		        
		    while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	        JSONObject jsonObject = new JSONObject(sb.toString());
			elecId = jsonObject.getInt("elecId");
			canId = jsonObject.getInt("canId");
		} 
		
		catch (JSONException e) {
			e.printStackTrace();
			logger.error("User : "+id+" "+ e.getMessage());
		}
		System.out.println(elecId+" "+canId);
	    JSONObject json = new JSONObject();
	    VoterManagement voterManagement = new VoterManagement();
	    try {
			Boolean isVoted = voterManagement.giveVote(Integer.parseInt(id), elecId, canId);
			if(isVoted) {
				json.put("statusCode", 200);
				json.put("message", "You are vote was submitted");
			}
			else {
				json.put("statusCode", 200);
				json.put("message", "Failed to give vote");
			}
	    } catch (NumberFormatException | SQLException | JSONException e) {
	    	e.printStackTrace();
			logger.error("User : "+id+" "+ e.getMessage());
		}
 	    
		response.getWriter().write(json.toString());
	}

}
