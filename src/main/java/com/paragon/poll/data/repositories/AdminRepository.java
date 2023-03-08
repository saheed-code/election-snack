package com.paragon.poll.data.repositories;

import com.paragon.poll.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
