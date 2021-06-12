package io.neeraj.p4.codingblox.service.impl;

import io.neeraj.p4.codingblox.constant.SortOrder;
import io.neeraj.p4.codingblox.constant.WebException;
import io.neeraj.p4.codingblox.domain.User;
import io.neeraj.p4.codingblox.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryUserServiceImpl implements UserService {

    private static Integer counter = 0;

    private static List<User> userList = new ArrayList<>();

    @Override
    public void createUser(String userName) {
        if(getUser( userName ).isPresent())
            throw new WebException(400, 4000, "User Already present");
        userList.add( getUserObj( userName ) );
    }

    private User getUserObj(String userName) {
        counter++;
        return new User( Long.valueOf( counter ), userName );
    }

    @Override
    public Optional<User> getUser(String userName) {
        return userList.stream().filter( user -> user.getUserId().equals( userName ) ).findAny();
    }

    @Override
    public void getLeaderBoard(SortOrder sortOrder) {

    }
}
