package com.library.services;

import java.util.ArrayList;
import java.util.List;

import com.library.entities.Client;


public class CommandeForm {
	
	private Client client = new Client();
	private List<OrderProduct> products = new ArrayList<>();
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	public List<OrderProduct> getProducts() {
		return products;
	}
	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}

}
