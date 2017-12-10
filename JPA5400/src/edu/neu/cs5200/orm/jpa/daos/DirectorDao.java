package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Director;

public class DirectorDao extends BaseDao{
	public int createDirector(Director director) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(director);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return director.getId();
	}

	public Director findDirectorById(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Director Director = em.find(Director.class, id);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return Director;
	}

	public List<Director> findAllDirector() {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select a from Director a", Director.class);
		List<Director> directors = query.getResultList();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return directors;
	}

	public Director findFirstDirector() {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select a from Director a", Director.class);
		query.setMaxResults(1).getResultList();
		List<Director> rst = query.getResultList();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return rst.get(0);
	}

	public void updateDirectorByName(int id, String firstName, String lastName) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Director director = em.find(Director.class, id);
		director.setFirstName(firstName);
		director.setLastName(lastName);
		em.merge(director);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void updateDirector(int id, Director direNew) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Director direOld = em.find(Director.class, id);
		direOld.setFirstName(direNew.getFirstName());
		direOld.setLastName(direNew.getLastName());
		direOld.setMovies(direNew.getMovies());
		direOld.setOscarWins(direNew.getOscarWins());
		em.persist(direOld);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void deleteAllDirector() {
		List<Director> all = findAllDirector();
		for (Director a : all) {
			deleteDirector(a.getId());
		}
	}

	public void deleteDirector(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		 em.createQuery("DELETE FROM Director act WHERE act.id=:id").setParameter("id",id ).executeUpdate();

		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	public List<Director> getDirectorWithName(String name) {
		List<Director> ds = findAllDirector();
		List<Director> res = new ArrayList<>();
		for (Director a : ds) {
			if (a.getFirstName().toLowerCase().contains(name.toLowerCase())) {
				res.add(a);
			} else if (a.getLastName().toLowerCase().contains(name.toLowerCase())) {
				res.add(a);
			}
		}
		return res;
	}
	
	public static void test() {
		Director a = new Director();
		a.setFirstName("Jimmy");
		a.setLastName("Camaron");

		Director b = new Director();
		b.setFirstName("Steven");
		b.setLastName("Spielberg");

		Director c = new Director();
		c.setFirstName("Ron");
		c.setLastName("Howard");

		DirectorDao ad = new DirectorDao();
		// remove all director
		ad.deleteAllDirector();

		// inserting 3 director
		ad.createDirector(a);
		ad.createDirector(b);
		ad.createDirector(c);

		// retrieve first Director
		Director first = ad.findFirstDirector();
		System.out.println(first.getFirstName() + " " + first.getLastName());

		// retrieve all directors
		List<Director> rst = ad.findAllDirector();
		for (Director Director : rst) {
			System.out.println(Director.getFirstName() + " " + Director.getLastName());
		}

		// change Jimmy Camaron => James Cameron
		 ad.updateDirectorByName(a.getId(), "James", "Cameron");

		// delete Ron Howard
		ad.deleteDirector(c.getId());

		// retrieve all directors
		List<Director> rst2 = ad.findAllDirector();
		for (Director Director : rst2) {
			System.out.println(Director.getFirstName() + " " + Director.getLastName());
		}
	}

	public static void main(String[] arg) {
		test();
	}
}
