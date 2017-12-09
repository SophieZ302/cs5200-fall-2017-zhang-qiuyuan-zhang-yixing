package edu.neu.cs5200.orm.jpa.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import edu.neu.cs5200.orm.jpa.entities.Critique;

public class CritiqueDao extends UserDao {
	public CritiqueDao() {
		super();
	}

	public int createCritique(Critique critique) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(critique);
		em.flush();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return critique.getId();
	}

	public Critique findCritiqueById(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Critique critique = em.find(Critique.class, id);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return critique;
	}

	public List<Critique> findAllCritique() {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select c from Critique c", Critique.class);
		List<Critique> critiques = query.getResultList();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return critiques;
	}

	public void updateCritique(int id, Critique newCritique) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Critique old = em.find(Critique.class, id);
		old.setUsername(newCritique.getUsername());
		old.setPassword(newCritique.getPassword());
		old.setEmail(newCritique.getEmail());
		old.setFirstname(newCritique.getFirstname());
		old.setLastname(newCritique.getLastname());
		em.merge(old);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void deleteAllCritiques() {
		List<Critique> list = findAllCritique();
		for (Critique critique : list) {
			deleteCritique(critique.getId());
		}
	}

	public void deleteCritique(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(em.find(Critique.class, id));
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}
