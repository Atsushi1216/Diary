package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.Diary;
import com.example.entity.User;
import com.example.service.DiaryService;

@Controller
public class DiaryController {

	@Autowired
	DiaryService diaryService;

	@Autowired
	HttpSession session;

	// topページの表示
	@GetMapping("/")
	public ModelAndView top() {
		ModelAndView mav = new ModelAndView();
		
		List<Diary> diarys = diaryService.findAllDiary();
		User user = (User) session.getAttribute("loginUser");

		// user情報がなければログイン画面へ遷移
		if (user == null) {
			mav.setViewName("/login");
			return mav;
		}

		mav.addObject("diarys", diarys);
		mav.addObject("loginUser", user);
		mav.setViewName("/top");
		return mav;
	}

	// 日記の投稿処理
	@PostMapping("/postDiary")
	public ModelAndView postDiary(@ModelAttribute("loginUser") Diary diary) {

		ModelAndView mav =new ModelAndView();
		User user = (User) session.getAttribute("loginUser");

		// user情報がなければログイン画面へ遷移
		if (user == null) {
			mav.setViewName("/login");
			return mav;
		}

		diary.setUserId(user.getId());

//		Timestamp cl = diary.getCreatedDate();
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String strDate = sdf.format(cl.getTime());
//		Timestamp currentTime = Timestamp.valueOf(strDate);
//
//		mav.addObject("currentTime", currentTime);


		diaryService.saveDiary(diary);
		mav.setViewName("/top");
		return new ModelAndView("/top");
	}
}
