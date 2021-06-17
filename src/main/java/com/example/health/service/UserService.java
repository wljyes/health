package com.example.health.service;

import com.example.health.data.ApiResult;
import com.example.health.data.UserBean;
import com.example.health.entity.User;
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

    public ApiResult<String> update(UserBean userBean, HttpSession session) {
        User user = (User) session.getAttribute("user");
        user.setName(userBean.getName());
        user.setAge(userBean.getAge());
        user.setSex(userBean.getSex());
        user.setTel(userBean.getTel());
        userRepository.save(user);

        return ApiResult.success();
    }

    public ApiResult<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ApiResult.fail();
        } else {
            return ApiResult.success(user);
        }
    }

    public ApiResult<User> getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ApiResult.success(user.get());
        } else {
            return ApiResult.fail();
        }
    }
}
