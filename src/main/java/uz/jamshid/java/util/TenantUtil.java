package uz.jamshid.java.util;

public class TenantUtil {
    public static String createSchemaName(final String tenantId) {
        return String.format("tenant_%s", tenantId);
    }
}
