package com.viethoc.smartbuilding.repository;

import com.viethoc.smartbuilding.model.Automate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomateRepository extends JpaRepository<Automate, Long> {
}
