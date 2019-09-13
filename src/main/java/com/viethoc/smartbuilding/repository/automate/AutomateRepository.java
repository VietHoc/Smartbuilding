package com.viethoc.smartbuilding.repository.automate;

import com.viethoc.smartbuilding.model.Automate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomateRepository extends JpaRepository<Automate, Long> {
    List<Automate> findAllByActive(Boolean active);
}
