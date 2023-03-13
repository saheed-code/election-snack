package com.paragon.poll.services;

import com.paragon.poll.data.models.Candidate;
import com.paragon.poll.data.models.Voter;
import com.paragon.poll.dtos.requests.RegisterRequest;
import com.paragon.poll.dtos.responses.GetAllResponse;
import com.paragon.poll.dtos.responses.GetResponse;
import com.paragon.poll.dtos.responses.RegisterResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    RegisterResponse register(RegisterRequest request);
    void verifyVoterAccount(Long id);
    void verifyCandidateAccount(Long id);
    GetResponse getVoterById(Long id);
    GetResponse getCandidateById(Long id);
    GetAllResponse getAllVoters(int pageNumber, int pageSize);
    GetAllResponse getAllCandidates(int pageNumber, int pageSize);
}
