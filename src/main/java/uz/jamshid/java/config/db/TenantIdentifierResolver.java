package uz.jamshid.java.config.db;

import com.sap.cloud.sdk.cloudplatform.tenant.exception.TenantAccessException;
import com.sap.cloud.security.xsuaa.token.AuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Value("${multitenant.defaultTenant}")
    String defaultTenant;

    @Override
    public String resolveCurrentTenantIdentifier() {
        try {
            AuthenticationToken authenticationToken = (AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            if (Objects.nonNull(authenticationToken)) {
                Map<String, Object> attributes = authenticationToken.getTokenAttributes();
                if (Objects.nonNull(attributes)) {
                    String tenant = (String) attributes.get("zid");
                    return isValidTenant(tenant) ? tenant : defaultTenant;
                }
            }
            return defaultTenant;
        } catch (TenantAccessException e) {
            log.warn("Tenant not found", e);
            return defaultTenant;
        }
    }

    private static boolean isValidTenant(String tenant) {
        return Objects.nonNull(tenant) && !Objects.equals("sap-provisioning", tenant);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
