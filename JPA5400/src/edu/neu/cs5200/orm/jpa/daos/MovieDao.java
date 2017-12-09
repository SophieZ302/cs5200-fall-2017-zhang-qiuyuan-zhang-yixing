package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Comment;
import edu.neu.cs5200.orm.jpa.entities.Director;
import edu.neu.cs5200.orm.jpa.entities.Movie;

public class MovieDao extends BaseDao {
	public MovieDao() {
		super();
	}

	public int createMovie(Movie movie) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.flush();	
		em.persist(movie);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return movie.getId();
	}

	public Movie findMovieById(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Movie movie = em.find(Movie.class, id);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return movie;
	}

	public List<Movie> findAllMovie() {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select a from Movie a", Movie.class);
		@SuppressWarnings("unchecked")
		List<Movie> movies = (List<Movie>)query.getResultList();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return movies;
	}

	public void updateMovie(int id, Movie newMovie) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Movie oldMovie = em.find(Movie.class, id);
		oldMovie.setTitle(newMovie.getTitle());
		oldMovie.setActors(newMovie.getActors());
		oldMovie.setDirectors(newMovie.getDirectors());

//		em.persist(oldMovie);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void deleteAllMovie() {
		List<Movie> all = findAllMovie();
		for (Movie a : all) {
			deleteMovie(a.getId());
		}
	}

	public void deleteMovie(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.createQuery("DELETE FROM Movie act WHERE act.id=:id").setParameter("id",id).executeUpdate();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	

	public static void test() {
		MovieDao movieDao = new MovieDao();		
		//a. remove all movies
		movieDao.deleteAllMovie();

		ActorDao aDao = new ActorDao();
		DirectorDao dDao = new DirectorDao();
		// create all directors and actors
		Director rs = new Director();
		rs.setFirstName("Ridley");
		rs.setLastName("Scott");
		dDao.createDirector(rs);

		Director ss = new Director();
		ss.setFirstName("Steven");
		ss.setLastName("Spielberg");
		dDao.createDirector(ss);

		// Harrison Ford
		Actor hf = new Actor();
		hf.setFirstName("Harrison");
		hf.setLastName("Ford");
		aDao.createActor(hf);

		// Rutger Huaer
		Actor rh = new Actor();
		rh.setFirstName("Rutger");
		rh.setLastName("Hauer");
		aDao.createActor(rh);

		// Karren Allen
		Actor ka = new Actor();
		ka.setFirstName("Karren");
		ka.setLastName("Allen");
		aDao.createActor(ka);

		// Richard Dreyfus
		Actor rd = new Actor();
		rd.setFirstName("Richard");
		rd.setLastName("Dreyfus");
		aDao.createActor(rd);

		// Melinda Dillon
		Actor md = new Actor();
		md.setFirstName("Melinda");
		md.setLastName("Dillon");
		aDao.createActor(md);
		

		//b. create movie Blade Runner
		// actors: Harrison Ford, Rutger Hauer
		// director: Ridley Scott
		ArrayList<Actor> aList = new ArrayList<>();
		aList.add(hf);
		aList.add(rh);
		ArrayList<Director> dList = new ArrayList<>();
		dList.add(rs);
		Movie br = new Movie();
		br.setTitle("Blade Runner");
		br.setDirectors(dList);
		br.setActors(aList);
		movieDao.createMovie(br);/*

		//c. create movie Raiders of The Lost Ark
		// actors: Harrison Ford, Karen Allen
		// director: Steven Spielberg
		ArrayList<Actor> a2List = new ArrayList<>();
		a2List.add(hf);
		a2List.add(ka);
		ArrayList<Director> d2List = new ArrayList<>();
		d2List.add(ss);
		
 		//TODO: every movie creates new person.		
		Movie mv2 = new Movie();
		mv2.setTitle("Raiders of The Lost Ark");
		mv2.setDirectors(d2List);
		mv2.setActors(a2List);
		movieDao.createMovie(mv2);
		

		//d. create movie Close Encounters of the Third Kind
		// actors: Richard Dreyfus, Melinda Dillon
		// director: Steven Spielberg
		ArrayList<Actor> a3List = new ArrayList<>();
		a3List.add(rd);
		a3List.add(md);
		ArrayList<Director> d3List = new ArrayList<>();
		d3List.add(ss);
		Movie mv3 = new Movie();
		mv3.setTitle("Close Encounters of the Third Kind");
		mv3.setDirectors(d3List);
		mv3.setActors(a3List);
		movieDao.createMovie(mv3);
		
		//e. retrieve all movies
		List<Movie> list = movieDao.findAllMovie();
		Set<String> movies = new HashSet<String>();
		System.out.println(list.size());
		for(Movie m : list) {
			if (!movies.contains(m.getTitle())) {
				movies.add(m.getTitle());	
			System.out.println(m.getTitle());
			//System.out.println("Actors: ");
			for(Actor a : m.getActors()) {
				System.out.println(a.getFirstName() + " " + a.getLastName());
			}
			//System.out.println("Director: ");
			for (Director d : m.getDirectors()) {
				System.out.println(d.getFirstName() + " " + d.getLastName());
			}
			//System.out.println();
			}
		}
		
		
		//f. retrieve all Harrison Ford  movies
		ActorDao actorDao = new ActorDao();
		Actor harrison = actorDao.findActorById(hf.getId());
		System.out.println(harrison.getFirstName() + " " + harrison.getLastName());
		for(Movie m : harrison.getMovies()) {
			System.out.println(m.getTitle());
		}
		
		//g. retrieve all Steven Spilberg movies
		DirectorDao directorDao = new DirectorDao();
		Director spilberg = directorDao.findDirectorById(ss.getId());
		System.out.println(spilberg.getFirstName() + " " + spilberg.getLastName());
		
		Set<String> mv = new HashSet<String>();
		for(Movie m : spilberg.getMovies()) {
				mv.add(m.getTitle());
				System.out.println(m.getTitle());
		}*/
		
	}

	public static void main(String[] args) {
//		test();
		MovieDao dao = new MovieDao();
		Movie m1 = dao.findMovieById(1);
		System.out.println(m1.getRegularRate());
		System.out.println(m1.getCritiqueRate());
	}

}
