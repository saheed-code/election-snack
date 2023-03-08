package com.paragon.poll.services;

import com.paragon.poll.data.models.AppUser;
import com.paragon.poll.dtos.requests.RegisterRequest;
import com.paragon.poll.dtos.requests.UpdateRequest;
import com.paragon.poll.dtos.responses.RegisterResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AppUserService {
    AppUser register(RegisterRequest request);
    AppUser updateUser(UpdateRequest request, Long appUserId);
}
