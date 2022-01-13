package com.khoding.auth.service.organization;

import com.khoding.auth.domain.organization.Organization;
import com.khoding.auth.repository.organization.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService{
    private final OrganizationRepository organizationRepository;
    private static final String LOGGER_PREFIX = "[OrganizationServiceImpl]";
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization createOrganization(OrganizationRequest organizationRequest) {
        LOGGER.info("{} Creating organizational request", organizationRequest);
        Organization organization = Organization.buid(organizationRequest.getName(),
                organizationRequest.getCode(), organizationRequest.getAddress());
        return organizationRepository.save(organization);
    }

    @Override
    public Boolean checkOrganizationExistsByName(String name) {
        LOGGER.info("{} Organization {} exists => {}", LOGGER_PREFIX, name,
                organizationRepository.existsByName(name));
        return organizationRepository.existsByName(name);
    }

    @Override
    public Boolean checkOrganizationExistsByCode(String code) {
        LOGGER.info("{} Organization {} exists => {}", LOGGER_PREFIX, code,
                organizationRepository.existsByCode(code));
        return organizationRepository.existsByCode(code);
    }

    @Override
    public Page<Organization> getAllOrganizations() {
        LOGGER.info("{} Getting all organizations", LOGGER_PREFIX);
        return organizationRepository.findAll(Pageable.unpaged());
    }

    @Override
    public Organization updateOrganization(Long organizationId, OrganizationRequest organizationRequest) {
        LOGGER.info("{} Update organization with id ={}", LOGGER_PREFIX, organizationId);
        Organization organization = getOrganizationById(organizationId).get();
        organization.setName(organizationRequest.getName());
        organization.setCode(organizationRequest.getCode());
        organization.setAddress(organizationRequest.getAddress());
        return organizationRepository.save(organization);
    }

    @Override
    public Optional<Organization> getOrganizationById(Long organizationId) {
        return organizationRepository.findById(organizationId);
    }
}
