package com.paragon.poll.services;

import com.paragon.poll.cloud.CloudService;
import com.paragon.poll.data.models.AppUser;
import com.paragon.poll.data.models.Candidate;
import com.paragon.poll.data.models.Voter;
import com.paragon.poll.data.repositories.AppUserRepository;
import com.paragon.poll.data.repositories.CandidateRepository;
import com.paragon.poll.dtos.requests.RegisterRequest;
import com.paragon.poll.dtos.requests.UpdateRequest;
import com.paragon.poll.dtos.requests.UpdateResponse;
import com.paragon.poll.dtos.responses.RegisterResponse;
import com.paragon.poll.exceptions.ImageUploadException;
import com.paragon.poll.exceptions.UserAlreadyExistException;
import com.paragon.poll.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CandidateServiceImpl implements CandidateService {
    private final AppUserService appUserService;
    private final CandidateRepository candidateRepository;
    private final CloudService cloudService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        AppUser candidateDetails = appUserService.register(request);

        var pedUrl = cloudService.upload(request.getPed());
        if (pedUrl == null)
            throw new ImageUploadException("PED Upload Failed!");

        Candidate candidate = Candidate.builder()
                .userDetails(candidateDetails)
                .ped(pedUrl)
                .sit(request.getSit())
                .party(request.getParty())
                .build();

        Candidate savedCandidate = candidateRepository.save(candidate);

        return RegisterResponse.builder()
                .code(HttpStatus.CREATED.value())
                .id(savedCandidate.getId())
                .isSuccessful(true)
                .message("Driver Registration Successful")
                .build();

    }

    @Override
    public UpdateResponse update(UpdateRequest request) {
        Candidate candidate = confirmCandidate(request.getId());

        Long id = candidate.getUserDetails().getId();
        AppUser updateUser = appUserService.updateUser(request, id);
        candidate.setUserDetails(updateUser);

        candidateRepository.save(candidate);
        return new UpdateResponse("Update successful");
    }

    public Candidate confirmCandidate(Long id){
        Optional<Candidate> foundCandidate = candidateRepository.findById(id);
        if(foundCandidate.isEmpty()) throw new UserNotFoundException("User not found");
        else return foundCandidate.get();
    }

    public void saveCandidate(Candidate candidate) {
        candidateRepository.save(candidate);
    }

}
