package com.paragon.poll.data.repositories;

import com.paragon.poll.data.models.Party;
import com.paragon.poll.data.models.Sit;
import com.paragon.poll.data.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
        Optional<Vote> findByCandidateId(Long candidateId);
        Optional<Vote> findByCandidateSit(Sit candidateSit);
        Optional<Vote> findByCandidateParty(Party candidateParty);
}
