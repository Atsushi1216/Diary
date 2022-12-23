package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.User;
import com.example.service.UserService;
import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	HttpSession session;

	// ログイン画面の表示
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/login");
		return mav;
	}

	// ログイン処理
	@PostMapping("/postLogin")
	public ModelAndView postLogin(@ModelAttribute("formModel") User user) {
//		ModelAndView mav = new ModelAndView();

		String rawPassword = user.getPassword();

	    Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(rawPassword, Charsets.UTF_8);
        HashCode sha256 = hasher.hash();

        String strSha256 = String.valueOf(sha256);

        // パスワードを変数にしたのでセットする必要
        user.setPassword(strSha256);

        user = userService.findLoginUser(user.getEmail(), strSha256);

        session.setAttribute("loginUser", user);

        return new ModelAndView("redirect:/");
	}
}
