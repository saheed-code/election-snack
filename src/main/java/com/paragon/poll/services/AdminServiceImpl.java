package com.paragon.poll.services;

import com.paragon.poll.data.models.Admin;
import com.paragon.poll.data.models.AppUser;
import com.paragon.poll.data.models.Candidate;
import com.paragon.poll.data.models.Voter;
import com.paragon.poll.data.repositories.AdminRepository;
import com.paragon.poll.data.repositories.CandidateRepository;
import com.paragon.poll.data.repositories.VoterRepository;
import com.paragon.poll.dtos.requests.RegisterRequest;
import com.paragon.poll.dtos.responses.GetAllResponse;
import com.paragon.poll.dtos.responses.GetResponse;
import com.paragon.poll.dtos.responses.RegisterResponse;
import com.paragon.poll.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AppUserService appUserService;
    private final AdminRepository adminRepository;
    private final VoterService voterService;
    private final CandidateService candidateService;
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;

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
                .message("Registration Successful")
                .build();
    }


    @Override
    public void verifyVoterAccount(Long id){
        Voter voter = voterService.confirmVoter(id);
        voter.getUserDetails().setIsApproved(true);
        voterService.saveVoter(voter);
    }

    @Override
    public void verifyCandidateAccount(Long id){
        Candidate candidate = candidateService.confirmCandidate(id);
        candidate.getUserDetails().setIsApproved(true);
        candidateService.saveCandidate(candidate);
    }

    @Override
    public GetResponse getVoterById(Long id) {
        Voter voter = voterRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(String.format("Voter with id %d was not found", id)));
        return mapVoterToResponse(voter);
    }


    @Override
    public GetResponse getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(String.format("Candidate with id %d was not found", id)));
        return mapCandidateToResponse(candidate);
    }

    @Override
    public GetAllResponse getAllVoters(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Voter> voters = voterRepository.findAll(pageable);
        List<Voter> votersList = voters.getContent();
        List<GetResponse> content = votersList.stream().map(p -> mapVoterToResponse(p)).toList();

        GetAllResponse getAllResponse = new GetAllResponse();
        getAllResponse.setContent(content);
        getAllResponse.setPageNumber(voters.getNumber());
        getAllResponse.setTotalPages(voters.getTotalPages());
        getAllResponse.setLast(voters.isLast());
        return getAllResponse;
    }

    @Override
    public GetAllResponse getAllCandidates(int pageNumber, int pageSize) {
        return null;
    }

    private GetResponse mapVoterToResponse(Voter voter){
        return mapToGetResponse(voter.getId(), voter.getUserDetails());
    }

    private GetResponse mapCandidateToResponse(Candidate candidate){
        return mapToGetResponse(candidate.getId(), candidate.getUserDetails());
    }

    private GetResponse mapToGetResponse(Long id, AppUser userDetails) {
        GetResponse getResponse = new GetResponse();
        getResponse.setId(id);
        getResponse.setFirstName(userDetails.getFirstName());
        getResponse.setLastName(userDetails.getLastName());
        getResponse.setNin(userDetails.getNin());
        getResponse.setIsApproved(userDetails.getIsApproved());
        return getResponse;
    }


}
