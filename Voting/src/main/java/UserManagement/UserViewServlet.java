//package UserManagement;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import org.apache.log4j.Logger;
//import org.json.JSONObject;
//
//import Common.Loggers;
//import Management.VoterManagement;
//
///**
// * Servlet implementation class UserViewServlet
// */
//@WebServlet("/UserViewServlet")
//public class UserViewServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	Logger logger = new Loggers(UserViewServlet.class).getLogger();
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public UserViewServlet() {
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
//		String viewOption = request.getParameter("option");
//		String elecId = request.getParameter("elecId");
//	    String orgValue = null;
//		Cookie[] cookies = request.getCookies();
//		JSONObject json = null;
//		System.out.println("ragavi");
//        if (cookies != null) {
//        	
//        	for(Cookie cookie:cookies) {
//        		if(cookie.getName().equals("org")) {
//            		orgValue = cookie.getValue();
//            	}
//        	}
//            for (Cookie cookie : cookies) {
//                if(cookie.getName().equals("email")) {
//                	VoterManagement user = new VoterManagement();
//                	try {
//                		if (viewOption.equals("profile")) {
//							json = user.info(cookie.getValue());
//                		}
//                		else if(viewOption.equals("elections")) {
//                			System.out.println(orgValue);
//                			json = user.viewElections(Integer.parseInt(orgValue));
//                		}
//                		else {
//                			json = user.viewCandidates(Integer.parseInt(orgValue),Integer.parseInt(elecId));
//                		}
//                	}
//                	catch (Exception e) {
//                		logger.error("The error was in UserView servlet while getting json from UserManagement class");
//						e.printStackTrace();
//					}
//                }
//            }
//        } 
//        response.getWriter().write(json.toString());
//	}
//
//}
