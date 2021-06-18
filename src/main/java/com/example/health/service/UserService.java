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

    public void update(UserBean userBean, User currentUser) {
        currentUser.setName(userBean.getName());
        currentUser.setAge(userBean.getAge());
        currentUser.setSex(userBean.getSex());
        currentUser.setTel(userBean.getTel());
        userRepository.save(currentUser);
    }

    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(UserNotFoundException::new);
    }
}
