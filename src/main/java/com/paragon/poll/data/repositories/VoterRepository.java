package com.paragon.poll.data.repositories;

import com.paragon.poll.data.models.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Long> {
}
