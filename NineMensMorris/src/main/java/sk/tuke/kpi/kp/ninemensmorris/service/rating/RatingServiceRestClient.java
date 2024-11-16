package sk.tuke.kpi.kp.ninemensmorris.service.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.ninemensmorris.entity.Rating;


public class RatingServiceRestClient implements RatingService
{
    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating)
    {
        restTemplate.postForEntity(url + "/rating", rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game)
    {
       return restTemplate.getForEntity(url + "/rating" + game, Integer.class).getBody();
    }

    @Override
    public Rating getRating(String game, String player)
    {
        return restTemplate.getForEntity(url + "/rating/" + game + player, Rating.class).getBody();
    }

    @Override
    public void reset()
    {
        throw new UnsupportedOperationException("Not supported via web service!");
    }
}
