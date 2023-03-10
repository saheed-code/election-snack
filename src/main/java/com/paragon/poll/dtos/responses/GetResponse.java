package com.paragon.poll.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String nin;
    private String ped;
    private Boolean isApproved;
}
