package dao;
import java.util.*;
import model.Location;
import java.sql.*;

public class Locationdao
{
    public void AddLocate(Location locate)
    {
        try(Connection conn=DBconnection.getConnection())
        {
            String sql="INSERT INTO location(userId,detail,phone) VALUES(?,?,?)";
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setInt(1,locate.getUserId());
            ps.setString(2, locate.getDetail());
            ps.setString(3,locate.getPhone());
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public List<Location> getAllLocate(int userId)
    {
        List<Location> list=new ArrayList<>();
        try(Connection conn=DBconnection.getConnection())
        {
            String sql="SELECT * FROM location WHERE userId=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setInt(1,userId);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                Location diachi=new Location();
                diachi.setUserId(rs.getInt("userId"));
                diachi.setDetail(rs.getString("detail"));
                diachi.setPhone(rs.getString("phone"));
                list.add(diachi);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public void updateLocate(Location locate)
    {
        try(Connection conn=DBconnection.getConnection())
        {
            String sql="UPDATE location SET detail=?, phone=? WHERE locationId=? AND userId=?";
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setString(1,locate.getDetail());
            ps.setString(2, locate.getPhone());
            ps.setInt(3,locate.getLocationId());
            ps.setInt(4,locate.getUserId());
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void deleteLocate(int id)
    {
        try(Connection conn=DBconnection.getConnection())
        {
            String sql="DELETE FROM location where locationId=?";
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
