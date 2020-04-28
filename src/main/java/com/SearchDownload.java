package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
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
import com.google.appengine.api.datastore.Query.FilterOperator;

public class SearchDownload extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0);
        response.setHeader("Content-Disposition", "attachment; filename=MyContacts.csv") ;
		response.setContentType("text/csv");
		PrintWriter out=response.getWriter();
		
		
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0);
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		String uid = session.getAttribute("uid").toString();
		String uname=session.getAttribute("uname").toString();
        String searchKey=request.getParameter("searchKey");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
        try {
			
			Query query = new Query("Contacts").addFilter("UserId", FilterOperator.EQUAL, uid);
			PreparedQuery results = datastore.prepare(query);
			
			for (Entity entity : results.asIterable()) {
				
				if(entity.getProperty("Name").toString().startsWith(searchKey)) {
					out.println("<h4>Entiy Key:"+entity.getKey()+"  :Name:"+entity.getProperty("Name")+"  :Number:"+entity.getProperty("Number")+"  :Email:"+entity.getProperty("Email")+" :Address:"+entity.getProperty("Address")+"</h4>");
				}
			}
			out.print("<form action=\"/AddContact.html\" > <input type =\"submit\" value=\"Add contact\"> </form>");
			out.print("<br><form action=\"/searchDownload\" method=\"get\" > <input type =\"submit\" value=\"Download Contacts\"> </form>");
			out.print("<form action=\"/welcome\" method=\"get\" > <input type =\"submit\" value=\"Logout\"> </form>");
			out.print("<form action=\"/profile\" method=\"get\" > <input type =\"submit\" value=\"Profile\"> </form>");
		} catch (Exception e) {
			out.print("<h3>something is wrong Try again!<h3><br>");
			out.print("<form action=\"/welcome\" method=\"get\" > <input type =\"submit\" value=\"Logout\"> </form>");
			out.print("<form action=\"/profile\" method=\"get\" > <input type =\"submit\" value=\"Profile\"> </form>");

		}
		
	}
}
