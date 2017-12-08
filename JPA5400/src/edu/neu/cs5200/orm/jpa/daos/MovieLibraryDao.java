package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Director;
import edu.neu.cs5200.orm.jpa.entities.Movie;
import edu.neu.cs5200.orm.jpa.entities.MovieLibrary;

public class MovieLibraryDao extends BaseDao {

	public MovieLibraryDao() {
		super();
	}

	public int createMovieLibrary(MovieLibrary m1) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(m1);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return m1.getId();
	}

	public MovieLibrary findMovieLibraryById(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		MovieLibrary mvl = em.find(MovieLibrary.class, id);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return mvl;
	}

	public List<MovieLibrary> findAllMovieLibrary() {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select a from MovieLibrary a", MovieLibrary.class);
		List<MovieLibrary> mvl = query.getResultList();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return mvl;
	}

	public void updateMovieLibrary(int id, MovieLibrary libNew) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		MovieLibrary libOld = em.find(MovieLibrary.class, id);
		libOld.setMovies(libNew.getMovies());
		libOld.setName(libNew.getName());
//		em.persist(libOld);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void deleteAllMovieLibrary() {
		List<MovieLibrary> all = findAllMovieLibrary();
		for (MovieLibrary a : all) {
			deleteMovieLibrary(a.getId());
		}
	}

	public void deleteMovieLibrary(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.createQuery("DELETE FROM MovieLibrary act WHERE act.id=:id").setParameter("id", id).executeUpdate();
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
		MovieLibraryDao mlDao = new MovieLibraryDao();
		// a. remove all movieLibrary
		mlDao.deleteAllMovieLibrary();

		// b. create library "Favorite Movies"
		Movie m1 = new Movie();
		m1.setTitle("Star Wars A New Hope");
		Director gl = new Director();
		gl.setFirstName("Gorge");
		gl.setLastName("Lucas");
		List<Director> ld = new ArrayList<>();
		ld.add(gl);

		Actor mh = new Actor();
		mh.setFirstName("Mark");
		mh.setLastName("Hamill");

		Actor cf = new Actor();
		cf.setFirstName("Carrie");
		cf.setLastName("Fisher");
		List<Actor> la = new ArrayList<>();
		la.add(mh);
		la.add(cf);
		m1.setActors(la);
		m1.setDirectors(ld);

		Movie m2 = new Movie();
		m2.setTitle("The Revanant");
		Director ai = new Director();
		ai.setFirstName("Alejandro");
		ai.setLastName("Inarritu");
		List<Director> ld2 = new ArrayList<>();
		ld2.add(ai);

		Actor lda = new Actor();
		lda.setFirstName("Leonardo");
		lda.setLastName("DiCaprio");

		Actor th = new Actor();
		th.setFirstName("Tom");
		th.setLastName("Hardy");
		List<Actor> la2 = new ArrayList<>();
		la2.add(lda);
		la2.add(th);
		m2.setActors(la2);
		m2.setDirectors(ld2);

		List<Movie> listMovie = new ArrayList<>();
		listMovie.add(m1);
		listMovie.add(m2);
		MovieLibrary mvl = new MovieLibrary();
		mvl.setName("Favorite Movies");
		mvl.setMovies(listMovie);
		mlDao.createMovieLibrary(mvl);

		// c. retrieve favorite movies library
		 MovieLibrary fav = mlDao.findMovieLibraryById(mvl.getId());
		 System.out.println(fav.getName());
		 List<Movie> mvs = fav.getMovies();
		 for (Movie m : mvs) {
		 System.out.println(m.getTitle());
		 for (Actor a : m.getActors()) {
		 System.out.println(a.getFirstName() + " " + a.getLastName());
		 }
		 for (Director d : m.getDirectors()) {
		 System.out.println(d.getFirstName() + " " + d.getLastName());
		 }
		 }
		
	}

	public static void main(String[] args) {
		
		test();
	}

}
