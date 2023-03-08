package com.paragon.poll.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paragon.poll.data.models.Gender;
import com.paragon.poll.data.repositories.VoterRepository;
import com.paragon.poll.dtos.requests.LoginRequest;
import com.paragon.poll.dtos.requests.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.paragon.poll.data.models.Voter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class VoterServiceImplTest {
   @Autowired
   private VoterService voterService;
   private RegisterRequest request;

   @Autowired
   private VoterRepository voterRepository;
   @Autowired
   private ObjectMapper objectMapper;

   @BeforeEach
    void setUp(){
       request = new RegisterRequest();
       request.setFirstName("Ahmed");
       request.setLastName("Tinubu");
       request.setGender(Gender.MALE);
       request.setEmail("bola.tinubu@email.com");
       request.setPassword("bolaahmedtinubu");

//       Voter voter = objectMapper.convertValue(request, Voter.class);
//       voterRepository.save(voter);
   }

   @Test
   void voterRegisterTest() throws IOException {
      MockMultipartFile file = new MockMultipartFile("profileImage-test",
              new FileInputStream("C:\\Users\\Admin\\Documents\\poll\\src\\test\\resources\\Tinubu.jpg"));
      request.setProfileImage(file);
      var response = voterService.register(request);
      assertNotNull(response);
      assertTrue(response.isSuccessful());
      assertEquals(response.getCode(), HttpStatus.CREATED.value());
   }

   @Test
   void voterLoginTest(){
      LoginRequest loginRequest = new LoginRequest();
      loginRequest.setId(1L);
      loginRequest.setPassword("bolaahmedtinubu");
      var response = voterService.login(loginRequest);
      assertNotNull(response);
   }



}
