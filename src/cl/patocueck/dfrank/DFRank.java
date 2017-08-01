/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.patocueck.dfrank;

import cl.patocueck.dfrank.command.CommandListener;
import cl.patocueck.dfrank.command.MyTimeCommand;
import cl.patocueck.dfrank.command.ReachCommand;
import cl.patocueck.dfrank.command.TopCommand;
import cl.patocueck.dfrank.console.Q3Console;
import cl.patocueck.dfrank.vo.GameVo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pato
 */
public class DFRank {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Q3Console q3Console = new Q3Console();
        boolean bSalir = false;
        CommandListener cmdListener;
        String sConsole;
        
        try {
            
            q3Console.initialize();
            cmdListener = new CommandListener();
            
            while(!bSalir){
                
                sConsole = q3Console.getConsoleText();
                q3Console.clearConsole();
                Thread.sleep(600);
                
                //Read Console for a reach time
                List<ReachCommand> reachCommands = cmdListener.getReachCommands(sConsole);
                for (ReachCommand reachCommand : reachCommands) {
                    q3Console.sendMessageToConsole("say " + reachCommand.execute());
                }
                
                //Read Console for a !mytime command
                List<MyTimeCommand> myTimeCommands = cmdListener.getMyTimeCommand(sConsole);
                for (MyTimeCommand myTimeCommand : myTimeCommands) {
                    GameVo gameVo = q3Console.getGameValues();
                    q3Console.sendMessageToConsole("say " + myTimeCommand.execute(gameVo)); 
                }
               
                //Read Console for a !top command
                List<TopCommand> topCommands = cmdListener.getTopCommand(sConsole);
                for (TopCommand topCommand : topCommands) {
                    GameVo gameVo = q3Console.getGameValues();
                    q3Console.sendMessageToConsole("say " + topCommand.execute(sConsole, gameVo));
                }               
            }
        } catch (Exception ex) {
            Logger.getLogger(DFRank.class.getName()).log(Level.SEVERE, null, ex);
        }                  
    }    
}
