package Servlet.Authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Common.Loggers;
import Authentication.Login;



/**
 * Servlet implementation class AuthenticationServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = new Loggers(LoginServlet.class).getLogger();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
//    public void service(HttpServletRequest request, HttpServletResponse response) {
//    	
//    }

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
		//doGet(request, response);
		
		JSONObject jsonObject = (JSONObject) request.getAttribute("object");
		String email = null;
		String password = null;
		JSONObject json = new JSONObject();
		
		try {
			 StringBuilder sb = new StringBuilder();
		        BufferedReader reader = request.getReader();
		        String line;
		        
		        while ((line = reader.readLine()) != null) {
		            sb.append(line);
		        }
		        System.out.println(sb);
//		        JSONObject jsonObject = new JSONObject(sb.toString());
			
			email = jsonObject.getString("email");
			password = jsonObject.getString("password");
			Login login = new Login();
			boolean isLoggedIn = login.login(email, password);
			
			if (isLoggedIn) {
				
				json.put("statusCode", 200);
				json.put("message", "Welcome, you have successfully logged in!");
				JSONObject org = login.getDetails(email);
		        JSONArray keys = org.names();
		        
		        if (keys != null) {
		        	for (int i = 0; i < keys.length(); i++) {
		                 String key = keys.getString(i);
		                 if(key.equals("Role")) {
		                	 json.put("role", org.get(key));
		                 }
		                 Cookie cookie = new Cookie(key, org.get(key).toString());
		                 cookie.setMaxAge(86400);
			             response.addCookie(cookie);
			        }
		        }
			}
			
			else {
				
				try {
					json.put("statusCode", 500);
					json.put("message", "Invalid login details!");
				} 
				catch (JSONException e) {
					logger.error(email+": "+e.getMessage());
					e.printStackTrace();
				}
			}
		} 
		catch (SQLException | JSONException | IOException e) {
			e.printStackTrace();
			logger.error(email+": "+e.getMessage());
		}
		System.out.println(json);
		response.getWriter().write(json.toString());
	}

}
