package com.example.explorecalijpa.repo;

import com.example.explorecalijpa.model.Difficulty;
import com.example.explorecalijpa.model.Tour;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Tag(name = "Tours", description = "The Tour API")
public interface TourRepository extends JpaRepository<Tour, Integer> {
    List<Tour> findByDifficulty(Difficulty difficulty);

    List<Tour> findByTourPackageCode(String tourPackageCode);
}
