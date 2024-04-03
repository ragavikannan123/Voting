package Filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import Common.Loggers;

/**
 * Servlet Filter implementation class LoginChecker
 */
@WebFilter({"/GetDetailsForUser", "/GetDetails"})
public class LoginChecker implements Filter {
	Logger logger = new Loggers(RegexChecking.class).getLogger();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if any
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String servletPath = httpRequest.getServletPath();
   
        if (httpRequest.getCookies() != null) {
            chain.doFilter(request, response);
//            return;
        }
        else {
        	 //httpResponse.sendRedirect("Voting/Authentication.html");
        	JSONObject responseObject = new JSONObject();
            try {
                responseObject.put("statusCode", 500);
                responseObject.put("message", "Loggedout");
            } catch (JSONException e) {
                logger.error("Error creating response JSON: " + e.getMessage());
            }
            
//            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write(responseObject.toString());
        }
       
    }

    @Override
    public void destroy() {
        // Cleanup code, if any
    }
}