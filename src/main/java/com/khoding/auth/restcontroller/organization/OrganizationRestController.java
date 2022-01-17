package com.khoding.auth.restcontroller.organization;

import com.khoding.auth.domain.organization.Organization;
import com.khoding.auth.domain.utils.Status;
import com.khoding.auth.response.MessageResponse;
import com.khoding.auth.service.organization.OrganizationRequest;
import com.khoding.auth.service.organization.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController ()
@RequestMapping("/api/organization")
public class OrganizationRestController {
    private final OrganizationService organizationService;
    private static final String LOGGER_PREFIX = "[OrganizationRestController]";
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationRestController.class);

    public OrganizationRestController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('EADMIN')")
    public ResponseEntity<?> createOrganization(@RequestBody @Valid OrganizationRequest organizationRequest){
        LOGGER.info("{} create organization {}", LOGGER_PREFIX, organizationRequest);
        ResponseEntity<MessageResponse> Organization_name_already_exists = getMessageResponseResponseEntity(organizationService.checkOrganizationExistsByName(organizationRequest.getName()), "Organization name already exists");
        if (Organization_name_already_exists != null) return Organization_name_already_exists;
        ResponseEntity<MessageResponse> Organization_code_already_exists = getMessageResponseResponseEntity(organizationService.checkOrganizationExistsByCode(organizationRequest.getCode()), "Organization code already exists");
        if (Organization_code_already_exists != null) return Organization_code_already_exists;
        return ResponseEntity.ok(organizationService.createOrganization(organizationRequest));
    }

    private ResponseEntity<MessageResponse> getMessageResponseResponseEntity(Boolean organizationService, String Organization_name_already_exists) {
        if (organizationService) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage(Organization_name_already_exists));
        }
        return null;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('EADMIN', 'ADMIN')")
    public Page<Organization> getAllOrganizations() {
        LOGGER.info("{} Initiating request to get all organizations...", LOGGER_PREFIX);
        return organizationService.getAllOrganizations();
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<?> updateOrganization(@PathVariable Long organizationId,
                                           @RequestBody @Valid OrganizationRequest organizationRequest){
        LOGGER.info("{} Update organization with id={}", LOGGER_PREFIX, organizationId);
        Optional<Organization> organization = organizationService.getOrganizationById(organizationId);
        if (organization.isPresent()) {
            return ResponseEntity.ok(organizationService.updateOrganization(organizationId, organizationRequest));
        }
        ResponseEntity<MessageResponse> body = getMessageResponseResponseEntity(organizationService.checkOrganizationExistsByCode(organizationRequest.getCode()), "Invalid Request. Code already exists");
        if (body != null) return body;
        ResponseEntity<MessageResponse> body1 = getMessageResponseResponseEntity(organizationService.checkOrganizationExistsByName(organizationRequest.getName()), "Invalid Request. Name already exists");
        if (body1 != null) return body1;
        return ResponseEntity
                .badRequest()
                .body(MessageResponse.buildMessage("Invalid Request. ERecord not found"));
    }

    @GetMapping("/getBy/{organizationId}")
    public ResponseEntity<?> getOrganizationById(@PathVariable Long organizationId){
        LOGGER.info("{} Get organization with id ={}", LOGGER_PREFIX, organizationId);
        Optional<Organization> organization = organizationService.getOrganizationById(organizationId);
        LOGGER.info("{} Organization retrieved ={}", LOGGER_PREFIX, organization);
        if (organization.isPresent()) {
            return ResponseEntity.ok(organization.get());
        }
        return ResponseEntity
                .badRequest()
                .body(MessageResponse.buildMessage("Invalid Request. ERecord not found"));
    }

    @PutMapping("/{organizationId}/{status}")
    public ResponseEntity<?> updateOrganizationStatus(@PathVariable Long organizationId,
                                                      @PathVariable Status status){
        LOGGER.info("{} Update organization with id ={}", LOGGER_PREFIX, organizationId);
        Optional<Organization> organization = organizationService.getOrganizationById(organizationId);
        if (organization.isPresent()) {
            return ResponseEntity.ok(organizationService.updateOrganizationStatus(organizationId, status));
        }
        return ResponseEntity
                .badRequest()
                .body(MessageResponse.buildMessage("Organization record not found"));
    }
}
