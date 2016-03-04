package org.camunda.bpm.extension.flyway;


import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;

public final class TestUtils {

  public static final DataSource newH2DataSource() {
    return JdbcConnectionPool.create("jdbc:h2:mem:camunda-test;DB_CLOSE_DELAY=100;MODE=Oracle", "sa", "");
  }
}
