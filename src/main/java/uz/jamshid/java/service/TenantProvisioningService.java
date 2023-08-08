package uz.jamshid.java.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantProvisioningService {
    private static final Pattern TENANT_PATTERN = Pattern.compile("[-\\w]+");
    public void subscribeTenant(final String tenantId) {
        String defaultSchemaName;
        try {
            Validate.isTrue(isValidTenantId(tenantId), String.format("Invalid tenant id: \"%s\"", tenantId));

        } catch (Exception e) {
            log.error("TenantProvisioningService: Tenant subscription failed for {}.", tenantId, e);
            throw e;
        }
    }

    public void unsubscribeTenant(final String tenantId) {
        try {
            Validate.isTrue(isValidTenantId(tenantId), String.format("Invalid tenant id: \"%s\"", tenantId));

        } catch (Exception e) {
            log.error("Tenant unsubscription failed for {}.", tenantId, e);
            throw e;
        }
    }

    private boolean isValidTenantId(String tenantId) {
        return Objects.nonNull(tenantId) && TENANT_PATTERN.matcher(tenantId).matches();
    }
}
