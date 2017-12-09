package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Comment
 *
 */
@Entity

public class Comment implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String content;
	private int rate;
	@ManyToOne
	private User user;
	@ManyToOne
	private Movie movie;

	private static final long serialVersionUID = 1L;

	public Comment() {
		super();
	}

	public Comment(String content, int rate) {
		super();
		this.content = content;
		this.rate = rate;
	}

	public Comment(String content, int rate, User user, Movie movie) {
		super();
		this.content = content;
		this.rate = rate;
		this.user = user;
		this.movie = movie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}
