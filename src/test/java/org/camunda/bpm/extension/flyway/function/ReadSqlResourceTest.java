package org.camunda.bpm.extension.flyway.function;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadSqlResourceTest {

  private final ReadSqlResource function = new ReadSqlResource();

  @Test
  public void read_resource() {
    final Resource resource = new ClassPathResource("dummy.txt");

    assertThat(function.apply(resource)).isEqualTo("Hello World! ");
  }

}
