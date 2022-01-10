package com.khoding.auth.service.organization;

import com.khoding.auth.domain.organization.Organization;
import org.springframework.data.domain.Page;

public interface OrganizationService {
    Organization createOrganization(OrganizationRequest organizationRequest);

    Boolean checkOrganizationExistsByName(String name);

    Boolean checkOrganizationExistsByCode(String code);

    Page<Organization> getAllOrganizations();
}
