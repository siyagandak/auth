package com.khoding.auth.restcontroller.organization;

import com.khoding.auth.domain.organization.Organization;
import com.khoding.auth.response.MessageResponse;
import com.khoding.auth.service.organization.OrganizationService;
import com.khoding.auth.service.organization.OrganizationRequest;
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
    private final static String LOGGER_PREFIX = "[OrganizationRestController]";
    private final static Logger LOGGER = LoggerFactory.getLogger(OrganizationRestController.class);

    public OrganizationRestController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('EADMIN')")
    public ResponseEntity<?> createOrganization(@RequestBody @Valid OrganizationRequest organizationRequest){
        LOGGER.info("{} create organization {}", LOGGER_PREFIX, organizationRequest);
        if (organizationService.checkOrganizationExistsByName(organizationRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage("Organization name already exists"));
        }
        if (organizationService.checkOrganizationExistsByCode(organizationRequest.getCode())) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage("Organization code already exists"));
        }
        return ResponseEntity.ok(organizationService.createOrganization(organizationRequest));
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
        if (organizationService.checkOrganizationExistsByCode(organizationRequest.getCode())) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage("Invalid Request. Code already exists"));
        }
        if (organizationService.checkOrganizationExistsByName(organizationRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.buildMessage("Invalid Request. Name already exists"));
        }
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
}
