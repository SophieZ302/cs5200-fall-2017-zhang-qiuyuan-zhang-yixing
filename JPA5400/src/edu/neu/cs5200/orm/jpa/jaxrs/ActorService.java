package edu.neu.cs5200.orm.jpa.jaxrs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import javax.ws.rs.Path;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import edu.neu.cs5200.orm.jpa.daos.ActorDao;
import edu.neu.cs5200.orm.jpa.daos.BaseDao;
import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Movie;

@Path("/actor")
public class ActorService {
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Actor> getAllActors() {
		ActorDao ad = new ActorDao();
		return ad.findAllActor();
	}
	

	
	@GET
	@Path("/{aid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Actor getActorById(@PathParam(value = "aid") int id) {
		ActorDao ad = new ActorDao();
		return ad.findActorById(id);
	}

	@GET
	@Path("/{aid}/movies")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getMoviesByActorId(@PathParam(value = "aid") int id) {
		List<Movie> rst = new ArrayList<>();
		ActorDao ad = new ActorDao();
		Actor a = ad.findActorById(id);
		rst =  a.getMovies();
		return rst;
	}

}
