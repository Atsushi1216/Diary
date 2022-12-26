package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.User;
import com.example.service.UserService;
import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/signup")
	public ModelAndView signup() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/signup");
		return mav;
	}

	@PostMapping("/postSignup")
	public ModelAndView postSignup(@ModelAttribute("formModel") User user,
			@RequestParam(name="password2") String password2) {
		ModelAndView mav = new ModelAndView();
		List<String> errorMessages = new ArrayList<String>();

		String rawPassword = user.getPassword();
		String strPassword2 = password2;

		Hasher hasher = Hashing.sha256().newHasher();
		hasher.putString(rawPassword, Charsets.UTF_8);
		HashCode sha256 = hasher.hash();

		String strSha256 = String.valueOf(sha256);

		user.setPassword(strSha256);

		if (!rawPassword.equals(strPassword2)) {
			errorMessages.add("確認用パスワードが違います");
		}

		if(errorMessages.size() != 0) {
			mav.setViewName("/signup");
			mav.addObject("errorMessages", errorMessages);
			return mav;
		}

		userService.saveUser(user);

		return new ModelAndView("redirect:/");
	}
}