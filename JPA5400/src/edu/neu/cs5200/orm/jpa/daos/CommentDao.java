package edu.neu.cs5200.orm.jpa.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import edu.neu.cs5200.orm.jpa.entities.Comment;
import edu.neu.cs5200.orm.jpa.entities.Movie;
import edu.neu.cs5200.orm.jpa.entities.Producer;
import edu.neu.cs5200.orm.jpa.entities.User;

public class CommentDao extends BaseDao {

	public CommentDao() {
		super();
	}
	
	public int createComment(Comment comment, int userId, int movieId) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		User user = em.find(User.class, userId);
		Movie movie = em.find(Movie.class, movieId);
		comment.setUser(user);
		comment.setMovie(movie);
		em.persist(comment);
		em.flush();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return comment.getId();
	}
	
	public Comment findCommentById(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Comment comment = em.find(Comment.class, id);
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return comment;
	}
	
	public List<Comment> findAllComment() {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select c from Comment c", Comment.class);
		List<Comment> comments = query.getResultList();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return comments;
	}
	
	public void updateComment(int id, Comment newComment) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Comment old = em.find(Comment.class, id);
		old.setRate(newComment.getRate());
		old.setContent(newComment.getContent());
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
	
	public void deleteAllComments() {
		List<Comment> list = findAllComment();
		for (Comment comment : list) {
			deleteComment(comment.getId());
		}
	}

	public void deleteComment(int id) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(em.find(Comment.class, id));
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public List<Comment> getCommentsbyMovieId(Movie movie) {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("select c from Comment c where c.movie =:id ", Comment.class).setParameter("id", movie);
		List<Comment> comments = query.getResultList();
		try {
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return comments;
	}
	
	public static void main(String[] args) {
		CommentDao dao = new CommentDao();
		Comment c1 = new Comment("good", 4);
		dao.createComment(c1, 4, 1);
	}
	
}
