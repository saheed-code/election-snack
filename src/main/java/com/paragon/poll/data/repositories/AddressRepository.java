package com.paragon.poll.data.repositories;

import com.paragon.poll.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
