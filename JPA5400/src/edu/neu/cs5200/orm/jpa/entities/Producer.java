package edu.neu.cs5200.orm.jpa.entities;

import edu.neu.cs5200.orm.jpa.entities.User;
import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Producer
 *
 */
@Entity

public class Producer extends User implements Serializable {

	private String companyName;
	@OneToMany(mappedBy="producer", cascade=CascadeType.ALL)
	List<Movie> movies = new ArrayList<>();
	
	private static final long serialVersionUID = 1L;

	public Producer() {
		super();
	}

	public Producer(String username, String password, String email) {
		super(username, password, email);
	}

	public Producer(String username, String password, String email, String companyName) {
		super(username, password, email);
		this.companyName = companyName;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
