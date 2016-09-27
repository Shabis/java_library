import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.sql2o.*;

public class Patron {
  private String name;
  private String email;
  private String phone;
  private int id;
  private int booksOut;


  public static final int MAX_CHECKOUT = 4;
  public static final int MIN_CHECKOUT = 0;

  public Patron(String name, String email, String phone) {
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.id = id;
    booksOut = MIN_CHECKOUT;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public int getId() {
    return id;
  }

  public static List<Patron> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name FROM patrons";
      return con.createQuery(sql)
                .executeAndFetch(Patron.class);
    }
  }

  public List<Book> getBooks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books where patron_id = :id";
      return con.createQuery(sql)
                .addParameter("id", this.id)
                .executeAndFetch(Book.class);
    }
  }

  public static Patron find (int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patrons where id = :id";
      Patron patron = con.createQuery(sql)
                           .addParameter("id", id)
                           .executeAndFetchFirst(Patron.class);
      return patron;
    }
  }

  public void update(String email, String phone) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE patrons SET email = :email, phone = :phone WHERE id = :id";
    con.createQuery(sql)
      .addParameter("email", email)
      .addParameter("phone", phone)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM patrons WHERE id = :id;";
      con.createQuery(sql)
         .addParameter("id", id)
         .executeUpdate();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons (name, email, phone) VALUES (:name, :email, :phone)";
      this.id = (int) con.createQuery(sql, true)
                         .addParameter("name", this.name)
                         .addParameter("email", this.email)
                         .addParameter("phone", this.phone)
                         .executeUpdate()
                         .getKey();
    }
  }

  @Override
  public boolean equals(Object otherPatron) {
    if(!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getName().equals(newPatron.getName()) &&
             this.getId() == newPatron.getId();
    }
  }

}
