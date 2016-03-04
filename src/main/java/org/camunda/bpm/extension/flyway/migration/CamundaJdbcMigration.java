package org.camunda.bpm.extension.flyway.migration;


import org.camunda.bpm.extension.flyway.CamundaFlyway;
import org.camunda.bpm.extension.flyway.function.DetermineDatabaseType;
import org.camunda.bpm.extension.flyway.function.ReadResources;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.configuration.ConfigurationAware;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.migration.MigrationInfoProvider;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;


public class CamundaJdbcMigration implements JdbcMigration, MigrationInfoProvider, ConfigurationAware {

  private final Logger logger = LoggerFactory.getLogger(CamundaJdbcMigration.class);
  private final DetermineDatabaseType determineDatabaseType;
  private final ReadResources readResources;
  private FlywayConfiguration configuration;

  public CamundaJdbcMigration() {
    this(CamundaFlyway.DETERMINE_DATABASE_TYPE, CamundaFlyway.READ_RESOURCES);
  }

  public CamundaJdbcMigration(DetermineDatabaseType determineDatabaseType, ReadResources readResources) {
    this.determineDatabaseType = determineDatabaseType;
    this.readResources = readResources;
  }

  @Override
  public void migrate(final Connection connection) throws Exception {
    Statement st = null;
    String databaseType = determineDatabaseType.apply(connection);
    Map<String, String> resources = readResources.apply(databaseType);
    try {
      st = connection.createStatement();
      for (Entry<String, String> entry : resources.entrySet()) {
        logger.debug("Migrating {}", entry.getKey());
        st.addBatch(entry.getValue());
      }
      st.executeBatch();
    } finally {
      st.close();
    }
  }

  @Override
  public MigrationVersion getVersion() {
    return MigrationVersion.fromVersion("1.1");
  }

  @Override
  public String getDescription() {
    return "set up camunda tables";
  }

  @Override
  public void setFlywayConfiguration(final FlywayConfiguration flywayConfiguration) {
    this.configuration = flywayConfiguration;
  }
}
