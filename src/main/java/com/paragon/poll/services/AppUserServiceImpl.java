package com.paragon.poll.services;

import com.paragon.poll.cloud.CloudService;
import com.paragon.poll.data.models.AppUser;
import com.paragon.poll.data.models.Voter;
import com.paragon.poll.data.repositories.AppUserRepository;
import com.paragon.poll.data.repositories.VoterRepository;
import com.paragon.poll.dtos.requests.RegisterRequest;
import com.paragon.poll.dtos.requests.UpdateRequest;
import com.paragon.poll.dtos.responses.RegisterResponse;
import com.paragon.poll.exceptions.ImageUploadException;
import com.paragon.poll.exceptions.InvalidDetailsException;
import com.paragon.poll.exceptions.UserAlreadyExistException;
import com.paragon.poll.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
@AllArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService{
    private final ModelMapper modelMapper;
    private final CloudService cloudService;
    private final AppUserRepository appUserRepository;

    @Override
    public AppUser register(RegisterRequest request) {
        boolean mailExists = checkEmailExists(request.getEmail());

        if (mailExists) throw new UserAlreadyExistException("User already exists");
        else {
            AppUser userDetails = modelMapper.map(request, AppUser.class);

            var imageUrl = cloudService.upload(request.getProfileImage());
            if (imageUrl == null)
                throw new ImageUploadException("Registration Failed!");

            userDetails.setProfileImage(imageUrl);
            userDetails.setCreatedAt(LocalDateTime.now().toString());
            return userDetails;
        }
    }

    @Override
    public AppUser updateUser(UpdateRequest request, Long appUserId) {
        AppUser user = confirmAppUser(appUserId);
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        return user;
    }

    private boolean checkEmailExists(String email){
        Optional<AppUser> user = appUserRepository.findByEmail(email);
        return user.isPresent();
    }

    private AppUser confirmAppUser(Long id){
        Optional<AppUser> foundUser = appUserRepository.findById(id);
        if(foundUser.isEmpty()) throw new UserNotFoundException("User does not exist!");
        else return foundUser.get();
    }
}
