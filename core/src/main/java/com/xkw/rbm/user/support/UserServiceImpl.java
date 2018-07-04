package com.xkw.rbm.user.support;

import com.xkw.rbm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User add(User user) {
        return userDao.save(user);
    }
}
