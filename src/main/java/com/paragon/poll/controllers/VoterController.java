package com.paragon.poll.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paragon.poll.dtos.requests.LoginRequest;
import com.paragon.poll.dtos.requests.RegisterRequest;
import com.paragon.poll.dtos.requests.UpdateRequest;
import com.paragon.poll.dtos.responses.RegisterResponse;
import com.paragon.poll.services.VoterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.MulticastChannel;

@RestController
@RequestMapping("/poll/voter")
@AllArgsConstructor
public class VoterController {
    private final VoterService voterService;

    @PostMapping(value="/register", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> register(@RequestParam String request, @RequestParam("image") MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterRequest registerRequest = objectMapper.readValue(request, RegisterRequest.class);
        registerRequest.setProfileImage(image);
        RegisterResponse response = voterService.register(registerRequest);

        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        var response = voterService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping(value="{voterId}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long voterId, @RequestBody UpdateRequest request){
        request.setId(voterId);
        var response = voterService.updateVoter(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
