package com.example.restservice;

public class Movie {

	private final String name;
	private final String release;

	public Movie(String name, String release) {
		this.name = name;
		this.release = release;
	}

	public String getName() {
		return name;
	}

	public String getRelease() {
		return release;
	}
}
