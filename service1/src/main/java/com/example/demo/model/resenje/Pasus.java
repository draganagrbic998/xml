package com.example.demo.model.resenje;

import java.util.List;

public class Pasus {
	
	private List<PasusItem> items;

	public Pasus() {
		super();
	}

	public Pasus(List<PasusItem> items) {
		super();
		this.items = items;
	}

	public List<PasusItem> getItems() {
		return items;
	}

	public void setItems(List<PasusItem> items) {
		this.items = items;
	}

}
