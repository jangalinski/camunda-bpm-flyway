package org.camunda.bpm.extension.flyway.function;

import org.camunda.bpm.extension.flyway.CamundaFlyway;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationTest {

  @Test
  public void properties_getSqlResourcePattern() {
    final String pattern = new CamundaFlyway.Configuration().getSqlResourcePattern();

    assertThat(pattern).isNotEmpty().contains("classpath:").contains("activiti").contains(".sql");
  }

  @Test
  public void readResource() {
    assertThat(new CamundaFlyway.Configuration("camunda-flyway.properties").get()).isNotNull();
  }
}
