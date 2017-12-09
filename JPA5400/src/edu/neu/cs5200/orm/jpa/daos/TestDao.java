package edu.neu.cs5200.orm.jpa.daos;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Admin;
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

		AdminDao adminDao = new AdminDao();
		CritiqueDao critiqueDao = new CritiqueDao();
		RegularDao regularDao = new RegularDao();
		ProducerDao producerDao = new ProducerDao();

		CommentDao commentDao = new CommentDao();

		// a. Remove all.
		movieDao.deleteAllMovies();
		actorDao.deleteAllActors();
		directorDao.deleteAllDirector();
		adminDao.deleteAllUsers();

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

		// e. Create a users.
		Admin admin = new Admin("admin", "admin", "admin@test1.com");
		adminDao.createAdmin(admin);
		
		Regular alice = new Regular("alice", "alice", "alice@test1.com");
		regularDao.createRegular(alice);
		
		Critique bob = new Critique("bob", "bob", "bob@test1.com", "Christ", "Yik");
		critiqueDao.createCritique(bob);

		Producer charlie = new Producer("charlie", "charlie", "charlie@test1.com", "star movie company");
		producerDao.createProducer(charlie);

		// f. Create comments.
		Comment excellent = new Comment("excellent", 5);
		Comment good = new Comment("good", 4);
		Comment bad = new Comment("bad", 0);

		commentDao.createComment(good, alice.getId(), blade.getId());
		commentDao.createComment(bad, alice.getId(), raiders.getId());
		commentDao.createComment(bad, alice.getId(), close.getId());
		
		commentDao.createComment(excellent, bob.getId(), blade.getId());
		commentDao.createComment(excellent, bob.getId(), raiders.getId());
		commentDao.createComment(good, bob.getId(), close.getId());

		commentDao.createComment(excellent, charlie.getId(), blade.getId());
		commentDao.createComment(good, charlie.getId(), raiders.getId());
		commentDao.createComment(bad, charlie.getId(), close.getId());
	}

	public static void main(String[] args) {
		test1();
	}

}
