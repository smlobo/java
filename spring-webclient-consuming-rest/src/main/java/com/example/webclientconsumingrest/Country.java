package com.example.webclientconsumingrest;

public class Country {

	private String name;
	private String currency;
	private String region;

	public Country() {}

	public String getName() {
		return name;
	}

	public String getCurrency() {
		return currency;
	}

	public String getRegion() {
		return region;
	}

	public String toString() {
		return "<" + name + "," + currency + "," + region + ">";
	}
}
