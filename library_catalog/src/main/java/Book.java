import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.sql2o.*;

public class Book {
  private String title;
  private String author;
  private String genre;
  private int id;
  private int patron_id;
  private int stock;

  public static final int MAX_STOCK = 3;
  public static final int MIN_STOCK = 0;

  public Book (String title, String author, String genre, int patron_id) {
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.patron_id = patron_id;
    this.id = id;
    stock = MAX_STOCK;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getGenre() {
    return genre;
  }

  public int getId() {
    return id;
  }

  public int getPatronId() {
    return patron_id;
  }

  public int getStock() {
    return stock;
  }

  public int depleteStock() {
    stock--;
    return stock;
  }

  public static List<Book> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, title FROM books";
      return con.createQuery(sql)
                .executeAndFetch(Book.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books (title, author, genre, patron_id) VALUES (:title, :author, :genre, :patron_id)";
      this.id = (int) con.createQuery(sql, true)
                         .addParameter("title", this.title)
                         .addParameter("author", this.author)
                         .addParameter("genre", this.genre)
                         .addParameter("patron_id", this.patron_id)
                         .executeUpdate()
                         .getKey();
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books where id = :id";
      Book book = con.createQuery(sql)
                     .addParameter("id", id)
                     .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void update(String title, String author, String genre, int patron_id) {
    try(Connection con = DB.sql2o.open()) {
      String sql =     "UPDATE books SET title = :title, author = :author, genre = :genre, patron_id = :patron_id WHERE id = :id";
      con.createQuery(sql)
         .addParameter("title", title)
         .addParameter("author", author)
         .addParameter("genre", genre)
         .addParameter("patron_id", patron_id)
         .addParameter("id", id)
         .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM books WHERE id = :id";
      con.createQuery(sql)
         .addParameter("id", id)
         .executeUpdate();
    }
  }

  @Override
  public boolean equals (Object otherBook) {
    if(!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) &&
             this.getAuthor().equals(newBook.getAuthor()) &&
             this.getGenre().equals(newBook.getGenre()) &&
             this.getPatronId() == newBook.getPatronId() &&
             this.getId() == newBook.getId();
    }
  }


}
