package com.allencam.controller;

import com.allencam.dto.TestimonialDTO;
import com.allencam.service.TestimonialGoogleSheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static com.allencam.common.Constants.TESTIMONIAL_SHEET_ID;

@RestController
@RequestMapping("/testimonials")
@CrossOrigin(origins = "http://localhost:5173")
public class TestimonialController {

    private static final String SHEET_ID = TESTIMONIAL_SHEET_ID;

    private final TestimonialGoogleSheetsService sheetsservice;

    @Autowired
    public TestimonialController(TestimonialGoogleSheetsService sheetsservice) {
        this.sheetsservice = sheetsservice;
    }

    @GetMapping
    public List<TestimonialDTO> getTestimonials() throws GeneralSecurityException, IOException {
        return sheetsservice.getApprovedTestimonials(TESTIMONIAL_SHEET_ID);
    }
}
