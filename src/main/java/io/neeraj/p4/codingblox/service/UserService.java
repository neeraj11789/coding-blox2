package io.neeraj.p4.codingblox.service;

import io.neeraj.p4.codingblox.constant.SortOrder;
import io.neeraj.p4.codingblox.domain.User;

import java.util.Optional;

public interface UserService {
    void createUser(final String userName);

    Optional<User> getUser(final String userName);

    void getLeaderBoard(final SortOrder sortOrder);
}
