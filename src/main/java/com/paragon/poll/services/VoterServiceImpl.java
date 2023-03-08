package com.paragon.poll.services;

import com.paragon.poll.cloud.CloudService;
import com.paragon.poll.data.models.AppUser;
import com.paragon.poll.data.models.Candidate;
import com.paragon.poll.data.models.Vote;
import com.paragon.poll.data.models.Voter;
import com.paragon.poll.data.repositories.AppUserRepository;
import com.paragon.poll.data.repositories.CandidateRepository;
import com.paragon.poll.data.repositories.VoteRepository;
import com.paragon.poll.data.repositories.VoterRepository;
import com.paragon.poll.dtos.requests.*;
import com.paragon.poll.dtos.responses.RegisterResponse;
import com.paragon.poll.dtos.responses.VoteResponse;
import com.paragon.poll.exceptions.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class VoterServiceImpl implements VoterService{
    private final VoterRepository voterRepository;
    private final VoteRepository voteRepository;
    private final AppUserService appUserService;
    private final CandidateRepository candidateRepository;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        AppUser registeredVoter = appUserService.register(request);

            Voter voter = Voter.builder()
                    .userDetails(registeredVoter)
                    .build();

            Voter savedVoter = voterRepository.save(voter);

            return RegisterResponse.builder()
                    .code(HttpStatus.CREATED.value())
                    .id(savedVoter.getId())
                    .isSuccessful(true)
                    .message("Driver Registration Successful")
                    .build();

    }

    @Override
    public Voter login(LoginRequest request) {
        Voter voter = confirmVoter(request.getId());
        if (voter.getUserDetails().getPassword().equals(request.getPassword())){
            return voter;
        }
        else throw new InvalidDetailsException("Invalid Details");
    }

    @Override
    public UpdateResponse updateVoter(UpdateRequest request) {

        Voter voter = confirmVoter(request.getId());;

        Long id = voter.getUserDetails().getId();

        AppUser updateUser = appUserService.updateUser(request, id);
        voter.setUserDetails(updateUser);

        voterRepository.save(voter);
        return new UpdateResponse("Update successful");
    }

    @Override
    public VoteResponse vote(VoteRequest request) {
        return null;
    }

    @Override
    public Voter confirmVoter(Long id) {
        Optional<Voter> foundVoter = voterRepository.findById(id);
        if(foundVoter.isEmpty()) throw new InvalidDetailsException("Invalid Details");
        else {
            return foundVoter.get();
        }
    }

    @Override
    public void saveVoter(Voter voter) {
        voterRepository.save(voter);
    }

    private boolean hasVoted(VoteRequest request){
       return false;
    }


}
