/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.patocueck.dfrank.command;

import cl.patocueck.dfrank.db.SQLite;
import cl.patocueck.dfrank.model.Time;
import cl.patocueck.dfrank.vo.GameVo;
import java.util.List;

/**
 *
 * @author pato
 */
public class MyTimeCommand {
    
    private String player;
    private String map;
    private String physic;
    
    public MyTimeCommand(String player, String map, String physic) {
        this.player = player;
        this.map = map;
        this.physic = physic!=null?physic.toUpperCase():null;
    }
    
    public String execute(GameVo gameVo) {
        Time playerTime = null;
        SQLite sqlite = new SQLite();
        StringBuilder ret = new StringBuilder();
        
        if (map == null) map = gameVo.getMap();
        if (physic == null) physic = gameVo.getPhysic();
        
        playerTime = sqlite.getTimesFromPlayer(player, map, physic);
        
        ret.append("^2TIME^7: ==> ");
        ret.append("^7");ret.append(player);ret.append("^7: [");
        ret.append(map); ret.append("] ["); ret.append(physic);
        ret.append("] ");
        
        if (playerTime!=null){
            ret.append("[");
            ret.append(playerTime.getDate());ret.append("] [^2");
            ret.append(playerTime.getTime());ret.append("^7]");
        }else{
            ret.append("^2No record in this map");
        }
            
        return ret.toString();
    }
    
}
