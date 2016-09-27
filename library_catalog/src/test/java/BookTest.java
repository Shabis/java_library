import org.sql2o.*;
import org.junit.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class BookTest {
  private Book testBook;
  private Book nextBook;

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void intialize() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_catalog_test", null, null);
    testBook = new Book("Title", "Author", "Genre", 1);
    nextBook = new Book("Title2", "Author2", "Genre2", 1);
  }

  @Test
  public void book_instantiatesCorrectly_true() {
    assertTrue(testBook instanceof Book);
  }

  @Test
  public void getTitle_instatiatesCorrectlyWithName_true() {
    assertEquals("Title", testBook.getTitle());
  }

  @Test
  public void getAuthor_instatiatesCorrectlyWithAuthor_true() {
    assertEquals("Author", testBook.getAuthor());
  }

  @Test
  public void getGenre_instatiatesCorrectlyWithGenre_true() {
    assertEquals("Genre", testBook.getGenre());
  }

  @Test
  public void getId_instatiatesCorrectlyWithId_true() {
    assertEquals("Id", testBook.getId());
  }

  @Test
  public void getPatronId_instatiatesCorrectlyWithPatronId_true() {
    assertEquals(1, testBook.getPatronId());
  }

  @Test
  public void getStock_instatiatesCorrectlyWithStock_true() {
    assertEquals(3, testBook.getStock());
  }



}
