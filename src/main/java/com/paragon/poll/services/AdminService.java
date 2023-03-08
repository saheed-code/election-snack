package com.paragon.poll.services;

import com.paragon.poll.dtos.requests.RegisterRequest;
import com.paragon.poll.dtos.responses.RegisterResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {
    RegisterResponse register(RegisterRequest request);
    void verifyVoterAccount(Long id);
    void verifyCandidateAccount(Long id);
}
