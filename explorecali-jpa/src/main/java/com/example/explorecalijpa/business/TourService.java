package com.example.explorecalijpa.business;

import com.example.explorecalijpa.model.Difficulty;
import com.example.explorecalijpa.model.Region;
import com.example.explorecalijpa.model.Tour;
import com.example.explorecalijpa.model.TourPackage;
import com.example.explorecalijpa.repo.TourPackageRepository;
import com.example.explorecalijpa.repo.TourRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TourService {

    private final TourPackageRepository tourPackageRepository;
    private final TourRepository tourRepository;

    public TourService(TourPackageRepository tourPackageRepository, TourRepository tourRepository) {
        this.tourPackageRepository = tourPackageRepository;
        this.tourRepository = tourRepository;
    }

    public Tour createTour(String tourPackageName, String title, String description, String blurb, Integer price, String duration, String bullets, String keywords, Difficulty difficulty, Region region){
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(() -> new RuntimeException("Tour Package Not Found for id" + tourPackageName));
        return tourRepository.save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage, difficulty, region));
    }

    public long total(){
        return tourRepository.count();
    }

    public List<Tour> lookupByPackage(String tourPackageCode){
        return tourRepository.findByTourPackageCode(tourPackageCode);
    }

    public List<Tour> lookupByDifficulty(Difficulty difficulty){
        return tourRepository.findByDifficulty(difficulty);
    }
}
