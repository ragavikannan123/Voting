//package UserManagement;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.SQLException;
//
//import org.apache.log4j.Logger;
//
//import Common.Loggers;
//import Management.VoterManagement;
//
///**
// * Servlet implementation class GiveVote
// */
//@WebServlet("/GiveVote")
//public class GiveVote extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	Logger logger = new Loggers(GiveVote.class).getLogger();
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public GiveVote() {
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
//		
//		String canId = request.getParameter("canId");
//		String elecId = request.getParameter("elecId");
//		Cookie[] cookies = request.getCookies();
//		String email = null;
//        if (cookies != null) {
//            
//            for (Cookie cookie : cookies) {
//                if(cookie.getName().equals("email")) {
//                	email = cookie.getValue();
//                }
//                	VoterManagement view = new VoterManagement();
//                	try {
//                		if(view.giveVote(email, Integer.parseInt(elecId), Integer.parseInt(canId))) {
//                			logger.info(email+" successfully gave their vote");
//                			response.getWriter().write("true");
//                		}
//                	}
//                	catch (SQLException e) {
//                		logger.error("The error was in GiveVote");
//						e.printStackTrace();
//					}
//                	break;
//                }
//            }
//        logger.info(email+" have already gave their vote");
//        response.getWriter().write("false");
//	}
//
//}
