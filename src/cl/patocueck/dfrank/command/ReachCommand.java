/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.patocueck.dfrank.command;

import cl.patocueck.dfrank.db.SQLite;
import cl.patocueck.dfrank.enums.MessageEnum;
import cl.patocueck.dfrank.model.Time;
import cl.patocueck.dfrank.utils.TimeParse;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author pato
 */
public class ReachCommand {
    
    private String line;
    private String map;
    private String time;
    private String physic;
    private String player;
    private String date;

    
    public ReachCommand(String sConsoleLine) {
        this.line = sConsoleLine;
        initialize();
    }

    private void initialize() {
        String word[] = this.line.split(" ");
        this.map= word[3];
        this.map = this.map.replaceAll("\"", "").toUpperCase();
        this.time = word[2];
        this.player = word[4];
        this.player = this.player.replaceAll("\"", "");
        this.date = word[11];
        this.physic = word[6];
        if ( this.physic.equals("1") ) this.physic = "CPM";
        else this.physic = "VQ3";
        
    }
    /**
    * Si no hay tiempo antiguo -> inserto nuevo tiempo
    * si hay tiempo antiguo-> inserto el mejor
     * @return Devuelve un String con el mensaje para el usuario.
    */
    public String execute(){
        String ret = String.format(MessageEnum.NOK.getMessage(),player);
        Time newTime = new Time();
        newTime.setPlayer(player);
        newTime.setMap(map);
        newTime.setPhysic(physic);
        newTime.setTime(new TimeParse(time).getFormatTime());
        newTime.setDate(date);
        
        SQLite sqlite = new SQLite();
        
        Time oldTime = sqlite.getTimeFromPlayer(player, map, physic);
        
        //If there'is no time, save the new time
        if ( oldTime == null ){    
            ret = String.format(MessageEnum.FIRST_TIME.getMessage(), newTime.getPlayer(), newTime.getTime());
            ArrayList<Time> times = sqlite.getTimesFromMap(map, physic);
            if ( times == null ){
                ret = String.format(MessageEnum.BROKE_SERVER_RC.getMessage(), newTime.getPlayer(), newTime.getTime());
            }else{
                Time betterTime = times.get(0);
                if ( betterTime.getTime().compareTo(newTime.getTime()) > 0 ){
                    ret = String.format(MessageEnum.BROKE_SERVER_RC.getMessage(), newTime.getPlayer(), newTime.getTime());
                }
            }
            sqlite.saveTime(newTime);
        }else {
            //If there'is a time, and the newest is better, save it in bd
            if ( oldTime.getTime().compareTo(newTime.getTime()) > 0 ){
                ret = String.format(MessageEnum.IMPROVE.getMessage(), newTime.getPlayer() , newTime.getTime());
                
                //Check if broke the server record
                List<Time> times = sqlite.getTimesFromMap(map, physic);
                if (times != null){
                    Time betterTime = times.get(0);
                    if (betterTime.getTime().compareTo(newTime.getTime()) > 0 ) {
                        ret = String.format(MessageEnum.BROKE_SERVER_RC.getMessage(), newTime.getPlayer(), newTime.getTime());
                    }
                }else{
                    ret = String.format(MessageEnum.BROKE_SERVER_RC.getMessage(), newTime.getPlayer(), newTime.getTime());
                }
                sqlite.saveTime(newTime);
                sqlite.deleteTime(oldTime);
            }else{
                ret = String.format(MessageEnum.NOT_IMPROVE.getMessage(), newTime.getPlayer());
            }
        }
        
        return ret;
    }
    
}
