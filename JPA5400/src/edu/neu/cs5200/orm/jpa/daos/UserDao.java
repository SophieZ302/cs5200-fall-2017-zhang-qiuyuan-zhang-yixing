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
	
	public int createUser(User user) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(user);
		em.flush();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return user.getId();
	}

	public User findUserById(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		User user = em.find(User.class, id);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return user;
	}

	public List<User> findAllUser() {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select u from User u", User.class);
		List<User> users = query.getResultList();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return users;
	}

	public void updateUser(int id, User newUser) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		User old = em.find(User.class, id);
		old.setUsername(newUser.getUsername());
		old.setPassword(newUser.getPassword());
		old.setEmail(newUser.getEmail());
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

	public void deleteAllUsers() {
		List<User> list = findAllUser();
		for (User user : list) {
			deleteUser(user.getId());
		}
	}

	public void deleteUser(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(em.find(User.class, id));
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public boolean isLike(int id1, int id2) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		boolean res = false;
		User u1 = em.find(User.class, id1);
		User u2 = em.find(User.class, id2);
		if (u1.getLikeList().contains(u2)) {
			res = true;
		}
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return res;
	}
	
	
	
	public static void main(String[] args) {
		UserDao dao = new UserDao();
//		User u = dao.getUserByUserNamePassword("cri1", "pass");
//		System.out.println(u);
		
//		dao.like(1, 2);
		System.out.println(dao.isLike(2, 4));
		
	}
	
}
