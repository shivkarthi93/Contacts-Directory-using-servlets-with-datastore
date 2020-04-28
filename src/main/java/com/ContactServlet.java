package com;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.google.appengine.api.datastore.Query.FilterOperator;

public class ContactServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		
		try {
		String uid = session.getAttribute("uid").toString();
		
		out.print("<h4>Contacts List:</h4><br>");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		

		

			Query query = new Query("Contacts").addFilter("UserId", FilterOperator.EQUAL, uid);
			PreparedQuery results = datastore.prepare(query);
			boolean flag=false;
			for (Entity entity : results.asIterable()) {
				flag=true;
				out.println("<h4>Contact Key:" + entity.getKey().toString() + "  :Name:" + entity.getProperty("Name")
						+ "  :Number:" + entity.getProperty("Number") + "  :Email:" + entity.getProperty("Email")
						+ " :Address:" + entity.getProperty("Address") + "</h4>");

			}
			if(!flag) {
				out.println("<h4>No contacts</h4>");
			}

		} catch (Exception e) {
			out.print("<h3>" + e + " Try again!</h3><br>");
			out.print("<form action=\"/welcome\" method=\"get\" > <input type =\"submit\" value=\"Logout\"> </form>");
			out.print("<form action=\"/profile\" method=\"get\" > <input type =\"submit\" value=\"Profile\"> </form>");

		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name;
		String number;
		String email;
		String address;

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity user;
		PrintWriter out = response.getWriter();
		String uid = session.getAttribute("uid").toString();
		String uname=session.getAttribute("uname").toString();
		name = request.getParameter("name");
		number = request.getParameter("number");
		email = request.getParameter("email");
		address = request.getParameter("address");
		try {
			
			
				Query contactQuery=new Query("Contacts").addFilter("UserId", FilterOperator.EQUAL, uid).addFilter("Email", FilterOperator.EQUAL, email);
				Entity contacts = datastore.prepare(contactQuery).asSingleEntity();
				if(contacts!=null) {
				throw new Exception("<h4>Contact name already exist ! </h4><br>");
				}
		

				if (name == null || number == null || email == null || address == null) {
					throw new Exception("<h4>Please enter valid inputs!!!");

				} else {
					String contactId=Integer.toString((name+email).hashCode())+address.length();
					contacts = new Entity("Contacts",contactId);
					contacts.setProperty("UserId",uid);
					contacts.setProperty("Name", name);
					contacts.setProperty("Number", number);
					contacts.setProperty("Email", email);
					contacts.setProperty("Address", address);
					datastore.put(contacts);

					response.sendRedirect("/profile");
					out.println("contacts added!");
					
				}
			

		} catch (Exception e) {
			out.print("<h4>" + e + " Try again!!</h4>");
			out.print("<form action=\"/AddContact.html\" > <input type =\"submit\" value=\"Add contact\"> </form>");
			out.print("<form action=\"/welcome\" method=\"get\" > <input type =\"submit\" value=\"Logout\"> </form>");
			out.print("<form action=\"/profile\" method=\"get\" > <input type =\"submit\" value=\"Profile\"> </form>");
		}
	}
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		String uid = session.getAttribute("uid").toString();
		String uname=session.getAttribute("uname").toString();
        String deleteName=request.getParameter("searchKey");
		try {
		
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		

		

			Query query = new Query("Contacts").addFilter("UserId", FilterOperator.EQUAL, uid).addFilter("Name", FilterOperator.EQUAL, deleteName);
			 
			Entity contact=datastore.prepare(query).asSingleEntity();
			if(contact==null) {
				throw new Exception("Contact:"+deleteName+" is not present!");
			}
			datastore.delete(contact.getKey());
			response.sendRedirect("/profile");
		} catch (Exception e) {
			out.print("<h3>" + e + " Try again!</h3><br>");
			out.print("<form action=\"/welcome\" method=\"get\" > <input type =\"submit\" value=\"Logout\"> </form>");

		}
	}
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		String uid = session.getAttribute("uid").toString();
		String uname=session.getAttribute("uname").toString();
        String key=request.getParameter("searchKey");
        String name;
		String number;
		String email;
		String address;
		try {
		
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		

		

			Query query = new Query("Contacts").addFilter("UserId", FilterOperator.EQUAL, uid).addFilter("Name", FilterOperator.EQUAL, key);
			 
			Entity contact=datastore.prepare(query).asSingleEntity();
			if(contact==null) {
				throw new Exception("Contact:"+key+" is not present!");
			}
			
			name = request.getParameter("name");
			number = request.getParameter("number");
			email = request.getParameter("email");
			address = request.getParameter("address");
			contact.setProperty("Name", name);
			contact.setProperty("Number", number);
			contact.setProperty("Email", email);
			contact.setProperty("Address", address);
			datastore.put(contact);
			response.sendRedirect("/profile");
		} catch (Exception e) {
			out.print("<h3>" + e + " Try again!</h3><br>");
			out.print("<form action=\"/welcome\" method=\"get\" > <input type =\"submit\" value=\"Logout\"> </form>");

		}
	}
}
