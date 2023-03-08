package com.paragon.poll.services;

import com.paragon.poll.data.models.Admin;
import com.paragon.poll.data.models.AppUser;
import com.paragon.poll.data.models.Candidate;
import com.paragon.poll.data.models.Voter;
import com.paragon.poll.data.repositories.AdminRepository;
import com.paragon.poll.dtos.requests.RegisterRequest;
import com.paragon.poll.dtos.responses.RegisterResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AppUserService appUserService;
    private final AdminRepository adminRepository;
    private final VoterService voterService;
    private final CandidateService candidateService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        AppUser registeredVoter = appUserService.register(request);

        Admin admin = Admin.builder()
                .userDetails(registeredVoter)
                .build();

        Admin savedAdmin = adminRepository.save(admin);

        return RegisterResponse.builder()
                .code(HttpStatus.CREATED.value())
                .id(savedAdmin.getId())
                .isSuccessful(true)
                .message("Driver Registration Successful")
                .build();
    }




    public void verifyVoterAccount(Long id){
        Voter voter = voterService.confirmVoter(id);
        voter.getUserDetails().setIsApproved(true);
        voterService.saveVoter(voter);
    }

    public void verifyCandidateAccount(Long id){
        Candidate candidate = candidateService.confirmCandidate(id);
        candidate.getUserDetails().setIsApproved(true);
        candidateService.saveCandidate(candidate);
    }




}
