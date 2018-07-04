package com.xkw.rbm.user.support;

import com.xkw.rbm.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
}
