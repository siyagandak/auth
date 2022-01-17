package com.khoding.auth.service.organization;

import com.khoding.auth.domain.organization.Organization;
import com.khoding.auth.domain.utils.Status;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrganizationService {
    Organization createOrganization(OrganizationRequest organizationRequest);

    Boolean checkOrganizationExistsByName(String name);

    Boolean checkOrganizationExistsByCode(String code);

    Page<Organization> getAllOrganizations();

    Organization updateOrganization(Long organizationId, OrganizationRequest organizationRequest);

    Optional<Organization> getOrganizationById(Long organizationId);

    Organization updateOrganizationStatus(Long id, Status status);
}
