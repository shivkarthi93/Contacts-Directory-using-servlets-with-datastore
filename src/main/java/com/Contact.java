package com;

public class Contact {
	String name;
	Long number;
	String email;
	String address;
	public Contact() {
		
	}
	public Contact(String name,Long number, String email,String address) {
		this.name=name;
		this.number=number;
		this.email=email;
		this.address=address;
	}
	public String toString() {
		return "->Name: "+name+" Number: "+number+" Email: "+email+" Address: "+address+" ";
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String toStringForFile() {
		
		return name+","+number+","+email+","+address;
	}
}
