package edu.neu.cs5200.orm.jpa.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import edu.neu.cs5200.orm.jpa.entities.Movie;
import edu.neu.cs5200.orm.jpa.entities.User;

public class UserDao extends BaseDao {
	public UserDao() {
		super();
	}
	public User getUserByUserNamePassword(String userName, String passWord) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	
		User rst = null;
		Query query = em.createQuery(
				"select a from User a "
				+ "where a.username =:name "
				+ "and a.password =:pass", User.class).setParameter("name", userName)
				.setParameter("pass", passWord);
		List<User> list =  query.getResultList();
		if (list.size() == 0) {
			return null;
		} else  {
			rst = list.get(0);
		}
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return rst;
	}
	
	public void like(int id1, int id2) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		User u1 = em.find(User.class, id1);
		User u2 = em.find(User.class, id2);
		u1.getLikeList().add(u2);
		u2.getLikedBy().add(u1);
		em.merge(u1);
		em.merge(u2);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public void disLike(int id1, int id2) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		User u1 = em.find(User.class, id1);
		User u2 = em.find(User.class, id2);
		u1.getLikeList().remove(u2);
		u2.getLikedBy().remove(u1);
		em.merge(u1);
		em.merge(u2);
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
		UserDao dao = new UserDao();
//		User u = dao.getUserByUserNamePassword("cri1", "pass");
//		System.out.println(u);
		
//		dao.like(1, 2);
		dao.disLike(1, 2);
	}
	
}
