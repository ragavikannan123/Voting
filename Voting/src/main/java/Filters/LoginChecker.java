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

/**
 * Servlet Filter implementation class LoginChecker
 */
//@WebFilter("/*")
public class LoginChecker implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if any
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String servletPath = httpRequest.getServletPath();
        
        if (servletPath.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }

        if (httpRequest.getCookies() != null) {
            chain.doFilter(request, response);
            return;
        }
        else {
//        	 httpResponse.sendRedirect("Voting/Authentication.html");
        }
       
    }

    @Override
    public void destroy() {
        // Cleanup code, if any
    }
}