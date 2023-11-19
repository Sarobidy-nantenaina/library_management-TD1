package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Subscriber;

public class SubscriberCrudOperations implements CrudOperations<Subscriber> {

  private Connection connection;

  public SubscriberCrudOperations(Connection connection) {
    this.connection = connection;
  }

  @Override
  public List<Subscriber> findAll() {
    List<Subscriber> subscribers = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM subscriber")) {
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Subscriber subscriber = createSubscriberFromResultSet(resultSet);
        subscribers.add(subscriber);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return subscribers;
  }

  @Override
  public List<Subscriber> saveAll(List<Subscriber> toSave) {
    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO subscriber VALUES (?, ?, ?)")) {
      for (Subscriber subscriber : toSave) {
        setPreparedStatementValues(preparedStatement, subscriber);
        preparedStatement.addBatch();
      }
      preparedStatement.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toSave;
  }

  @Override
  public Subscriber save(Subscriber toSave) {
    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO subscriber VALUES (?, ?, ?)")) {
      setPreparedStatementValues(preparedStatement, toSave);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toSave;
  }

  @Override
  public Subscriber delete(Subscriber toDelete) {
    try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM subscriber WHERE id = ?")) {
      preparedStatement.setString(1, toDelete.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return toDelete;
  }

  private Subscriber createSubscriberFromResultSet(ResultSet resultSet) throws SQLException {
    String id = resultSet.getString("id");
    String name = resultSet.getString("name");
    String reference = resultSet.getString("reference");

    return new Subscriber(id, name, reference);
  }

  private void setPreparedStatementValues(PreparedStatement preparedStatement, Subscriber subscriber) throws SQLException {
    preparedStatement.setString(1, subscriber.getId());
    preparedStatement.setString(2, subscriber.getName());
    preparedStatement.setString(3, subscriber.getReference());
  }
}

