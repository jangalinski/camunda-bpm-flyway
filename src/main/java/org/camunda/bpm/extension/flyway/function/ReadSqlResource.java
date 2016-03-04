package org.camunda.bpm.extension.flyway.function;


import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadSqlResource {

  public String apply(final Resource resource) {

    BufferedReader bufferedReader = null;
    StringBuilder stringBuilder = new StringBuilder();
    String line;

    try {
      bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line).append(" ");
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return stringBuilder.toString();
  }
}
