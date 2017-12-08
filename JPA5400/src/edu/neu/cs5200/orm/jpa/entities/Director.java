package edu.neu.cs5200.orm.jpa.entities;

import edu.neu.cs5200.orm.jpa.entities.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Director
 *
 */
@Entity

public class Director extends Person implements Serializable {
	
	private int oscarWins;
	
	private static final long serialVersionUID = 1L;
	
	@ManyToMany(mappedBy = "directors", cascade = CascadeType.ALL)
	private List<Movie> moviesDirected = null;

	public List<Movie> getMovies() {
		if (moviesDirected == null) {
			moviesDirected = new ArrayList<Movie>();
		}
		return moviesDirected;
	}

	public void setMovies(List<Movie> movies) {
	       this.moviesDirected = movies;
	       for(Movie movie: movies) {
	    	   		movie.getDirectors().add(this);
	       }
	} 

	public Director() {
		super();
	}   
	public int getOscarWins() {
		return this.oscarWins;
	}

	public void setOscarWins(int oscarWins) {
		this.oscarWins = oscarWins;
	}
   
}
