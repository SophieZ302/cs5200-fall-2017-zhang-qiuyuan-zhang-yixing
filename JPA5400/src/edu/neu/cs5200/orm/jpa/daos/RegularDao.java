package edu.neu.cs5200.orm.jpa.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import edu.neu.cs5200.orm.jpa.entities.Regular;

public class RegularDao extends UserDao {

	public RegularDao() {
		super();
	}

	public int createRegular(Regular regular) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(regular);
		em.flush();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return regular.getId();
	}

	public Regular findRegularById(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Regular regular = em.find(Regular.class, id);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return regular;
	}

	public List<Regular> findAllRegular() {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select r from Regular r", Regular.class);
		List<Regular> regulars = query.getResultList();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return regulars;
	}

	public void updateRegular(int id, Regular newRegular) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Regular old = em.find(Regular.class, id);
		old.setUsername(newRegular.getUsername());
		old.setPassword(newRegular.getPassword());
		old.setEmail(newRegular.getEmail());
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

	public void deleteAllRegulars() {
		List<Regular> list = findAllRegular();
		for (Regular regular : list) {
			deleteRegular(regular.getId());
		}
	}

	public void deleteRegular(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(em.find(Regular.class, id));
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
