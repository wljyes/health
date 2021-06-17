package com.example.health.service;

import com.example.health.data.ApiResult;
import com.example.health.data.Role;
import com.example.health.data.AccountBean;
import com.example.health.entity.Account;
import com.example.health.entity.Doctor;
import com.example.health.entity.User;
import com.example.health.repository.AccountRepository;
import com.example.health.repository.DoctorRepository;
import com.example.health.repository.UserRepository;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AccountService {
    private static final String SHA1 = "SHA-1";
    private static final int ITERATIONS = 512;
    private static final SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;

    public ApiResult<String> signUp(AccountBean accountBean) {
        if (accountRepository.findByUsernameAndRole(
                accountBean.getUsername(), accountBean.getRole()) != null) {
            return ApiResult.fail("用户已存在!");
        }

        Role role = Role.of(accountBean.getRole());
        String salt = generateSalt();
        String passwordHash = hashPassword(accountBean.getPassword(), salt);
        Account account = new Account(null, accountBean.getUsername(),
                passwordHash, salt, role.getCode());
        accountRepository.save(account);

        switch (role) {
            case User:
                userRepository.save(new User(account.getUsername(), account.getId()));
                break;
            case Doctor:
                doctorRepository.save(new Doctor(account.getId()));
                break;
        }

        return ApiResult.success();
    }

    public ApiResult<String> signIn(AccountBean accountBean, HttpSession session) {
        Account account = accountRepository.findByUsernameAndRole(
                accountBean.getUsername(), accountBean.getRole());

        if (account == null) {
            return ApiResult.fail("用户不存在!");
        }
        if (validatePassword(accountBean.getPassword(), account.getSalt(),
                account.getPasswordHash())) {
            return ApiResult.fail("密码错误!");
        }

        switch (Role.of(accountBean.getRole())) {
            case User:
                User user = userRepository.findByAccountId(account.getId());
                session.setAttribute("user", user);
                session.setAttribute("role", Role.User.getCode());
                break;
            case Doctor:
                Doctor doctor = doctorRepository.findByAccountId(account.getId());
                session.setAttribute("doctor", doctor);
                session.setAttribute("role", Role.Doctor.getCode());
                break;
        }
        return ApiResult.success();
    }

    public ApiResult<String> changePassword(AccountBean accountBean, HttpSession session) {
        Role role = Role.of((Integer) session.getAttribute("role"));
        Account account = null;
        switch (role) {
            case User:
                User user = (User) session.getAttribute("user");
                account = accountRepository.findById(user.getAccountId()).get();
                break;
            case Doctor:
                Doctor doctor = (Doctor) session.getAttribute("doctor");
                account = accountRepository.findById(doctor.getAccountId()).get();
                break;
        }

        if (!validatePassword(accountBean.getPassword(), account.getSalt(),
                account.getPasswordHash())) {
            return ApiResult.fail("密码不一致!");
        }

        String newSalt = generateSalt();
        String newPasswordHash = hashPassword(accountBean.getNewPassword(), newSalt);
        account.setPasswordHash(newPasswordHash);
        account.setSalt(newSalt);
        accountRepository.save(account);

        return ApiResult.success();
    }

    private boolean validatePassword(String password, String salt, String passwordHash) {
        return hashPassword(password, salt).equals(passwordHash);
    }

    private String hashPassword(String password, String salt) {
        return new SimpleHash(SHA1, password, salt, ITERATIONS).toBase64();
    }

    private String generateSalt() {
        return randomNumberGenerator.nextBytes().toHex();
    }
}
