package com.example.health.service;

import com.example.health.data.AccountBean;
import com.example.health.data.ApiResult;
import com.example.health.data.UserBean;
import com.example.health.entity.Account;
import com.example.health.entity.User;
import com.example.health.exception.UserNotFoundException;
import com.example.health.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void signUp(UserBean userBean, Account account) {
        User user = new User();
        userBean.fillUser(user);
        user.setName(userBean.getUsername());
        user.setAccountId(account.getId());
        userRepository.save(user);
    }

    public User getUserByAccountId(int id) {
        return userRepository.findByAccountId(id);
    }

    public void update(UserBean userBean, User currentUser) {
        userBean.fillUser(currentUser);
        userRepository.save(currentUser);
    }

    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(UserNotFoundException::new);
    }
}
