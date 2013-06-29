package com.myfirstgapp.app;

//import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*; 
import java.text.*; 
import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;


@SuppressWarnings("serial")
public class GooglestartServlet extends HttpServlet {
	 double principal; // original principal 
	  double intRate;   // interest rate 
	  double numYears;  // length of loan in years 
	 
	  /* Number of payments per year.  You could 
	     allow this value to be set by the user. */ 
	  final int payPerYear = 12; 
	 
	  NumberFormat nf; 
	 
	  public void doGet(HttpServletRequest request, 
	                    HttpServletResponse response) 
	    throws ServletException, IOException {  
	    String payStr = ""; 
	 
	    // Log visitor in datastore
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	    Entity employee = new Entity("Visitor");
	    //employee.setProperty("firstName", "Antonio");
	    //employee.setProperty("lastName", "Salieri");
	    Date hireDate = new Date();
	    employee.setProperty("VisitDate", hireDate);
	    //employee.setProperty("attendedHrTraining", true);

	    datastore.put(employee);
	    // Create a number format. 
	    nf = NumberFormat.getInstance(); 
	    nf.setMinimumFractionDigits(2); 
	    nf.setMaximumFractionDigits(2); 
	 
	    // Get the parameters. 
	    String amountStr = request.getParameter("amount"); 
	    String periodStr = request.getParameter("period"); 
	    String rateStr = request.getParameter("rate"); 
	 
	    try {  
	      if(amountStr != null && periodStr != null && 
	         rateStr != null) { 
	        principal = Double.parseDouble(amountStr); 
	        numYears = Double.parseDouble(periodStr); 
	        intRate = Double.parseDouble(rateStr) / 100; 
	 
	        payStr = nf.format(compute()); 
	      } 
	      else { // one or more parameters missing 
	        amountStr = ""; 
	        periodStr = ""; 
	        rateStr = ""; 
	      } 
	    } catch (NumberFormatException exc) { 
	      // Take appropriate action here. 
	    } 
	 
	    // Set the content type. 
	    response.setContentType("text/html"); 
	 
	    // Get the output stream. 
	    PrintWriter pw = response.getWriter(); 
	 
	    // Display the necessary HTML. 
	    pw.print("<html><body> <left><img src='images/Philosophy.jpg' /><br/><br/>" + 
	             "<form name=\"Form1\"" + 
	             " action=\"http://bookshelf.99k.org/checklogin.php\" method=\"POST\">" + 
	             "<B>User Name:</B>" + 
	             " <input type=textbox name=\"myusername\"" + 
	             " size=12 value=\""); 
	    pw.print(amountStr + "\">"); 
	    pw.print("<BR><B>Password:</B>" + 
	             " <input type=password name=\"mypassword\""+ 
	             " size=12 value=\""); 
	    pw.println(periodStr + "\">"); 
	    /*pw.print("<BR><B>Enter interest rate:</B>" + 
	             " <input type=textbox name=\"rate\"" + 
	             " size=12 value=\""); 
	    pw.print(rateStr + "\">"); 
	    pw.print("<BR><B>Monthly Payment:</B>" + 
	             " <input READONLY type=textbox" + 
	             " name=\"payment\" size=12 value=\""); 
	    pw.print(payStr + "\">"); */ 
	    pw.print("<BR><P><input type=submit value=\"Submit\">"); 
	    pw.println("</form>"); 
	    pw.println("<div id=\"wx_module_8923\">");
	    pw.println("<a href=\"http://www.weather.com/weather/local/INXX0038\">Delhi Weather Forecast, India</a>");
	    pw.println("</div>");

	    pw.println("<script type=\"text/javascript\">");

	    /* Locations can be edited manually by updating 'wx_locID' below.  Please also update */
	    /* the location name and link in the above div (wx_module) to reflect any changes made. */
	    pw.println("var wx_locID = 'INXX0038';");

	    /* If you are editing locations manually and are adding multiple modules to one page, each */
	    /* module must have a unique div id.  Please append a unique # to the div above, as well */
	    /* as the one referenced just below.  If you use the builder to create individual modules  */
	    /* you will not need to edit these parameters. */
	    pw.println("var wx_targetDiv = 'wx_module_8923';");

	    /* Please do not change the configuration value [wx_config] manually - your module */
	    /* will no longer function if you do.  If at any time you wish to modify this */
	    /* configuration please use the graphical configuration tool found at */
	    /* https://registration.weather.com/ursa/wow/step2 */
	    pw.println("var wx_config='SZ=300x250*WX=FHC*LNK=SSNL*UNT=C*BGI=sun*MAP=india_|null*DN=appspot.com*TIER=0*PID=1289891131*MD5=50ca89cc7a67d4489ac3cfa999b5a274';");

	    pw.println("document.write('<scr'+'ipt src=\"'+document.location.protocol+'//wow.weather.com/weather/wow/module/'+wx_locID+'?config='+wx_config+'&proto='+document.location.protocol+'&target='+wx_targetDiv+'\"></scr'+'ipt>');");  
	    pw.println("</script>");
	    pw.println("<br/><span>For Free Site click <a href='http://bookshelf.99k.org' target='_blank'>here</a></span>");
		pw.println("<br/><br/>");
		pw.println("<span>Contact Email: ngoswamy@hotmail.com");
		pw.println("</body> </html>");
	  }  
	 
	  // Compute the loan payment. 
	  double compute() { 
	    double numer; 
	    double denom; 
	    double b, e; 
	 
	    numer = intRate * principal / payPerYear;  
	 
	    e = -(payPerYear * numYears); 
	    b = (intRate / payPerYear) + 1.0; 
	 
	    denom = 1.0 - Math.pow(b, e); 
	 
	    return numer / denom; 
	  } 

	/*public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		//resp.getWriter().println("Hello, Welcome to Neeraj Goswamy's site");
		resp.getWriter().println("<body><h1>Welcome!!</h1>");
		resp.getWriter().println("<span>This application is developed on Google App Engine</span><br/>");
		resp.getWriter().println("<span>For more click <a href='http://bookshelf.99k.org' target='_blank'>here</a></span>");
		resp.getWriter().println("<br/><br/>");
		resp.getWriter().println("<span>Contact Email: ngoswamy@hotmail.com");
		resp.getWriter().println("</body>");
	} */
}
