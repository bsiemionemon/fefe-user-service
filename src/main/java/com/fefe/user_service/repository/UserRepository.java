package com.fefe.user_service.repository;

import com.fefe.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    @Query(value = "SELECT nextval('loyalty_card_seq')", nativeQuery = true)
    Long getNextLoyaltyCardNumber();
}
