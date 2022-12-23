package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	// 全件取得
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	// ログイン
	public User findLoginUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	// ユーザー情報の保存
	public void saveUser(User user) {
		userRepository.save(user);
	}
}
