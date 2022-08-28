package org.example.reactiverestservice;

public class Country {

	private final String name;
	private final String currency;
	private final String region;

	public Country(String name, String currency, String region) {
		this.name = name;
		this.currency = currency;
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public String getCurrency() {
		return currency;
	}

	public String getRegion() {
		return region;
	}
}
