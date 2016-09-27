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
    testBook.save();
    assertTrue(testBook.getId() > 0);
  }

  @Test
  public void getPatronId_instatiatesCorrectlyWithPatronId_true() {
    assertEquals(1, testBook.getPatronId());
  }

  @Test
  public void getStock_instatiatesCorrectlyWithStock_true() {
    assertEquals(3, testBook.getStock());
  }

  @Test
  public void depleteStock_depletesStock_2() {
    assertEquals(2, testBook.depleteStock());
  }

  @Test
  public void all_checkIfBookIsContainedInInstance_true() {
    testBook.save();
    nextBook.save();
    assertTrue(Book.all().get(0).equals(testBook));
    assertTrue(Book.all().get(1).equals(nextBook));
  }

  @Test
  public void save_assignsIDToObject() {
    testBook.save();
    Book secondBook = Book.all().get(0);
    assertEquals(testBook.getId(), secondBook.getId());
  }

  @Test
  public void save_savesIntoDatabase_true() {
    testBook.save();
    assertTrue(Book.all().get(0).equals(testBook));
  }



}
