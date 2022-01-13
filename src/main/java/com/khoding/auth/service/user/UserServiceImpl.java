package com.khoding.auth.service.user;

import com.khoding.auth.domain.user.User;
import com.khoding.auth.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String LOGGER_PREFIX = "[UserServiceImpl]";

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        LOGGER.info("{} Retrieving user = {}", LOGGER_PREFIX, username);
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

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        LOGGER.info("{} Gettting all users", LOGGER_PREFIX);
        return userRepository.findAll(pageable);
    }

    @Override
    public User saveUser(User user) {
        LOGGER.info("{} Saving user {}", LOGGER_PREFIX, user);
        return userRepository.save(user);
    }
}
