package service;

import dao.Shopdao;
import model.Shop;
import java.util.*;
public class ShopService {
    Shopdao dao=new Shopdao();
    public void createShop(Shop s)
    {
        if(s.getName()==null || s.getName().isEmpty())
        {
            System.out.println("Ten khong hop le");
            return;
        }
        dao.add(s);
    }
    public List<Shop> getAll()
    {
        return dao.getAll();
    }
    public void update(Shop s)
    {
        dao.update(s);
    }
    public void close(int id)
    {
        dao.close(id);
    }
    public void remove(int id)
    {
        dao.removeShop(id);
    }
}
