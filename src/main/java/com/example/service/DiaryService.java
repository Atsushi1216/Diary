package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Diary;
import com.example.repository.DiaryRepository;

@Service
public class DiaryService {

	@Autowired
	DiaryRepository diaryRepository;

	// 日記の全件取得
	public List<Diary> findAllDiary() {
		return diaryRepository.findAll();
	}

	// 日記の保存
	public void saveDiary(Diary diary) {
		diaryRepository.save(diary);
	}
	
	// 1件取得
	public Diary editDiary(Integer id) {
		Diary diarys = diaryRepository.findById(id).orElse(null);
		return diarys;
	}
	
	public Diary findById(Integer id) {
		return diaryRepository.findById(id).orElse(null);

	}
	
	public void deleteDiary(Integer id) {
		diaryRepository.deleteById(id);
	}

}
