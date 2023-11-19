package request_sql;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;

public class SqlRequests {

  public static void executeSqlQueries(Connection connection) {
    executeSqlFile(connection, "V_01_Create_table_book.sql");
    executeSqlFile(connection, "V_02_Create_table_author.sql");
    executeSqlFile(connection, "V_03_Create_table_subscriber.sql");
    executeSqlFile(connection, "V_04_Insert_data_author.sql");
    executeSqlFile(connection, "V_05_Insert_data_book.sql");
    executeSqlFile(connection, "V_06_Insert_data_subscriber.sql");
  }

  private static void executeSqlFile(Connection connection, String fileName) {
    try (InputStream inputStream = SqlRequests.class.getClassLoader().getResourceAsStream("request_sql/" + fileName);
         BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
         Statement statement = connection.createStatement()) {

      StringBuilder queryBuilder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        queryBuilder.append(line).append("\n");
      }

      statement.executeUpdate(queryBuilder.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
