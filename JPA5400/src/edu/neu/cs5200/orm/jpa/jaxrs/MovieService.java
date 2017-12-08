package edu.neu.cs5200.orm.jpa.jaxrs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.neu.cs5200.orm.jpa.daos.BaseDao;
import edu.neu.cs5200.orm.jpa.daos.MovieDao;
import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Movie;

@Path("/movie")
public class MovieService {
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getAllDevelopers() {
		List<Movie> movies = new ArrayList<Movie>();		
		MovieDao md = new MovieDao();
		movies = md.findAllMovie();
		return movies;
	}
	
	@GET
	@Path("/{mid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Movie getActorById(@PathParam(value = "mid") int id) {
		MovieDao md = new MovieDao();
		return md.findMovieById(id);
	}

	@GET
	@Path("/{mid}/actors")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Actor> getMoviesByActorId(@PathParam(value = "mid") int id) {
		List<Actor> rst = new ArrayList<>();
		MovieDao md = new MovieDao();
		Movie a = md.findMovieById(id);
		rst = a.getActors();
		return rst;
	}
	
	

}
