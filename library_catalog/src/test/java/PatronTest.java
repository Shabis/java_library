import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class PatronTest {
  private Patron testPatron;
  private Patron nextPatron;

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void initialize() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_catalog_test", null, null);
    testPatron = new Patron ("Jack", "jack@gmail", "555-555-5555");
    nextPatron = new Patron ("Jill", "jill@gmail", "444-777-6666");
  }

  @Test
  public void person_instantiatesCorrectly_true() {
    assertTrue(testPatron instanceof Patron);
  }

  @Test
  public void getName_instantiatesWithCorrectName_String() {
    assertEquals("Jack", testPatron.getName());
  }

  @Test
  public void getEmail_instantiatesWithCorrectEmail_String() {
    assertEquals("jack@gmail", testPatron.getEmail());
  }

  @Test
  public void getPhone_instantiatesWithCorrectPhone_String() {
    assertEquals("555-555-5555", testPatron.getPhone());
  }

  @Test
  public void getId_instantiatesWithCorrectId_True() {
    testPatron.save();
    assertTrue(testPatron.getId() > 0);
  }

  @Test
  public void all_checksIfPatronContainsInstance_true() {
    testPatron.save();
    nextPatron.save();
    assertTrue(Patron.all().get(0).equals(testPatron));
    assertTrue(Patron.all().get(1).equals(nextPatron));
  }

  @Test
  public void getBooks_retrievesAllBooksFromDB_booksList() {
    testPatron.save();
    Book testBook = new Book("Title", "Author", "Genre", testPatron.getId());
    testBook.save();
    Book nextBook = new Book("Title2", "Author2", "Genre2", testPatron.getId());
    nextBook.save();
    Book[] books = new Book[] { testBook, nextBook };
    assertTrue(testPatron.getBooks().containsAll(Arrays.asList(books)));
  }

  @Test
  public void find_returnsPatronWithSameId_nextPatron() {
    testPatron.save();
    nextPatron.save();
    assertEquals(Patron.find(nextPatron.getId()), nextPatron);
  }

  @Test
  public void update_returnUpdatedPatronInformation_true() {
    Patron secondPatron = new Patron("Andre@gmail", "234-523-2352");
    secondPatron.save()
    secondPatron.update("jackc@gmail", "342-928-1384");
    assertEquals("jackc@gmail", Patron.find(secondPatron.getId()).getEmail());
    assertEquals("342-928-1384", Patron.find(secondPatron.getId()).getPhone());
  }

  @Test
  public void delete_deletePatron_true() {
    Patron secondPatron = new Patron("Jack", "jack@gmail", "555-555-5555");
    secondPatron.save();
    int newPatronId = secondPatron.getId();
    secondPatron.delete();
    assertEquals(null, Patron.find(newPatronId));
  }

  @Test
  public void save_assignsIDToObject() {
    testPatron.save();
    Patron savedPatron = Patron.all().get(0);
    assertEquals(testPatron.getId(), savedPatron.getId());
  }

  @Test
  public void save_savesIntoDatabase_true() {
    testPatron.save();
    assertTrue(Patron.all().get(0).equals(testPatron));
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame_true() {
    Patron secondPatron = new Patron ("Jack", "jack@gmail", "555-555-5555");
    assertTrue(testPatron.equals(secondPatron));
  }


}
