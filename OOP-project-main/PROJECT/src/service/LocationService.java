package service;

import java.util.*;
import model.Location;
import dao.Locationdao;

public class LocationService {
    Locationdao locatedao = new Locationdao();

    public void add(Location locate) {
        if (locate.getDetail() == null || locate.getDetail().isEmpty()) {
            System.out.println("Dia chi khong hop le");
            return;
        }
        if (locate.getPhone().length() < 9 || !locate.getPhone().matches("\\d+")) {
            System.out.println("So dien thoai khong hop le");
            return;
        }
        locatedao.AddLocate(locate);
    }
    public List<Location> getAll(int userId)
    {
        return locatedao.getAllLocate(userId);
    }
    public void updateLocate(Location locate)
    {
        locatedao.updateLocate(locate);
    }
    public void delteLocate(int id)
    {
        locatedao.deleteLocate(id);
    }
}
