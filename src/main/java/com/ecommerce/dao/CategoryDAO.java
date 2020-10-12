package com.ecommerce.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Category; 

@Repository
@Component
public class CategoryDAO {

	@Autowired
    private SessionFactory sessionFactory;

	private Session session;
	private Transaction txn;
	
	@SuppressWarnings("unchecked")
	public Category getCategoryById(long id) {
		String strId = String.valueOf(id);
		//List<Category> list = this.sessionFactory.getCurrentSession().createQuery("from Category where id=" + strId).list();
		session = this.sessionFactory.getCurrentSession();
		txn = session.beginTransaction();
		Query query = session.createQuery("from Category where id=" + strId);
		List<Category> list = query.list();
		txn.commit();
		if (list.size() > 0)
			return (Category) list.get(0);
		else
			return null;
		
	}
	
	
	public void updateCategory(Category category) {
		String sql = "";
		if (category.getID() == 0) {
			//this.sessionFactory.getCurrentSession().save(category);
			session = this.sessionFactory.getCurrentSession();
			txn = session.beginTransaction();
			session.save(category);
			txn.commit();
		}else if (category.getID() > 0) {
			sql = "update Category set name=:name where " +
					" ID=:id";
			
			
			//Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			session = this.sessionFactory.getCurrentSession();
			txn = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter("name", category.getName());
			
			if (category.getID() > 0)
				query.setParameter("id", category.getID());
			
			
			query.executeUpdate();
			txn.commit();
			
		}
		
	}
	

	public void deleteCategory(long id) {
		// mark all category_id of products with this category to zero before deleting the category row 
		String sql = "";
		sql = "update Product set category_id=0 where category_id=:id";

		//Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		session = this.sessionFactory.getCurrentSession();
		txn = session.beginTransaction();
		Query query = session.createQuery(sql);
		query.setParameter("id", id);
		query.executeUpdate();
		txn.commit();

		sql = "delete from Category where ID=:id";
		//query = this.sessionFactory.getCurrentSession().createQuery(sql);
		session = this.sessionFactory.getCurrentSession();
		txn = session.beginTransaction();
		query = session.createQuery(sql);
		query.setParameter("id", id);
		query.executeUpdate();
		txn.commit();
	}

	@SuppressWarnings("unchecked")
	public List<Category> getAllCategories() {
		//List<Category> list = this.sessionFactory.getCurrentSession().createQuery("from Category order by name").list();
		session = this.sessionFactory.getCurrentSession();
		txn = session.beginTransaction();
		Query query = session.createQuery("from Category order by name");
		List<Category> list = query.list();
		txn.commit();
		return list;
	}
	
}
