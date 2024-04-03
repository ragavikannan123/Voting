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
@WebServlet("/ApplyForCandidate")
public class ApplyForCandidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = new Loggers(SignUpForAdmin.class).getLogger();

    /**
     * Default constructor. 
     */
    public ApplyForCandidate() {
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
	    
	    JSONObject jsonObject = (JSONObject) request.getAttribute("object");
		
		int elecId = 0;
		String logo = null;
		
		try {
//			StringBuilder sb = new StringBuilder();
//	        BufferedReader reader = request.getReader();
//	        String line;
//	        
//	        while ((line = reader.readLine()) != null) {
//	            sb.append(line);
//	        }
//	        System.out.println(id);
//	        System.out.println(sb);
//	        JSONObject jsonObject = new JSONObject(sb.toString());
			elecId = jsonObject.getInt("elecId");
			logo = jsonObject.getString("logo");
		} 
		
		catch (JSONException e) {
		e.printStackTrace();
			logger.error("User : "+id+" "+ e.getMessage());
		}
		
	    JSONObject json = new JSONObject();
	    VoterManagement voterManagement = new VoterManagement();
	    try {
			Boolean isVoted = voterManagement.applyCandidate(Integer.parseInt(id), logo, elecId);
			if(isVoted) {
				json.put("statusCode", 200);
				json.put("message", "You are applied for candidate");
			}
			else {
				json.put("statusCode", 200);
				json.put("message", "Failed to apply");
			}
	    } catch (NumberFormatException | SQLException | JSONException e) {
	    	e.printStackTrace();
			logger.error("User : "+id+" "+ e.getMessage());
		}
 	    
		response.getWriter().write(json.toString());
	}

}
