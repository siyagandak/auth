package com.khoding.auth.service;

import com.khoding.auth.domain.login.User;
import com.khoding.auth.domain.login.UserRole;
import com.khoding.auth.repository.RoleRepository;
import com.khoding.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final static String LOGGER_PREFIX = "[UserServiceImpl]";

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        LOGGER.info("{} search user = {}", LOGGER_PREFIX, username);
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findAllByOrganization_Id(Long organizationId, Pageable pageablee) {
        LOGGER.info("{} search all users for organizationId = {} ", LOGGER_PREFIX, organizationId);
        return userRepository.findAllByOrganization_Id(organizationId, pageablee);
    }

    @Override
    public Page<User> adminViewUsers(String username) {
        User user = getUserByUsername(username);
        Long organizationId = user.getOrganization().getId();
        return findAllByOrganization_Id(organizationId, Pageable.unpaged());
    }
}
