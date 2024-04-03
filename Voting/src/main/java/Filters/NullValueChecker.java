package Filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import Common.Loggers;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter({"/Login", "/AddElection", "/SignUpAdmin", "/SignUpUser", "/ApplyForCandidate"})
public class NullValueChecker implements Filter {
	
	Logger logger = new Loggers(RegexChecking.class).getLogger();
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = httpRequest.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        
        String jsonData = sb.toString();
        JSONObject jsonObject = null;
        
        try {
            jsonObject = new JSONObject(jsonData);
        } catch (JSONException e) {
            logger.error("Error parsing JSON data: " + e.getMessage());
        }

        boolean hasNullValue = false;
        if (jsonObject != null) {
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value;
				try {
					value = jsonObject.get(key);
					 if (value == "") {
						 System.out.println("null");
		                    hasNullValue = true;
		                    break;
		                }
				} catch (JSONException e) {
					 logger.error("Error parsing response JSON: " + e.getMessage());
				}
               
            }
        } else {
            hasNullValue = true; // JSON object is null
        }
        
        
        if (hasNullValue) {
            JSONObject responseObject = new JSONObject();
            try {
                responseObject.put("statusCode", 500);
                responseObject.put("message", "Invalid data format: Null values are not allowed.");
            } catch (JSONException e) {
                logger.error("Error creating response JSON: " + e.getMessage());
            }
            
//            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write(responseObject.toString());
        } 
        else {
        	request.setAttribute("object", jsonObject);
            chain.doFilter(request, response);
        }
    }


    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
