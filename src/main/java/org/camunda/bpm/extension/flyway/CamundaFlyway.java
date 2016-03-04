package org.camunda.bpm.extension.flyway;


import org.camunda.bpm.extension.flyway.function.DetermineDatabaseType;
import org.camunda.bpm.extension.flyway.function.FindSqlResources;
import org.camunda.bpm.extension.flyway.function.ReadResources;
import org.camunda.bpm.extension.flyway.function.ReadSqlResource;
import org.camunda.bpm.extension.flyway.migration.CamundaJdbcMigration;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class CamundaFlyway {

  public static final Configuration CONFIGURATION = new Configuration();
  public static final DetermineDatabaseType DETERMINE_DATABASE_TYPE = new DetermineDatabaseType();
  public static final FindSqlResources FIND_SQL_RESOURCES = new FindSqlResources(CONFIGURATION);
  public static final ReadSqlResource READ_SQL_RESOURCE = new ReadSqlResource();
  public static final ReadResources READ_RESOURCES = new ReadResources(FIND_SQL_RESOURCES, READ_SQL_RESOURCE);

  private final Flyway flyway;

  public static CamundaFlyway from(Flyway flyway) {
    return new CamundaFlyway(flyway);
  }

  public static CamundaFlyway create() {
    return new CamundaFlyway(new Flyway());
  }

  private CamundaFlyway(Flyway flyway) {
    this.flyway = flyway;
    addLocation(CamundaJdbcMigration.class.getPackage());
  }

  public CamundaFlyway addLocation(Package p) {
    Set<String> locations = new LinkedHashSet<String>(Arrays.asList(flyway.getLocations()));

    locations.add(p.getName());

    flyway.setLocations(locations.toArray(new String[locations.size()]));

    return this;
  }

  public CamundaFlyway withDatasource(DataSource datasource) {
    flyway.setDataSource(datasource);

    return this;
  }

  public Flyway get() {
    return flyway;
  }

  public static class Configuration {

    private final ResourceBundle resourceBundle;

    public Configuration() {
      this("camunda-flyway.properties");
    }

    public Configuration(final String resourceName) {
     resourceBundle =  ResourceBundle.getBundle(resourceName.replaceAll("\\.properties", ""));
    }

    public String getSqlResourcePattern() {
      return resourceBundle.getString("camunda.sql.resource.pattern").trim();
    }

    public ResourceBundle get() {
      return resourceBundle;
    }
  }
}
