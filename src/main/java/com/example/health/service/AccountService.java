package com.example.health.service;

import com.example.health.data.enums.Role;
import com.example.health.data.AccountBean;
import com.example.health.entity.Account;
import com.example.health.exception.AccountException;
import com.example.health.repository.AccountRepository;
import com.example.health.repository.DoctorRepository;
import com.example.health.repository.UserRepository;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Account signUp(AccountBean accountBean) {
        if (accountRepository.findByUsernameAndRole(
                accountBean.getUsername(), accountBean.getRole()) != null) {
            throw new AccountException("用户名已存在!");
        }

        Role role = Role.of(accountBean.getRole());
        String salt = generateSalt();
        String passwordHash = hashPassword(accountBean.getPassword(), salt);
        Account account = new Account(null, accountBean.getUsername(),
                passwordHash, salt, role.getCode());
        account = accountRepository.save(account);

        return account;
    }

//    public Doctor doctorSignIn(AccountBean accountBean) {
//        Account account = accountSignIn(accountBean);
//
//        return doctorRepository.findByAccountId(account.getId());
//    }

    public void changePassword(AccountBean accountBean, int currentAccountId) {
        Account account = accountRepository.findById(currentAccountId).orElseThrow();

        if (!validatePassword(accountBean.getPassword(), account.getSalt(),
                account.getPasswordHash())) {
            throw new AccountException("密码不一致!");
        }

        String newSalt = generateSalt();
        String newPasswordHash = hashPassword(accountBean.getNewPassword(), newSalt);
        account.setPasswordHash(newPasswordHash);
        account.setSalt(newSalt);
        accountRepository.save(account);
    }


    public Account accountSignIn(AccountBean accountBean) {
        Account account = accountRepository.findByUsernameAndRole(
                accountBean.getUsername(), accountBean.getRole());

        if (account == null) {
            throw new AccountException("用户不存在!");
        }
        if (validatePassword(accountBean.getPassword(), account.getSalt(),
                account.getPasswordHash())) {
            throw new AccountException("密码错误!");
        }
        return account;
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
