package org.lobo.java.webapps;

import org.hibernate.Session;

import java.util.List;

public class BookDao {
    static Book getBook(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Book book = session.get(Book.class, (Integer) id);
        session.close();
        return book;
    }

    static List<Book> getBooks(String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sqlQuery = "from org.lobo.java.webapps.Book";
        if (!"".equals(id))
            sqlQuery += " where id = " + id;
        List<Book> books = session.createQuery(sqlQuery).list();
        session.close();
        return books;
    }

    static int addBook(Book book) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        int id = (Integer) session.save(book);

        session.getTransaction().commit();
        session.close();

        return id;
    }

    static void removeBook(Book book) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(book);
        session.flush();
        session.close();
    }

    static Book removeLastBook() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Book> books = session.createQuery("from org.lobo.java.webapps.Book").list();
        Book last = books.get(books.size()-1);
        session.delete(last);
        session.flush();
        session.close();
        return last;
    }
}
