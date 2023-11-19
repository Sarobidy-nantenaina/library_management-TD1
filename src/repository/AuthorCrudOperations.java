package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Author;

public class AuthorCrudOperations implements CrudOperations<Author> {

  private Connection connection;

  public AuthorCrudOperations(Connection connection) {
    this.connection = connection;
  }

  @Override
  public List<Author> findAll() {
    List<Author> authors = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM author")) {
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Author author = createAuthorFromResultSet(resultSet);
        authors.add(author);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return authors;
  }

  @Override
  public List<Author> saveAll(List<Author> toSave) {
    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO author VALUES (?, ?)")) {
      for (Author author : toSave) {
        setPreparedStatementValues(preparedStatement, author);
        preparedStatement.addBatch();
      }
      preparedStatement.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toSave;
  }

  @Override
  public Author save(Author toSave) {
    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO author VALUES (?, ?, ?)")) {
      setPreparedStatementValues(preparedStatement, toSave);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toSave;
  }

  @Override
  public Author delete(Author toDelete) {
    try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM author WHERE id = ?")) {
      preparedStatement.setString(1, toDelete.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toDelete;
  }

  private Author createAuthorFromResultSet(ResultSet resultSet) throws SQLException {
    String id = resultSet.getString("id");
    String authorName = resultSet.getString("authorName");
    // Assuming Sex is an enum with values MALE and FEMALE
    Author.Sex sex = Author.Sex.valueOf(resultSet.getString("sex"));

    return new Author(id, authorName, sex);
  }

  private void setPreparedStatementValues(PreparedStatement preparedStatement, Author author) throws SQLException {
    preparedStatement.setString(1, author.getId());
    preparedStatement.setString(2, author.getAuthorName());
    preparedStatement.setString(3, author.getSex().name());
  }
}

