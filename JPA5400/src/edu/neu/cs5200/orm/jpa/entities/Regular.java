package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Regular
 */
@Entity

public class Regular extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	public Regular() {
		super();
	}

	public Regular(String username, String password, String email) {
		super(username, password, email);
	}
   
}
