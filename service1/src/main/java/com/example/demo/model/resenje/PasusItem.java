package com.example.demo.model.resenje;

public class PasusItem {
	
	private ItemType type;
	private String value;
	private String clan;
	private String stav;
	
	public PasusItem() {
		super();
	}
	
	public PasusItem(ItemType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

	public PasusItem(ItemType type, String value, String clan, String stav) {
		super();
		this.type = type;
		this.value = value;
		this.clan = clan;
		this.stav = stav;
	}
	
	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getClan() {
		return clan;
	}

	public void setClan(String clan) {
		this.clan = clan;
	}

	public String getStav() {
		return stav;
	}

	public void setStav(String stav) {
		this.stav = stav;
	}

}
