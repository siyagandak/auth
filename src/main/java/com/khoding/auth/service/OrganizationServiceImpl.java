package com.khoding.auth.service;

import com.khoding.auth.domain.login.Organization;
import com.khoding.auth.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService{
    private final OrganizationRepository organizationRepository;
    private final static String LOGGER_PREFIX = "[OrganizationServiceImpl]";
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
}
