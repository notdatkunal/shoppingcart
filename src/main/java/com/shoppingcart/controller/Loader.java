package com.shoppingcart.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;



public class Loader {

	

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		System.out.println("hi".isEmpty());
		

	}

}
