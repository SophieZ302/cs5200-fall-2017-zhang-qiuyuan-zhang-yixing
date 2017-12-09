package edu.neu.cs5200.orm.jpa.entities;

import edu.neu.cs5200.orm.jpa.entities.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import javax.xml.bind.annotation.*;

/**
 * Entity implementation class for Entity: Actor
 *
 */
@Entity
@XmlRootElement
public class Actor extends Person implements Serializable {

	private int oscarNominations;
	private static final long serialVersionUID = 1L;

	@ManyToMany(mappedBy = "actors", cascade = CascadeType.ALL)
	private List<Movie> moviesActed = null;

	@XmlTransient
	public List<Movie> getMovies() {
		if (moviesActed == null) {
			moviesActed = new ArrayList<Movie>();
		}
		return moviesActed;
	}

	public void setMovies(List<Movie> movies) {
		this.moviesActed = movies;
		for (Movie movie : movies) {
			movie.getActors().add(this);
		}
	}

	public int getOscarNominations() {
		return oscarNominations;
	}

	public void setOscarNominations(int oscarNominations) {
		this.oscarNominations = oscarNominations;
	}

	public Actor() {
		super();
	}

	public Actor(String firstName, String lastName) {
		super(firstName, lastName);
	}

}
