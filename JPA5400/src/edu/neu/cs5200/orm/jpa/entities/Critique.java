package edu.neu.cs5200.orm.jpa.entities;

import edu.neu.cs5200.orm.jpa.entities.User;
import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Critique
 *
 */
@Entity

public class Critique extends User implements Serializable {
	
	private String firstname;
	private String lastname;
	private static final long serialVersionUID = 1L;

	public Critique() {
		super();
	}   
	
	
	public Critique(String username, String password, String email) {
		super(username, password, email);
		// TODO Auto-generated constructor stub
	}


	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}   
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
   
}
