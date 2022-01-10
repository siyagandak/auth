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
}
