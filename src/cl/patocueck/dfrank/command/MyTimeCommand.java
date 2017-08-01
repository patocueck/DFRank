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

    
    public MyTimeCommand() {
    }
    
    public String execute(GameVo gameVo) {
        List<Time> playerTime = null;
        SQLite sqlite = new SQLite();
        StringBuilder ret = new StringBuilder();
        
        playerTime = sqlite.getTimesFromMap(gameVo.getMap(), gameVo.getPhysic());
        ret.append("^2TIME^7: ==> ");
        int i=1;
        if (playerTime!=null){
            if ( !playerTime.isEmpty() ){
                Time time = playerTime.get(0);
                ret.append("^7");ret.append(time.getPlayer());ret.append("^7: [");
                ret.append(time.getMap()); ret.append("] ["); ret.append(time.getPhysic());
                ret.append("] [");ret.append(time.getDate());ret.append("] [^2");
                ret.append(time.getTime());ret.append("^7]");
            }
        }else{
            ret.append("^2No record in this map");
        }
            
        return ret.toString();
    }
    
}
