package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/**
 * Entity implementation class for Entity: Movie
 *
 */
@Entity
@XmlRootElement
public class Movie implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private int regularReview;
	private int cretiqueReview;
	
	@ManyToOne
	private MovieLibrary library;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "MOVIE2ACTOR")
	private List<Actor> actors = null;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "MOVIE2DIRECTOR")
	private List<Director> directors = null;
	
	private static final long serialVersionUID = 1L;
	
	
	@XmlTransient public List<Director> getDirectors() {
		if (directors == null) {
			directors = new ArrayList<Director>();
		}
		return directors;
	}
	
	public void setDirectors(List<Director> directors) {
		this.directors = directors;
		for (Director director : directors) {
			director.getMovies().add(this);
		}
	}
	
	
	//get actors for this movie
	@XmlTransient  public List<Actor> getActors() {
		if (actors == null) {
			actors = new ArrayList<Actor>();
		}
		return actors;
	}

	//add movies to actor
	public void setActors(List<Actor> actors) {
		this.actors = actors;
		for (Actor actor : actors) {
			actor.getMovies().add(this);
		}
	}

	public Movie() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRegularReview() {
		return regularReview;
	}

	public void setRegularReview(int regularReview) {
		this.regularReview = regularReview;
	}

	public int getCretiqueReview() {
		return cretiqueReview;
	}

	public void setCretiqueReview(int cretiqueReview) {
		this.cretiqueReview = cretiqueReview;
	}

}
