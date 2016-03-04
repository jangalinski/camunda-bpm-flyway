package org.camunda.bpm.extension.flyway.function;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.camunda.bpm.extension.flyway.TestUtils;
import org.junit.Test;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DetermineDatabaseTypeTest {

  private final DetermineDatabaseType function = new DetermineDatabaseType();

  private final DataSource dataSource = TestUtils.newH2DataSource();

  @Test
  public void determine_h2_by_dataSource() {
    assertThat(function.apply(TestUtils.newH2DataSource())).isEqualTo("h2");
  }

  @Test
  public void determine_h2_by_connection() throws SQLException {
    assertThat(function.apply(TestUtils.newH2DataSource().getConnection())).isEqualTo("h2");
  }
}
