package com.viethoc.smartbuilding.repository;

import com.viethoc.smartbuilding.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchListRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findAllByAutomateId(Long automateId);
    List<Watchlist> findAllByIsActive(Boolean isActive);
}
