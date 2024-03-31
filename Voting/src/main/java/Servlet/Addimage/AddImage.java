package Servlet.Addimage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import Common.Loggers;

/**
 * Servlet implementation class AddImage
 */
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024, 
	    maxFileSize = 5 * 1024 * 1024,   
	    maxRequestSize = 10 * 1024 * 1024 
	)
@WebServlet("/AddImage")
public class AddImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = new Loggers(AddImage.class).getLogger();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddImage() {
        super();
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
		//doGet(request, response);
		Part filePart = request.getPart("image");
		String fileName = filePart.getSubmittedFileName();     
		String folderPath = getServletContext().getRealPath("/images/User");
		File destinationFile = new File(folderPath, fileName);
        try {
        	InputStream input = filePart.getInputStream();
		    try (FileOutputStream output = new FileOutputStream(destinationFile)) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = input.read(buffer)) != -1) {
					output.write(buffer, 0, bytesRead);
				}
			}
		    logger.info("image added successfully");
		    response.getWriter().println("Image saved to: " + destinationFile.getAbsolutePath());
		 } 
         catch (IOException e) {
        	 e.printStackTrace();
        	 logger.info("The error was in Addimage class");
		     response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		     response.getWriter().println("Error on saving image");
		 }
	}

}
