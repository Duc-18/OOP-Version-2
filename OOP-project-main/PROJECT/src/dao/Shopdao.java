package dao;

import model.Shop;
import java.sql.*;
import java.util.*;

public class Shopdao {
    public void add(Shop s)
    {
        try(Connection conn =DBconnection.getConnection())
        {
            String sql ="INSERT INTO shop(name,description,address,status) VALUES(?,?,?,?)";
            PreparedStatement ps= conn.prepareStatement(sql); //gui cau lenh sql
            ps.setString(1,s.getName());
            ps.setString(2,s.getDescription());
            ps.setString(3,s.getAddress());
            ps.setString(4,"active");
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public List<Shop> getAll()
    {
        List<Shop> list=new ArrayList<>();
        try(Connection conn=DBconnection.getConnection())
        {
            String sql = "SELECT * FROM shop";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Shop s = new Shop();
                s.setShopId(rs.getInt("shopId"));
                s.setName(rs.getString("name"));
                s.setAddress(rs.getString("address"));
                s.setStatus(rs.getString("status"));
                list.add(s);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public void update(Shop s)
    {
        try(Connection conn=DBconnection.getConnection())
        {
            String sql="UPDATE shop SET description=?, address=? WHERE shopId=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1,s.getDescription());
            ps.setString(2,s.getAddress());
            ps.setInt(3,s.getShopId());
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void close(int id)
    {
        try(Connection conn=DBconnection.getConnection())
        {
            String sql="UPDATE shop SET status='close' WHERE shopId=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void removeShop(int id)
    {
        try(Connection conn=DBconnection.getConnection())
        {
            String sql="DELETE FROM shop WHERE shopId=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
