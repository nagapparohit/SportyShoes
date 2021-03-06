package com.ecommerce.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Component;
import com.ecommerce.entity.PurchaseItem; 

@Repository
@Component
public class PurchaseItemDAO {

	@Autowired
    private SessionFactory sessionFactory;

	private Session session;
	
	private Transaction txn;
	
	@SuppressWarnings("unchecked")
	public PurchaseItem getItemById(long id) {
		String strId = String.valueOf(id);
		//List<PurchaseItem> list = this.sessionFactory.getCurrentSession().createQuery("from PurchaseItem where id=" + strId).list();
		session = this.sessionFactory.getCurrentSession();
		txn = session.beginTransaction();
		List<PurchaseItem> list = session.createQuery("from PurchaseItem where id=" + strId).list();
		txn.commit();
		if (list.size() > 0)
			return (PurchaseItem) list.get(0);
		else
			return null;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<PurchaseItem> getAllItemsByPurchaseId(long purchaseId) {
		String strId = String.valueOf(purchaseId);
		//List<PurchaseItem> list = this.sessionFactory.getCurrentSession().createQuery("from PurchaseItem where purchase_id=" + strId).list();
		session = this.sessionFactory.getCurrentSession();
		txn = session.beginTransaction();
		List<PurchaseItem> list = session.createQuery("from PurchaseItem where purchase_id=" + strId).list();
		txn.commit();
		return list;
	}	
	
	public void updateItem(PurchaseItem item) {
		//this.sessionFactory.getCurrentSession().save(item);
		session = this.sessionFactory.getCurrentSession();
		txn = session.beginTransaction();
		session.save(item);
		txn.commit();
		
	}
	

	public void deleteItem(long id) {
		//Pending: Purchase total in purchase table should be updated after this
		String sql = "";
		sql += "delete from PurchaseItem where ID=:id";

		//Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		session = this.sessionFactory.getCurrentSession();
		txn = session.beginTransaction();
		Query query = session.createQuery(sql);
		query.setParameter("id", id);
		query.executeUpdate();
		txn.commit();
		
	}

	public void deleteAllItemsForPurchaseId(long purchaseId) {
		String sql = "";
		sql += "delete from PurchaseItem where purchase_id=:purchase_id";

		//Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		session = this.sessionFactory.getCurrentSession();
		txn = session.beginTransaction();
		Query query = session.createQuery(sql);
		query.setParameter("purchase_id", purchaseId);
		query.executeUpdate();
		txn.commit();
		
	}

	
}
