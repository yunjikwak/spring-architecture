package com.example.demo.repository;

import com.example.demo.repository.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    Optional<Team> findById(Integer id);
    Team save(Team entity);
    void deleteById(Integer id);
}
