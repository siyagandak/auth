package com.khoding.auth.repository.organization;

import com.khoding.auth.domain.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Boolean existsByName(String name);

    Boolean existsByCode(String code);
}
