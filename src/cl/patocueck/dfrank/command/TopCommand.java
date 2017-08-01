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
   
    public TopCommand() {
    }
        
    public String execute(GameVo gameVo){
        
        List<Time> playerTime = null;
        SQLite sqlite = new SQLite();
        StringBuilder ret = new StringBuilder();
        
        playerTime = sqlite.getTimesFromMap(gameVo.getMap(), gameVo.getPhysic());
        ret.append("^2TOP 10^7: ==> ");
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
