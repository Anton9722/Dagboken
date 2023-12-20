package com.dagbok.dagbok;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EntrieRepository extends CrudRepository<Entries, Integer> {

    @Query("SELECT e FROM Entries e WHERE e.date <= CURRENT_DATE")
    List<Entries> findByCurrentOrPastDate();

    @Query("SELECT e FROM Entries e WHERE e.date BETWEEN :startDate AND :endDate")
    List<Entries> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
}
