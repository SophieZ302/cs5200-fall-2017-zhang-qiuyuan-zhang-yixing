package edu.neu.cs5200.orm.jpa.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import edu.neu.cs5200.orm.jpa.entities.Movie;
import edu.neu.cs5200.orm.jpa.entities.Producer;

public class ProducerDao extends UserDao {

	public ProducerDao() {
		super();
	}
	
	public int createProducer(Producer producer) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(producer);
		em.flush();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return producer.getId();
	}
	
	public void produces(int pid, int mid) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Producer producer = em.find(Producer.class, pid);
		Movie movie = em.find(Movie.class, mid);
		movie.setProducer(producer);
		producer.getMovies().add(movie);
		em.merge(producer);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public Producer findProducerById(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Producer producer = em.find(Producer.class, id);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return producer;
	}
	
	public List<Producer> findAllProducer() {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select p from Producer p", Producer.class);
		List<Producer> producers = query.getResultList();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return producers;
	}
	
	public void updateProducer(int id, Producer newProducer) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Producer old = em.find(Producer.class, id);
		old.setUsername(newProducer.getUsername());
		old.setPassword(newProducer.getPassword());
		old.setEmail(newProducer.getEmail());
		old.setCompanyName(newProducer.getCompanyName());
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
	
	public void deleteAllProducers() {
		List<Producer> list = findAllProducer();
		for (Producer producer : list) {
			deleteProducer(producer.getId());
		}
	}

	public void deleteProducer(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(em.find(Producer.class, id));
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public static void main(String[] args) {
		ProducerDao dao = new ProducerDao();
		Producer p1 = new Producer("pro1", "pass", "123@qwe.com", "movie com");
		Producer p2 = new Producer("pro2", "pass", "123@qwe.com", "movie com");
		dao.createProducer(p1);
		dao.createProducer(p2);
//		dao.deleteAllProducers();
		System.out.println(dao.findAllProducer());
	}

}
