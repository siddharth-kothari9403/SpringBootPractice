package com.example.explorecalijpa.web;

import com.example.explorecalijpa.model.TourRating;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.explorecalijpa.business.TourRatingService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Tour Rating Controller
 * Created by Mary Ellen Bowman
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
@Slf4j
@Tag(name = "Tour Rating", description = "the rating for a Tour API")
public class TourRatingController {
    private final TourRatingService tourRatingService;

    public TourRatingController(TourRatingService tourRatingService) {
        this.tourRatingService = tourRatingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a Tour Rating")
    public void createTourRating(@PathVariable int tourId, @RequestBody @Valid RatingDto ratingDto) {
        log.info("POST /tours/{}/ratings", tourId);
        tourRatingService.createNew(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
    }

    @GetMapping
    @Operation(summary = "Lookup All Ratings for a Tour")
    public List<RatingDto> getAllRatingsForTour(@PathVariable int tourId) {
        log.info("GET /tours/{}/ratings", tourId);
        List<TourRating> tourRatings = tourRatingService.lookupRatings(tourId);
        return tourRatings.stream().map(RatingDto::new).toList();
    }

    @GetMapping("/average")
    @Operation(summary = "Get The Average Score for a Tour")
    public Map<String, Double> getAverage(@PathVariable int tourId) {
        log.info("GET /tours/{}/ratings/average", tourId);
        return Map.of("average", tourRatingService.getAverageScore(tourId));
    }

    @PutMapping
    @Operation(summary = "Modify all Tour Rating Attributes")
    public RatingDto updateWithPut(@PathVariable int tourId, @RequestBody @Valid RatingDto ratingDto) {
        log.info("PUT /tours/{}/ratings", tourId);
        return new RatingDto(tourRatingService.update(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment()));
    }

    @DeleteMapping("{customerId}")
    @Operation(summary = "Delete a Customer's Rating of a Tour")
    public void delete(@PathVariable int tourId, @PathVariable int customerId) {
        log.info("DELETE /tours/{}/ratings/{}", tourId, customerId);
        tourRatingService.delete(tourId, customerId);
    }

    @PatchMapping
    @Operation(summary = "Modify Some Tour Rating Attributes")
    public RatingDto updateWithPatch(@PathVariable int tourId, @RequestBody RatingDto ratingDto) {
        log.info("PATCH /tours/{}/ratings", tourId);
        return new RatingDto(tourRatingService.updateSome(tourId, ratingDto.getCustomerId(), Optional.ofNullable(ratingDto.getScore()), Optional.ofNullable(ratingDto.getComment())));
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Give Many Tours Same Score")
    public void createManyTourRatings(@PathVariable int tourId,
                                      @RequestParam(value = "score") int score,
                                      @RequestBody List<Integer> customers) {
        log.info("POST /tours/{}/ratings/batch?score={}", tourId, score);
        tourRatingService.rateMany(tourId, score, customers);
    }
}
