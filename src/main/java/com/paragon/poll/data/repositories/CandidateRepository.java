package com.paragon.poll.data.repositories;

import com.paragon.poll.data.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
