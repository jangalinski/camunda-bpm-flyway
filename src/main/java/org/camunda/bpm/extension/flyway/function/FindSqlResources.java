package org.camunda.bpm.extension.flyway.function;

import org.camunda.bpm.extension.flyway.CamundaFlyway;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FindSqlResources {

  private final PathMatchingResourcePatternResolver resolver;

  private final String pattern;

  public FindSqlResources(final CamundaFlyway.Configuration configuration) {
    resolver = new PathMatchingResourcePatternResolver(FindSqlResources.class.getClassLoader());

    this.pattern = configuration.getSqlResourcePattern();
  }

  public Set<Resource> apply(final String databaseType) {
    final String resourcePattern = resourcePattern(databaseType);
    try {
      Set<Resource> resources = new HashSet<Resource>();
      resources.addAll(Arrays.asList(resolver.getResources(resourcePattern)));
      return resources;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  String resourcePattern(String databaseType) {
    return MessageFormat.format(pattern, databaseType);
  }
}
