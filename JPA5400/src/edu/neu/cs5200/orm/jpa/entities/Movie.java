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

	@ManyToOne
	private MovieLibrary library;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "MOVIE2ACTOR")
	private List<Actor> actors = null;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "MOVIE2DIRECTOR")
	private List<Director> directors = null;

	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	private static final long serialVersionUID = 1L;

	public Movie(String title) {
		super();
		this.title = title;
	}
	
	public double getCritiqueRate() {
		double sum = 0;
		int count = 0;
		for (Comment c : comments) {
			if (c.getUser() instanceof Critique) {
				count++;
				sum += c.getRate();
			}
		}
		if (count == 0) {
			return 0;
		} else {
			return sum / count;
		}
	}
	
	public double getRegularRate() {
		double sum = 0;
		int count = 0;
		for (Comment c : comments) {
			if (!(c.getUser() instanceof Critique)) {
				count++;
				sum += c.getRate();
			}
		}
		if (count == 0) {
			return 0;
		} else {
			return sum / count;
		}
	}
	
	public double getRate() {
		double sum = 0;
		int count = 0;
		for (Comment c : comments) {
			count++;
			sum += c.getRate();
		}
		if (count == 0) {
			return 0;
		} else {
			return sum / count;
		}
	}

	@XmlTransient
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@XmlTransient
	public List<Director> getDirectors() {
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

	// get actors for this movie
	@XmlTransient
	public List<Actor> getActors() {
		if (actors == null) {
			actors = new ArrayList<Actor>();
		}
		return actors;
	}

	// add movies to actor
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
}
