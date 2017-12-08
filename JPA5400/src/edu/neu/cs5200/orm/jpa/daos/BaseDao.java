package edu.neu.cs5200.orm.jpa.daos;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BaseDao {
	public static final String UNIT = "JPA5400";
	public static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(UNIT);
}
