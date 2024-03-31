package Filters;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import Common.Loggers;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebFilter({"/SignUpAdmin", "/SignUpUser"})
public class RegexChecking implements Filter {

	Logger logger = new Loggers(RegexChecking.class).getLogger();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
//	    HttpServletResponse httpResponse = (HttpServletResponse) response;

	    StringBuilder sb = new StringBuilder();
        BufferedReader reader = httpRequest.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
        	sb.append(line);
        }
        
		String jsonData = sb.toString();
		JSONObject jsonObject = null;
		String email = null;
		String password = null;
		
		try {
			jsonObject = new JSONObject(jsonData);
			email = jsonObject.getString("email");
			password = jsonObject.getString("password");
		} 
		catch (JSONException e) {
			logger.error(email+": "+e.getMessage());
		}
		
		String passwordRegex = "^(?=.*\\d)(?=.*[a-zA-Z].*[a-zA-Z].*[a-zA-Z]).{8,}$";
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	    
			if (!password.matches(passwordRegex) || !email.matches(emailRegex)) {
				
			    JSONObject responseObject = new JSONObject();
			    try {
					responseObject.put("statusCode", 500);
					responseObject.put("message", "Invalid password or email format");
				} 
			    catch (JSONException e) {
					logger.error(email+": "+e.getMessage());
				}
			  
			    response.getWriter().write(responseObject.toString());
			    return;
			}
		
	    request.setAttribute("object", jsonObject);
	    chain.doFilter(request, response);
    }

   

    @Override
    public void destroy() {
  // Clean-up code if needed

    }
}
