package com.shoppingcart.controller;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.shoppingcart.dto.Item;
import com.shoppingcart.dto.Product;

public class ItemManager {

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
	static EntityManager em = emf.createEntityManager();
	static EntityTransaction et = em.getTransaction();
	public static Item getItem(Product product,int quantity) throws ItemException, ItemAlreadyExistsException{
		Query query = em.createQuery("SELECT a from Item a");
		List<Item> Items= query.getResultList();
		for (Item item : Items) {
			if(item.getProduct().equals(product)) {
				throw new ItemAlreadyExistsException();
			}
		}
		
		if(product.getStock()<quantity) {
			throw new ItemException();
		}
		return new Item(product,quantity);
	}
	public static void orderItem(Item item) throws ItemException {
		
		Product product = item.getProduct();
		if(product.getStock()<item.getQuantity()) {
			throw new ItemException();
		}
		product.setStock(product.getStock()-item.getQuantity());
		et.begin();
		em.remove(item);
		em.merge(product);
		et.commit();
	}

}
class ItemAlreadyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ItemAlreadyExistsException() {
		// TODO Auto-generated constructor stub
	}
}
class ItemException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ItemException() {
		// TODO Auto-generated constructor stub
	}
}
