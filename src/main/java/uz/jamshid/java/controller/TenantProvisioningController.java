package uz.jamshid.java.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import uz.jamshid.java.service.TenantProvisioningService;

@Slf4j
@Component
@RestController
@RequestScope
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/callback/tenant")
public class TenantProvisioningController {
    private static final String APP_ROUTER_DOMAIN_NAME = "-web-generous-crane-ut.cfapps.us10-001.hana.ondemand.com";
    private static final String HTTPS = "https://";
    private final TenantProvisioningService tenantProvisioningService;

    @PutMapping("/{tenantId}")
    public HttpEntity<?> subscribeTenant(@RequestBody JsonNode requestBody, @PathVariable(value = "tenantId") String tenantId){
        log.info("Tenant callback service was called with method PUT for tenant {}.", tenantId);
        String subscribedSubdomain = requestBody.get("subscribedSubdomain").asText();

        tenantProvisioningService.subscribeTenant(tenantId);
        String subscriptionLink = HTTPS + subscribedSubdomain + APP_ROUTER_DOMAIN_NAME;

        return ResponseEntity.status(200).body(subscriptionLink);
    }

    @DeleteMapping("/{tenantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpEntity<?> unsubscribeTenant(@PathVariable(value = "tenantId")  String tenantId) {
        log.info("Tenant callback service was called with method DELETE for tenant {}.", tenantId);
        tenantProvisioningService.unsubscribeTenant(tenantId);
        return ResponseEntity.status(200).build();
    }
}
