package service;
import dao.Reviewdao;
import model.Review;
import java.util.*;

public class ReviewService {
    Reviewdao dao=new Reviewdao();
    public void add(Review r)
    {
        if(r.getRating()<1 || r.getRating()>5)
        {
            System.out.println("Rating phai tu 1 den 5");
            return;
        }
        dao.add(r);
    }
    public List<Review> getbyShopId(int shopId)
    {
        return dao.getAll(shopId);
    }
    public void update(Review r)
    {
        dao.upadte(r);
    }
    public void deleteR(int id)
    {
        dao.delete(id);
    }
}
