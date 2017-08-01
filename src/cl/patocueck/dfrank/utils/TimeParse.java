/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.patocueck.dfrank.utils;

/**
 *
 * @author pato
 */
public class TimeParse {
    
    private String noFormatTime;
    private String formatTime;

    private TimeParse(){}
    public TimeParse(String noFormatTime){
        this.noFormatTime = noFormatTime;
        timeParse(noFormatTime);
    }
    
    private void timeParse(String noFormatTime) {
        switch(noFormatTime.length()){
            case 3:
                formatTime = "00."+noFormatTime;
                break;
            case 4:
                formatTime = "0"+noFormatTime.substring(0, 1)+"."+noFormatTime.substring(1,noFormatTime.length() );
                break;
            case 5:
                formatTime = noFormatTime.substring(0, 2)+"."+noFormatTime.substring(2,noFormatTime.length() );
                break;
            case 6:
                formatTime = noFormatTime.substring(0, 3)+"."+noFormatTime.substring(3,noFormatTime.length() );
                break;
        }
    }
    
    public String getNoFormatTime() {
        return noFormatTime;
    }

    public void setNoFormatTime(String noFormatTime) {
        this.noFormatTime = noFormatTime;
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }    
}
