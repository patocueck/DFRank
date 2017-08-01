/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.patocueck.dfrank.command;

import cl.patocueck.dfrank.console.Q3Console;
import cl.patocueck.dfrank.enums.CommandEnum;
import cl.patocueck.dfrank.vo.GameVo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pato
 */
public class CommandListener {
    
    
    
    public List<ReachCommand> getReachCommands(String sConsole){
        
        String[] sConsoleLines = sConsole.split("\r\n");
        List<ReachCommand> cmds = new ArrayList();
        
        for (String sConsoleLine : sConsoleLines) {
            if (sConsoleLine.startsWith("ClientTimerStop")) {
                cmds.add(new ReachCommand(sConsoleLine));
            }
        }
        
        return cmds;
    }

    public List<TopCommand> getTopCommand(String sConsole) {
        String[] sConsoleLines = sConsole.split("\r\n");
        List<TopCommand> topCmds = new ArrayList();
        
        for (String sConsoleLine : sConsoleLines) {
            if (sConsoleLine.startsWith("say:")) {
                try{
                    String[] param = sConsoleLine.split(" ");
                    if (param[2].equalsIgnoreCase("!top")){
                        String map;
                        String physic;
                        try{
                            map = param[3];
                        }catch(ArrayIndexOutOfBoundsException ex){
                            map = null;
                        }
                        try{
                            physic = param[4];
                        }catch(ArrayIndexOutOfBoundsException ex){
                            physic = null;
                        }                        
                        topCmds.add(new TopCommand(map, physic));
                    }
                    
                }catch (ArrayIndexOutOfBoundsException ex){
                    
                }
            }
        }
        
        return topCmds;
    }

    public List<MyTimeCommand> getMyTimeCommand(String sConsole) {
        String[] sConsoleLines = sConsole.split("\r\n");
        List<MyTimeCommand> myTimeCmds = new ArrayList();
        
        for (String sConsoleLine : sConsoleLines) {
            if (sConsoleLine.startsWith("say:")) {
                try{
                    String[] param = sConsoleLine.split(" ");
                    if (param[2].equalsIgnoreCase("!mytime")){
                        String player = param[1].substring(0, param[1].length()-1); 
                        String map;
                        String physic;
                        try{
                            map = param[3];
                        }catch(ArrayIndexOutOfBoundsException ex){
                            map = null;
                        }
                        try{
                            physic = param[4];
                        }catch(ArrayIndexOutOfBoundsException ex){
                            physic = null;
                        }  
                        myTimeCmds.add(new MyTimeCommand(player, map, physic));     
                    }
                    
                }catch (ArrayIndexOutOfBoundsException ex){
                    System.out.println("ArrayIndexOutOfBoundsException: " + ex.getLocalizedMessage());
                }
            }
        }
        
        return myTimeCmds;
    }
}
