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

  public Book (String title, String email, String phone, int patron_id) {
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

  public void depleteStock() {
    stock--;
  }

  public static List<Book> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, title FROM books";
      return con.createQuery(sql)
                .executeAndFetch(Book.class);
    }
  }


}
