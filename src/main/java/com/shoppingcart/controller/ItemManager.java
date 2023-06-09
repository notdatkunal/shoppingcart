package com.shoppingcart.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import com.shoppingcart.dto.Cart;
import com.shoppingcart.dto.Item;
import com.shoppingcart.dto.Product;

public class ItemManager {

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
	static EntityManager em = emf.createEntityManager();
	static EntityTransaction et = em.getTransaction();
	public static Item getItem(Product product,int quantity,Cart cart) throws ItemException, ItemAlreadyExistsException{
		Query query = em.createQuery("SELECT i FROM Item i ");
		
		List<Item> Items= query.getResultList();
		for (Item item : Items) {
			if(cart.equals(item.getCart()))
			if(item.getProduct().equals(product)) 
				throw new ItemAlreadyExistsException();
			
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
	public static Cart removeFromCart(Cart cart,int id) {
		Item item = em.find(Item.class, id);
		List<Item>items = cart.getItems();
		items.remove(item);
		cart.setItems(items);
		
		et.begin();
		em.merge(cart);
		et.commit();
		
		return cart;
	}
	public static void removeItem(int productId) {
		// TODO Auto-generated method stub
		Item item = em.find(Item.class, productId);
		
		item.setCart(null);
		item.setProduct(null);
		et.begin();
		em.remove(item);
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
