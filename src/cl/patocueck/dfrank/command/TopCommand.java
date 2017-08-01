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
public class TopCommand {
    
    private String map;
    private String physic;
    
    public TopCommand(String map, String physic) {
        this.map = map;
        this.physic = physic!=null?physic.toUpperCase():null;
    }
        
    public String execute(String sConsole, GameVo gameVo){
        List<Time> playerTime = null;
        SQLite sqlite = new SQLite();
        StringBuilder ret = new StringBuilder();
        
        if (map == null) map = gameVo.getMap();
        if (physic == null) physic = gameVo.getPhysic();
                
        playerTime = sqlite.getTimesFromMap(map, physic);
        
        ret.append("^2TOP 10^7: ^7[");
        ret.append(map);ret.append("] [");
        ret.append(physic);ret.append("]");
        ret.append(" ==> ");
        int i=1;
        if (playerTime!=null){
            for (Time time : playerTime) {
                ret.append("^7");ret.append(i);ret.append("^7) ^7");
                ret.append(time.getPlayer());
                ret.append(" ^2");
                ret.append(time.getTime());
                ret.append("^3 ...");
                i++;
            }
        }else{
                ret.append("^2No records in this map");
        }
            
        return ret.toString();
    }
}
