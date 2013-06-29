package com.myfirstgapp.app;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*; 
//import java.text.NumberFormat;
import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class LogVisitorServlet extends HttpServlet {
  
//	double principal; // original principal 
	//  double intRate;   // interest rate 
	//  double numYears;  // length of loan in years 
	 
	  /* Number of payments per year.  You could 
	     allow this value to be set by the user. */ 
	  //final int payPerYear = 12; 
	 
	  //NumberFormat nf; 
	 
	  public void doGet(HttpServletRequest request, 
	                    HttpServletResponse response) 
	    throws ServletException, IOException { 
		// Set the content type. 
		    response.setContentType("text/html"); 
		 
		    // Get the output stream. 
		    PrintWriter pw = response.getWriter(); 
		 
	   // String payStr = ""; 
		  String uname = request.getParameter("username");
		//  String pass = request.getParameter("password");
		  if(uname==null || uname.trim().equals("")){
			  uname= "";
			  pw.println("callback('{success: false}')");
			  return;
		  }
	    // Log visitor in datastore
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	    Entity visitor = new Entity("Visitor");
	    visitor.setProperty("Username", uname);
	    //visitor.setProperty("password", pass);
	    Date hireDate = new Date();
	    visitor.setProperty("VisitDate", hireDate);
	    //employee.setProperty("attendedHrTraining", true);

	    datastore.put(visitor);
	    
	    // Display the necessary HTML. 
	    pw.print("callback('{success: true}')"); 
	  }  
	 	
}
