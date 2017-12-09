package edu.neu.cs5200.orm.jpa.daos;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Comment;
import edu.neu.cs5200.orm.jpa.entities.Critique;
import edu.neu.cs5200.orm.jpa.entities.Director;
import edu.neu.cs5200.orm.jpa.entities.Movie;
import edu.neu.cs5200.orm.jpa.entities.Producer;
import edu.neu.cs5200.orm.jpa.entities.Regular;

public class TestDao {

	public static void test1() {
		MovieDao movieDao = new MovieDao();
		ActorDao actorDao = new ActorDao();
		DirectorDao directorDao = new DirectorDao();

		CritiqueDao critiqueDao = new CritiqueDao();
		RegularDao regularDao = new RegularDao();
		ProducerDao producerDao = new ProducerDao();

		CommentDao commentDao = new CommentDao();

		// a. Remove all.
		movieDao.deleteAllMovies();
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

		// e. Create a Critique.
		Critique cri1 = new Critique("cri1", "pass", "cri1@test1.com", "Christ", "Yik");
		critiqueDao.createCritique(cri1);

		// f. Create a Regular user.
		Regular r1 = new Regular("reg1", "pass", "reg1@test1.com");
		regularDao.createRegular(r1);

		// g. Create a Producer.
		Producer p1 = new Producer("pro1", "pass", "pro1@test1.com", "star movie company");
		producerDao.createProducer(p1);

		// h. Create comments.
		Comment excellent = new Comment("excellent", 5);
		Comment good = new Comment("good", 4);
		Comment bad = new Comment("bad", 0);

		commentDao.createComment(excellent, cri1.getId(), blade.getId());
		commentDao.createComment(excellent, cri1.getId(), raiders.getId());
		commentDao.createComment(good, cri1.getId(), close.getId());

		commentDao.createComment(good, r1.getId(), blade.getId());
		commentDao.createComment(bad, r1.getId(), raiders.getId());
		commentDao.createComment(bad, r1.getId(), close.getId());

		commentDao.createComment(excellent, p1.getId(), blade.getId());
		commentDao.createComment(good, p1.getId(), raiders.getId());
		commentDao.createComment(bad, p1.getId(), close.getId());
	}

	public static void main(String[] args) {
		test1();
	}

}
