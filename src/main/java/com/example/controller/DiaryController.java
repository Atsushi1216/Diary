package com.example.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	// 日記編集画面
	@GetMapping("/edit/{id}")
	public ModelAndView editContent(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView();
		
		User user = (User) session.getAttribute("loginUser");
		
		if (user == null) {
			mav.setViewName("/login");
			return mav;
		}
		
		Diary diary = diaryService.editDiary(id);
		
		mav.addObject("formModel", diary);
		mav.setViewName("/edit");
		return mav;
	}
	
	// 日記編集処理
	@PutMapping("/postEdit/{id}")
	public ModelAndView postEditContent(@PathVariable Integer id, @ModelAttribute("formModel") Diary diary) {
		User user = (User) session.getAttribute("loginUser");
		
		diary.setUserId(user.getId());		
		diary.setId(id);
		
		Timestamp update_date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
	
		update_date = new Timestamp(date.getTime());
		diary.setUpdatedDate(update_date);
		
		diaryService.saveDiary(diary);
		return new ModelAndView("redirect:/");
		
	}
	
	
}