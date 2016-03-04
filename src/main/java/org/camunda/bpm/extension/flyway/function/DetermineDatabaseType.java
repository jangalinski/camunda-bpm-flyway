package org.camunda.bpm.extension.flyway.function;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotNull;

public class DetermineDatabaseType {

  public static final Properties databaseTypeMappings = new Properties();

  static {
    databaseTypeMappings.setProperty("H2", "h2");
    databaseTypeMappings.setProperty("MySQL", "mysql");
    databaseTypeMappings.setProperty("Oracle", "oracle");
    databaseTypeMappings.setProperty("PostgreSQL", "postgres");
    databaseTypeMappings.setProperty("Microsoft SQL Server", "mssql");
    databaseTypeMappings.setProperty("DB2", "db2");
    databaseTypeMappings.setProperty("DB2", "db2");
    databaseTypeMappings.setProperty("DB2/NT", "db2");
    databaseTypeMappings.setProperty("DB2/NT64", "db2");
    databaseTypeMappings.setProperty("DB2 UDP", "db2");
    databaseTypeMappings.setProperty("DB2/LINUX", "db2");
    databaseTypeMappings.setProperty("DB2/LINUX390", "db2");
    databaseTypeMappings.setProperty("DB2/LINUXX8664", "db2");
    databaseTypeMappings.setProperty("DB2/LINUXZ64", "db2");
    databaseTypeMappings.setProperty("DB2/400 SQL", "db2");
    databaseTypeMappings.setProperty("DB2/6000", "db2");
    databaseTypeMappings.setProperty("DB2 UDB iSeries", "db2");
    databaseTypeMappings.setProperty("DB2/AIX64", "db2");
    databaseTypeMappings.setProperty("DB2/HPUX", "db2");
    databaseTypeMappings.setProperty("DB2/HP64", "db2");
    databaseTypeMappings.setProperty("DB2/SUN", "db2");
    databaseTypeMappings.setProperty("DB2/SUN64", "db2");
    databaseTypeMappings.setProperty("DB2/PTX", "db2");
    databaseTypeMappings.setProperty("DB2/2", "db2");
  }


  public String apply(final DataSource dataSource) {
    Connection connection = null;
    try {
      connection = dataSource.getConnection();
      return apply(connection);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public String apply(final Connection connection) {
    try {
      DatabaseMetaData databaseMetaData = connection.getMetaData();
      String databaseProductName = databaseMetaData.getDatabaseProductName();
      String databaseType = databaseTypeMappings.getProperty(databaseProductName);
      ensureNotNull("couldn't deduct database type from database product name '" + databaseProductName + "'", "databaseType", databaseType);

      return databaseType;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
