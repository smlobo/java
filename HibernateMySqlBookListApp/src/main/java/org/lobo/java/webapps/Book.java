package org.lobo.java.webapps;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "book_list")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "rating")
    public int rating;
    
    @Column(name = "date_added")
    @Temporal(TemporalType.TIMESTAMP)
    public Date date_added;

    @Column(name = "application")
    public String application;

    public Book() {}

    public Book(String name, int rating, String application) {
        this.name = name;
        this.rating = rating;
        this.date_added = new Date();
        this.application = application;
    }
}
