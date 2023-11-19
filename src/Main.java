import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Author;
import model.Book;
import model.Subscriber;
import repository.AuthorCrudOperations;
import repository.BookCrudOperations;
import repository.SubscriberCrudOperations;
import request_sql.SqlRequests;

public class Main {
  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_RESET = "\u001B[0m";

  public static void main(String[] args) {
    String jdbcUrl = System.getenv("DATABASE_URL");
    String user = System.getenv("DATABASE_USER");
    String password = System.getenv("DATABASE_PASSWORD");

    if (jdbcUrl == null || user == null || password == null) {
      LOGGER.severe(ANSI_GREEN + "Les informations de connexion à la base de données ne sont pas définies." + ANSI_RESET);
      System.exit(1);
    }

    try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
      SqlRequests.executeSqlQueries(connection);

      try {
        testBookCrudOperations(connection);
        testAuthorCrudOperations(connection);
        testSubscriberCrudOperations(connection);
      } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, ANSI_GREEN + "Erreur lors de l'exécution des tests CRUD : " + e.getMessage(), e);
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, ANSI_GREEN + "Erreur lors de la connexion à la base de données : " + e.getMessage(), e);
    }
  }

  private static void testBookCrudOperations(Connection connection) throws SQLException {
    BookCrudOperations bookCrudOperations = new BookCrudOperations(connection);

    List<Book> allBooks = bookCrudOperations.findAll();
    LOGGER.info(ANSI_GREEN + "Liste de tous les livres : " + allBooks + ANSI_RESET);

    Book newBook = new Book("123", "Nouveau Livre", 200, Book.Topic.OTHER, new java.util.Date(), "A123", true);
    Book savedBook = bookCrudOperations.save(newBook);
    LOGGER.info(ANSI_GREEN + "Livre sauvegardé : " + savedBook + ANSI_RESET);

    List<Book> updatedBooks = bookCrudOperations.findAll();
    LOGGER.info(ANSI_GREEN + "Liste de livres après l'ajout : " + updatedBooks + ANSI_RESET);

    Book deletedBook = bookCrudOperations.delete(savedBook);
    LOGGER.info(ANSI_GREEN + "Livre supprimé : " + deletedBook + ANSI_RESET);

    List<Book> finalBooks = bookCrudOperations.findAll();
    LOGGER.info(ANSI_GREEN + "Liste de livres après la suppression : " + finalBooks + ANSI_RESET);
  }

  private static void testAuthorCrudOperations(Connection connection) throws SQLException {
    AuthorCrudOperations authorCrudOperations = new AuthorCrudOperations(connection);

    List<Author> allAuthors = authorCrudOperations.findAll();
    LOGGER.info(ANSI_GREEN + "Liste de tous les auteurs : " + allAuthors + ANSI_RESET);

    Author newAuthor = new Author("A123", "Nouvel Auteur", Author.Sex.M);
    Author savedAuthor = authorCrudOperations.save(newAuthor);
    LOGGER.info(ANSI_GREEN + "Auteur sauvegardé : " + savedAuthor + ANSI_RESET);

    List<Author> updatedAuthors = authorCrudOperations.findAll();
    LOGGER.info(ANSI_GREEN + "Liste d'auteurs après l'ajout : " + updatedAuthors + ANSI_RESET);

    Author deletedAuthor = authorCrudOperations.delete(savedAuthor);
    LOGGER.info(ANSI_GREEN + "Auteur supprimé : " + deletedAuthor + ANSI_RESET);

    List<Author> finalAuthors = authorCrudOperations.findAll();
    LOGGER.info(ANSI_GREEN + "Liste d'auteurs après la suppression : " + finalAuthors + ANSI_RESET);
  }

  private static void testSubscriberCrudOperations(Connection connection) throws SQLException {
    SubscriberCrudOperations subscriberCrudOperations = new SubscriberCrudOperations(connection);

    List<Subscriber> allSubscribers = subscriberCrudOperations.findAll();
    LOGGER.info(ANSI_GREEN + "Liste de tous les abonnés : " + allSubscribers + ANSI_RESET);

    Subscriber newSubscriber = new Subscriber("S123", "Nouvel Abonné", "Reference123");
    Subscriber savedSubscriber = subscriberCrudOperations.save(newSubscriber);
    LOGGER.info(ANSI_GREEN + "Abonné sauvegardé : " + savedSubscriber + ANSI_RESET);

    List<Subscriber> updatedSubscribers = subscriberCrudOperations.findAll();
    LOGGER.info(ANSI_GREEN + "Liste d'abonnés après l'ajout : " + updatedSubscribers + ANSI_RESET);

    Subscriber deletedSubscriber = subscriberCrudOperations.delete(savedSubscriber);
    LOGGER.info(ANSI_GREEN + "Abonné supprimé : " + deletedSubscriber + ANSI_RESET);

    List<Subscriber> finalSubscribers = subscriberCrudOperations.findAll();
    LOGGER.info(ANSI_GREEN + "Liste d'abonnés après la suppression : " + finalSubscribers + ANSI_RESET);
  }
}