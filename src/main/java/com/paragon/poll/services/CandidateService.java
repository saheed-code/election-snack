package com.paragon.poll.services;

import com.paragon.poll.data.models.Candidate;
import com.paragon.poll.dtos.requests.RegisterRequest;
import com.paragon.poll.dtos.requests.UpdateRequest;
import com.paragon.poll.dtos.requests.UpdateResponse;
import com.paragon.poll.dtos.responses.RegisterResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CandidateService {
    RegisterResponse register(RegisterRequest request);
    UpdateResponse update(UpdateRequest request);
    Candidate confirmCandidate(Long id);

    void saveCandidate(Candidate candidate);
}
