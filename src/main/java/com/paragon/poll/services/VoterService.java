package com.paragon.poll.services;

import com.paragon.poll.data.models.Voter;
import com.paragon.poll.dtos.requests.*;
import com.paragon.poll.dtos.responses.RegisterResponse;
import com.paragon.poll.dtos.responses.VoteResponse;
import org.springframework.web.multipart.MultipartFile;

public interface VoterService {
    RegisterResponse register(RegisterRequest request);
    Voter login(LoginRequest request);
    UpdateResponse updateVoter(UpdateRequest request);
    VoteResponse vote(VoteRequest request);

    Voter confirmVoter(Long id);

    void saveVoter(Voter voter);
}
