//package UserManagement;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//import org.apache.log4j.Logger;
//
//import Common.Loggers;
//import Management.VoterManagement;
//
///**
// * Servlet implementation class ApplyCandidate
// */
//@WebServlet("/ApplyCandidate")
//public class ApplyCandidate extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	Logger logger = new Loggers(ApplyCandidate.class).getLogger();   
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public ApplyCandidate() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		//doGet(request, response);
//		String election = request.getParameter("election");
//		String image = request.getParameter("image");
//		
//		System.out.println(election);
//		Cookie[] cookies = request.getCookies();
//		boolean isAble = false;
//		String email = null;
//        if (cookies != null) {
//            
//        	for(Cookie cookie:cookies) {
//        		if(cookie.getName().equals("email")) {
//        			email = cookie.getValue();
//            		VoterManagement user = new VoterManagement();
//            		try {
//						isAble = user.applyCandidate(email, election,image);
//					} catch (SQLException e) {
//						logger.error("The error was in ApplyCandidate servlet");
//						e.printStackTrace();
//					}
//            	}
//        	}
//        }
//        if(isAble) {
//        	logger.info(email+" Applied for candidate");
//        	response.getWriter().write("true");
//        }
//        else {
//        	logger.info(email+" Failed to apply for candidate");
//        	response.getWriter().write("false");
//        }
//	}
//
//}
