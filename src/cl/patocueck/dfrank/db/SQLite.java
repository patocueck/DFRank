/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.patocueck.dfrank.db;

import cl.patocueck.dfrank.model.Time;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pato
 */
public class SQLite {
    
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:db/dfrank.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public Time getTimeFromPlayer(String player, String map, String physic) {
        Time time = null;
        String sql = "SELECT player, map, time, date, physic FROM TIME WHERE player = ? AND map = ? AND physic = ?";
         Connection conn = this.connect();
        try {
            
           
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            
            pstmt.setString(1,player);
            pstmt.setString(2,map);
            pstmt.setString(3,physic);
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            if (rs.next()) {
                time = new Time();
                time.setPlayer(player);
                time.setMap(map.toUpperCase());
                time.setTime(rs.getString("time"));
                time.setDate(rs.getString("date"));
                time.setPhysic(rs.getString("physic"));
            }
            
            conn = null;
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());          
        }
        
        return time;
    }

    public void saveTime(Time time) {
        String sql = "INSERT INTO TIME(player, map, time, date, physic) VALUES(?,?,?,?,?)";
 
        try {
            
            Connection conn = this.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, time.getPlayer());
            pstmt.setString(2, time.getMap().toUpperCase());
            pstmt.setString(3, time.getTime());
            pstmt.setString(4, time.getDate());
            pstmt.setString(5, time.getPhysic());
            pstmt.executeUpdate();
            conn = null;
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTime(Time oldTime) {
        String sql = "DELETE FROM TIME WHERE player = ? AND map = ? AND time = ? AND date = ? AND physic = ?";
 
        try {
            
            Connection conn = this.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql);
 
            pstmt.setString(1, oldTime.getPlayer());
            pstmt.setString(2, oldTime.getMap().toUpperCase());
            pstmt.setString(3, oldTime.getTime());
            pstmt.setString(4, oldTime.getDate());
            pstmt.setString(5, oldTime.getPhysic());
            
            pstmt.executeUpdate();
            conn = null;
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Time> getTimesFromMap(String map, String physic) {
        ArrayList<Time> times = null;
        
        String sql = "SELECT player, map, time, date, physic FROM TIME WHERE map = ? AND physic = ? ORDER BY time ASC";
        
        try {
            
            Connection conn = this.connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            
            pstmt.setString(1,map.toUpperCase());
            pstmt.setString(2,physic);
            
            ResultSet rs  = pstmt.executeQuery();
            
            times = new ArrayList();
            // loop through the result set
            while (rs.next()) {
                Time time = new Time();
                time.setPlayer(rs.getString("player"));
                time.setMap(map.toUpperCase());
                time.setTime(rs.getString("time"));
                time.setDate(rs.getString("date"));
                time.setPhysic(physic);
                times.add(time);
            }
            conn = null;
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        return times.isEmpty()? null: times;
    }

    public Time getTimesFromPlayer(String player, String map, String physic) {
        Time time = null;
        
        String sql = "SELECT player, map, time, date, physic FROM TIME WHERE player = ? AND map = ? AND physic = ? ORDER BY time ASC";
        
        try {
            
            Connection conn = this.connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            
            pstmt.setString(1,player);
            pstmt.setString(2,map.toUpperCase());
            pstmt.setString(3,physic);
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                time = new Time();
                time.setPlayer(rs.getString("player"));
                time.setMap(map.toUpperCase());
                time.setTime(rs.getString("time"));
                time.setDate(rs.getString("date"));
                time.setPhysic(physic);
            }
            conn = null;
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        return time;
    }

}
