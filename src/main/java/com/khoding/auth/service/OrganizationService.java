package com.khoding.auth.service;

import com.khoding.auth.domain.login.Organization;
import org.springframework.data.domain.Page;

public interface OrganizationService {
    Organization createOrganization(OrganizationRequest organizationRequest);

    Boolean checkOrganizationExistsByName(String name);

    Boolean checkOrganizationExistsByCode(String code);

    Page<Organization> getAllOrganizations();
}
