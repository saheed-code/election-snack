package com.paragon.poll.dtos.requests;

import com.paragon.poll.data.models.Address;
import com.paragon.poll.data.models.Gender;
import com.paragon.poll.data.models.Party;
import com.paragon.poll.data.models.Sit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private Address address;
    private String nin;
    private String password;
    private MultipartFile profileImage;
    private MultipartFile ped;

    private Sit sit;
    private Party party;
}
