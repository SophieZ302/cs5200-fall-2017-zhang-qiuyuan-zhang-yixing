package edu.neu.cs5200.orm.jpa.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import edu.neu.cs5200.orm.jpa.entities.Actor;

public class ActorDao extends BaseDao {
	public ActorDao() {
		super();
	}

	public int createActor(Actor actor) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(actor);
		em.flush();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return actor.getId();
	}

	public Actor findActorById(int actorId) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Actor actor = em.find(Actor.class, actorId);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return actor;
	}

	public List<Actor> findAllActor() {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select a from Actor a", Actor.class);
		List<Actor> actors = query.getResultList();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return actors;
	}

	public Actor findFirstActor() {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select a from Actor a", Actor.class);
		query.setMaxResults(1).getResultList();
		List<Actor> rst = query.getResultList();
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

	public void updateActorByName(int id, String firstName, String lastName) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		Actor actor = em.find(Actor.class, id);
		actor.setFirstName(firstName);
		actor.setLastName(lastName);
		em.merge(actor);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void updateActor(int id , Actor actorNew) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Actor actorOld = em.find(Actor.class, id);
		actorOld.setFirstName(actorNew.getFirstName());
		actorOld.setLastName(actorNew.getLastName());
		actorOld.setMovies(actorNew.getMovies());
		actorOld.setOscarNominations(actorOld.getOscarNominations());
//		em.persist(actorOld);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void deleteAllActors() {
		List<Actor> all = findAllActor();
		for (Actor a : all) {
			deleteActor(a.getId());
		}
	}

	public void deleteActor(int id ) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.createQuery("DELETE FROM Actor act WHERE act.id=:id").setParameter("id", id).executeUpdate();

		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	/*
	 * Remove all actors. Deletes should cascade to all related records Create the
	 * following 3 actors: Sigorney Weaver, Dan Creg and Jim Carrey Retrieve the
	 * first actor and print to the console Retrieve all actors and print to the
	 * console Change actor Dan Creg's first and last name to Daniel Craig Delete
	 * actor Jim Carrey
	 */
	public static void test() {
		Actor a = new Actor();
		a.setFirstName("Sigorney");
		a.setLastName("Weaver");

		Actor b = new Actor();
		b.setFirstName("Dan");
		b.setLastName("Creg");

		Actor c = new Actor();
		c.setFirstName("Jim");
		c.setLastName("Carrey");

		ActorDao ad = new ActorDao();
		// remove all actors
		ad.deleteAllActors();

		// inserting 3 actors
		ad.createActor(a);
		ad.createActor(b);
		ad.createActor(c);

		// retrieve first actor
		Actor first = ad.findFirstActor();
		System.out.println(first.getFirstName() + " " + first.getLastName());

		// retrieve all actors
		List<Actor> rst = ad.findAllActor();
		for (Actor actor : rst) {
			System.out.println(actor.getFirstName() + " " + actor.getLastName());
		}

		// change Dan Creg => Daniel Craig
		ad.updateActorByName(b.getId(), "Daniel", "Craig");

		// delete Jim Carry
		ad.deleteActor(c.getId());

		// retrieve all actors
		List<Actor> rst2 = ad.findAllActor();
		for (Actor actor : rst2) {
			System.out.println(actor.getFirstName() + " " + actor.getLastName());
		}
	}

	public static void main(String[] arg) {
		test();
	}
}
