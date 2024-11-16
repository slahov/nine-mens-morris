package sk.tuke.kpi.kp.ninemensmorris.service;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ninemensmorris.entity.Rating;
import sk.tuke.kpi.kp.ninemensmorris.service.rating.RatingService;
import sk.tuke.kpi.kp.ninemensmorris.service.rating.RatingServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
public class RatingServiceTest
{
    private RatingService ratingService = new RatingServiceJDBC();

    private Date date = new Date();
    @Test
    public void testReset()
    {
        ratingService.reset();
        assertEquals(0,ratingService.getRating("nine mens morris","slavka"));
    }

    @Test
    public void testGetAverageRating()
    {
        ratingService.reset();
        ratingService.setRating(new Rating("denis","nine mens morris",3,date));
        ratingService.setRating(new Rating("denis","nine mens morris",5,date));
        ratingService.setRating(new Rating("slavka","nine mens morris",1,date));
        ratingService.setRating(new Rating("david","nine mens morris",1,date));
        ratingService.setRating(new Rating("majo","nine mens morris",1,date));
        ratingService.setRating(new Rating("dominik","mines",5, date));
        ratingService.setRating(new Rating("peto","mines",1, date));
        var rating = ratingService.getAverageRating("mines");
        assertEquals(3,rating);
        rating = ratingService.getAverageRating("nine mens morris");
        assertEquals(2,rating);
    }

    @Test
    public void testSetRating()
    {
        ratingService.reset();
        ratingService.setRating(new Rating("slavka", "nine mens morris",1, date));
        ratingService.setRating(new Rating("slavka", "mines",3, date));
        ratingService.setRating(new Rating("denis", "nine mens morris",5, date));
        ratingService.setRating(new Rating("denis", "nine mens morris",2, date));
        var rating = ratingService.getRating("slavka", "nine mens morris");
        assertEquals(1,rating);
        rating = ratingService.getRating("slavka", "mines");
        assertEquals(3,rating);
        rating = ratingService.getRating("denis", "nine mens morris");
        assertEquals(5,rating);
    }

    @Test
    public void testGetRating()
    {
        ratingService.reset();
        ratingService.setRating(new Rating("slavka","nine mens morris",3, date));
        ratingService.setRating(new Rating("slavka","nine mens morris",5, date));
        ratingService.setRating(new Rating("stano","nine mens morris",1, date));
        ratingService.setRating(new Rating("david","nine mens morris",2, date));
        ratingService.setRating(new Rating("denis","mines",4, date));
        var ratings = ratingService.getRating("slavka", "nine mens morris");
        assertEquals(3,ratings);
        ratings = ratingService.getRating("david", "nine mens morris");
        assertEquals(2,ratings);
        ratings = ratingService.getRating("denis", "mines");
        assertEquals(4,ratings);
    }

}
