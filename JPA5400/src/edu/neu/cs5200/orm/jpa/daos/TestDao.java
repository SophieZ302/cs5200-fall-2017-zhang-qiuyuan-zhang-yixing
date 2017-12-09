package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Critique;
import edu.neu.cs5200.orm.jpa.entities.Director;
import edu.neu.cs5200.orm.jpa.entities.Movie;

public class TestDao {
	public static void main(String[] args) {
		MovieDao movieDao = new MovieDao();
		ActorDao actorDao = new ActorDao();
		DirectorDao directorDao = new DirectorDao();
		
		CritiqueDao critiqueDao = new CritiqueDao();
		RegularDao regularDao = new RegularDao();
		ProducerDao producerDao = new ProducerDao();
		
		CommentDao commentDao = new CommentDao();

		// a. Remove all.
		movieDao.deleteAllMovie();
		actorDao.deleteAllActors();
		directorDao.deleteAllDirector();
		critiqueDao.deleteAllCritiques();
		regularDao.deleteAllRegulars();
		producerDao.deleteAllProducers();

		// b. Create a movie.
		Movie blade = new Movie("Blade Runner");
		movieDao.createMovie(blade);
		Actor har = new Actor("Harrison", "Ford");
		actorDao.createActor(har);
		Actor rut = new Actor("Rutger", "Hauer");
		actorDao.createActor(rut);
		Director rid = new Director("Ridley", "Scott");
		directorDao.createDirector(rid);
		movieDao.addActor(blade, har);
		movieDao.addActor(blade, rut);
		movieDao.addDirector(blade, rid);
		
		// c. Create a movie.
		Movie raiders = new Movie("Raiders of The Lost Ark");
		movieDao.createMovie(raiders);
		Actor kar = new Actor("Karen", "Allen");
		actorDao.createActor(kar);
		Director ste = new Director("Steven", "Spielberg");
		directorDao.createDirector(ste);
		movieDao.addActor(raiders, har);
		movieDao.addActor(raiders, kar);
		movieDao.addDirector(raiders, ste);
		
		// d. Create a movie.
		Movie close = new Movie("Close ENcounters of the Third Kind");
		movieDao.createMovie(close);
		Actor ric = new Actor("Richard", "Dreyfus");
		actorDao.createActor(ric);
		Actor mel = new Actor("Melinda", "Dillon");
		actorDao.createActor(mel);
		movieDao.addActor(close, ric);
		movieDao.addActor(close, mel);
		movieDao.addDirector(close, ste);
		
		// e. Create a Critique
		Critique c1 = new Critique("cri1", "pass", "cri1@test.com", "Christ", "Yik");
	}

}
