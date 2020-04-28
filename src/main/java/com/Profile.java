package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Profile extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		if(session!=null) {
		PrintWriter out = response.getWriter();
		String uname=(String) session.getAttribute("uname");
		out.print("<h2>Welcome "+uname+" !</h2><br>");
		
		out.print("<form action=\"/searchDownload\" method=\"post\" > <input type =\"search\" placeholder=\"type name to search\" name=\"searchKey\" required> <input type =\"submit\" value=\"search\"> </form>");
		
		request.getRequestDispatcher("/contactServlet").include(request, response);
		out.print("<br><form action=\"/AddContact.html\" > <input type =\"submit\" value=\"Add contact\"> </form>");
		out.print("<br><form action=\"/update\" method=\"get\"  > <input type =\"submit\" value=\"Update contact\"> </form>");
		out.print("<br><form action=\"/delete\" method=\"get\"  > <input type =\"submit\" value=\"Delete contact\"> </form>");
		out.print("<br><form action=\"/welcome\" method=\"get\" > <input type =\"submit\" value=\"Logout\"> </form>");
		
		
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			}
}