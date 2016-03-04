package org.camunda.bpm.extension.flyway;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.extension.flyway.CamundaFlyway.CONFIGURATION;

public class CamundaFlywayTest {

  private final DataSource dataSource = TestUtils.newH2DataSource();

  private Flyway flyway;

  @Before
  public void setUp() {
    flyway = CamundaFlyway.create().withDatasource(dataSource).get();
  }

  @Test
  public void migrates_database() throws Exception {
    flyway.migrate();

    Set<String> tableNames = tableNames();

    assertThat(tableNames).contains("ACT_RU_TASK");
    assertThat(tableNames).hasSize(32);
  }

  private Set<String> tableNames() throws Exception {

    Statement st = null;
    ResultSet rs = null;
    try  {
      st = dataSource.getConnection().createStatement();
      rs = st.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = SCHEMA()");
        final Set<String> names = new TreeSet<String>();
        while (rs.next()) {

          String name = rs.getString("TABLE_NAME");
          if (!name.equals("schema_version")) {
            names.add(name);
          }
        }
        return names;
      }
    finally {
      if (rs != null) rs.close();
      if (st != null) st.close();
    }
  }

}
