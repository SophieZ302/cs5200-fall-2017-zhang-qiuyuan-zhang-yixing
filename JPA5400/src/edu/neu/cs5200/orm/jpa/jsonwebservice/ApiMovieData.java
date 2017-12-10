package edu.neu.cs5200.orm.jpa.jsonwebservice;

public class ApiMovieData {
	private String title;
	private String directors;
	private String plot;
	private String actors;
	private String imageUrl;
	private Double rating;
	private String id;
	
	@Override
	public String toString() {
		return "ApiMovieData [id= " + id + ",title=" + title + ", directors=" + directors + ", plot=" + plot + ", actors=" + actors
				+ ", imageUrl=" + imageUrl + ", rating=" + rating + "]";
	}

	public ApiMovieData() {}
	
	public String getId() {
		return id;
	}
	public void setId (String inputId) {
		this.id = inputId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirectors() {
		return directors;
	}
	public void setDirectors(String directors) {
		this.directors = directors;
	}
	public String getPlot() {
		return plot;
	}
	public void setPlot(String plot) {
		this.plot = plot;
	}
	public String getActors() {
		return actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getRating() {
		return Double.valueOf(rating);
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	

}
