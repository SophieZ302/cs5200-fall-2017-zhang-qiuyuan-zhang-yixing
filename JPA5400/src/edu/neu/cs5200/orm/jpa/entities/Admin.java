package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Admin
 *
 */
@Entity

public class Admin extends User implements Serializable {

	private static final long serialVersionUID = 1L;

	public Admin() {
		super();
	}

	public Admin(String username, String password, String email) {
		super(username, password, email);
	}

}
