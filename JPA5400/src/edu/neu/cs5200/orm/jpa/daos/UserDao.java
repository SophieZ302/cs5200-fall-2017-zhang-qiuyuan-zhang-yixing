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
		rst = (User) query.getSingleResult();
	
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
	
	public static void main(String[] args) {
		UserDao dao = new UserDao();
		User u = dao.getUserByUserNamePassword("cri1", "pass");
		System.out.println(u);
	}
	
}
