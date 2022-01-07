package com.khoding.auth.service;

import com.khoding.auth.domain.login.Organization;

public interface OrganizationService {
    Organization createOrganization(OrganizationRequest organizationRequest);

    Boolean checkOrganizationExistsByName(String name);

    Boolean checkOrganizationExistsByCode(String code);
}
