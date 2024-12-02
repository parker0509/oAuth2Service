package com.example.studymatching_api;

import com.example.studymatching_api.Recruitment;
import com.example.studymatching_api.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitments")
public class RecruitmentController {

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    // 새로운 게시글 생성
    @PostMapping
    public Recruitment createRecruitment(@RequestBody Recruitment recruitment) {
        return recruitmentRepository.save(recruitment);
    }

    // 모든 게시글 조회
    @GetMapping
    public List<Recruitment> getAllRecruitments() {
        return recruitmentRepository.findAll();
    }

    // 카테고리별 게시글 조회
    @GetMapping("/category/{category}")
    public List<Recruitment> getRecruitmentsByCategory(@PathVariable String category) {
        return recruitmentRepository.findByCategory(category);
    }
}
