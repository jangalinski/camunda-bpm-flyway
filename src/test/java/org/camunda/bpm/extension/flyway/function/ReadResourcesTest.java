package org.camunda.bpm.extension.flyway.function;

import org.camunda.bpm.extension.flyway.CamundaFlyway;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadResourcesTest {

  private final CamundaFlyway.Configuration configuration = new CamundaFlyway.Configuration();
  private final FindSqlResources findSqlResources = new FindSqlResources(configuration);
  private final ReadResources function = new ReadResources(findSqlResources, new ReadSqlResource());

  @Test
  public void read_h2_sql_files() {
    final Map<String, String> sql = function.apply("h2");

    assertThat(sql).hasSize(7);
    assertThat(sql.get("activiti.h2.create.history.sql")).contains("create table ACT_HI_PROCINST");
  }

}
