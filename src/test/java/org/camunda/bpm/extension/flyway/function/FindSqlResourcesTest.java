package org.camunda.bpm.extension.flyway.function;

import org.camunda.bpm.extension.flyway.CamundaFlyway;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class FindSqlResourcesTest {

  private final CamundaFlyway.Configuration configuration = new CamundaFlyway.Configuration();

  private final FindSqlResources function = new FindSqlResources(configuration);

  @Test
  public void create_resourcePattern() {
    assertThat(function.resourcePattern("foo")).isEqualTo("classpath:org/camunda/bpm/engine/db/create/activiti.foo.*.sql");
  }

  @Test
  public void find_all_h2_resources() {
    final Set<String> resources = resourcesNames(function.apply("h2"));

    assertThat(resources).contains("activiti.h2.create.identity.sql").hasSize(7);
  }

  @Test
  public void find_all_oracle_resources() {
    final Set<String> resources = resourcesNames(function.apply("oracle"));

    assertThat(resources).contains("activiti.oracle.create.identity.sql").hasSize(7);
  }

  private static Set<String> resourcesNames(Set<Resource> resources) {
    Set<String> names = new HashSet<String>();
    for (Resource r : resources) {
      names.add(r.getFilename());
    }

    return names;
  }

}
