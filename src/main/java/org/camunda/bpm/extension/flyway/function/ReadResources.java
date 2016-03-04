package org.camunda.bpm.extension.flyway.function;


import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

public class ReadResources {

  private final ReadSqlResource readResource;
  private final FindSqlResources findSqlResources;

  public ReadResources(FindSqlResources findSqlResources, ReadSqlResource readResource) {
    this.findSqlResources = findSqlResources;
    this.readResource = readResource;
  }

  public Map<String, String> apply(String databaseType) {
    final Map<String, String> result = new HashMap<String, String>();

    for (Resource resource : findSqlResources.apply(databaseType)) {
      result.put(resource.getFilename(), readResource.apply(resource));
    }

    return result;
  }
}
