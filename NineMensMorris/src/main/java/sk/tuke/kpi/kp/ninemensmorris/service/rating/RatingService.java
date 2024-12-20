package sk.tuke.kpi.kp.ninemensmorris.service.rating;

import sk.tuke.kpi.kp.ninemensmorris.entity.Rating;

public interface RatingService {
    void setRating(Rating rating) throws RatingException;
    int getAverageRating(String game) throws RatingException;
    Rating getRating(String game, String player) throws RatingException;
    void reset() throws RatingException;
}
