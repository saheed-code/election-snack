package com.paragon.poll.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paragon.poll.dtos.requests.RegisterRequest;
import com.paragon.poll.dtos.responses.RegisterResponse;
import com.paragon.poll.services.CandidateService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/poll/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping(value="/register", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> register(@RequestParam String request,
                                      @RequestParam MultipartFile image,
                                      @RequestParam MultipartFile ped) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterRequest registerRequest = objectMapper.readValue(request, RegisterRequest.class);
        registerRequest.setProfileImage(image);
        registerRequest.setPed(ped);
        RegisterResponse response = candidateService.register(registerRequest);

        return ResponseEntity.status(response.getCode()).body(response);
    }
}
