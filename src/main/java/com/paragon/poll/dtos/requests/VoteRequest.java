package com.paragon.poll.dtos.requests;

import com.paragon.poll.data.models.Party;
import com.paragon.poll.data.models.Sit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class VoteRequest {
    private Long candidateId;
    private Sit candidateSit;
    private Party candidateParty;

}
