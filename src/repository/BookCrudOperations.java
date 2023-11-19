package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Book;

public class BookCrudOperations implements CrudOperations<Book> {

  private Connection connection;

  public BookCrudOperations(Connection connection) {
    this.connection = connection;
  }

  @Override
  public List<Book> findAll() {
    List<Book> books = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book")) {
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        // Créez des objets Book à partir des résultats de la requête et ajoutez-les à la liste
        Book book = createBookFromResultSet(resultSet);
        books.add(book);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return books;
  }

  @Override
  public List<Book> saveAll(List<Book> toSave) {
    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?)")) {
      for (Book book : toSave) {
        // Utilisez le PreparedStatement pour insérer chaque livre dans la base de données
        setPreparedStatementValues(preparedStatement, book);
        preparedStatement.addBatch();
      }
      preparedStatement.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toSave;
  }

  @Override
  public Book save(Book toSave) {
    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?)")) {
      // Utilisez le PreparedStatement pour insérer le livre dans la base de données
      setPreparedStatementValues(preparedStatement, toSave);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toSave;
  }

  @Override
  public Book delete(Book toDelete) {
    try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM book WHERE id = ?")) {
      // Utilisez le PreparedStatement pour supprimer le livre de la base de données
      preparedStatement.setString(1, toDelete.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toDelete;
  }
  // Méthode utilitaire pour créer un objet Book à partir d'un ResultSet
  private Book createBookFromResultSet(ResultSet resultSet) throws SQLException {
    String id = resultSet.getString("id");
    String bookName = resultSet.getString("bookName");
    int pageNumbers = resultSet.getInt("pageNumbers");
    String topicString = resultSet.getString("topic");
    Book.Topic topic = Book.Topic.valueOf(topicString);
    Date releaseDate = resultSet.getDate("releaseDate");
    String author_id = resultSet.getString("author_id");
    boolean available = resultSet.getBoolean("available");

    return new Book(id, bookName, pageNumbers, topic, releaseDate, author_id, available);
  }

  // Méthode utilitaire pour configurer les valeurs d'un PreparedStatement à partir d'un objet Book
  private void setPreparedStatementValues(PreparedStatement preparedStatement, Book book) throws SQLException {
    preparedStatement.setString(1, book.getId());
    preparedStatement.setString(2, book.getBookName());
    preparedStatement.setInt(3, book.getPageNumbers());
    preparedStatement.setString(4, book.getTopic().name());
    preparedStatement.setDate(5, new java.sql.Date(book.getReleaseDate().getTime()));
    preparedStatement.setString(6, book.getAuthor_id());
    preparedStatement.setBoolean(7, book.isAvailable());
  }
}

