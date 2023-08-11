package uz.jamshid.java.service;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import uz.jamshid.java.util.TenantUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantProvisioningService {
    private final DataSource dataSource;
    public static final String LIQUIBASE_PATH = "db/changelog/db.changelog-master.yaml";
    private static final Pattern TENANT_PATTERN = Pattern.compile("[-\\w]+");

    public void subscribeTenant(final String tenantId) {
        String defaultSchemaName;
        try {
            Validate.isTrue(isValidTenantId(tenantId), String.format("Invalid tenant id: \"%s\"", tenantId));

            final String schemaName = TenantUtil.createSchemaName(tenantId);

            final Connection connection = dataSource.getConnection();
            final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            try (Statement statement = connection.createStatement()) {
                statement.execute(String.format("CREATE SCHEMA IF NOT EXISTS \"%s\"", schemaName));
                connection.commit();

                defaultSchemaName = database.getDefaultSchemaName();
                database.setDefaultSchemaName(schemaName);

                final Liquibase liquibase = new liquibase.Liquibase(LIQUIBASE_PATH,
                        new ClassLoaderResourceAccessor(), database);

                liquibase.update(new Contexts(), new LabelExpression());
                database.setDefaultSchemaName(defaultSchemaName);
            }
        } catch (Exception e) {
            log.error("TenantProvisioningService: Tenant subscription failed for {}.", tenantId, e);
        }
    }

    public void unsubscribeTenant(final String tenantId) {
        try {
            Validate.isTrue(isValidTenantId(tenantId), String.format("Invalid tenant id: \"%s\"", tenantId));

            final String schemaName = TenantUtil.createSchemaName(tenantId);
            final Connection connection = dataSource.getConnection();
            try (Statement statement = connection.createStatement()) {
                statement.execute(String.format("DROP SCHEMA IF EXISTS \"%s\" CASCADE", schemaName));
            }
        } catch (Exception e) {
            log.error("Tenant unsubscription failed for {}.", tenantId, e);
        }
    }

    private boolean isValidTenantId(String tenantId) {
        return Objects.nonNull(tenantId) && TENANT_PATTERN.matcher(tenantId).matches();
    }
}
