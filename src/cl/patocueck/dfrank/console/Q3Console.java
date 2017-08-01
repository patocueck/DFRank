/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.patocueck.dfrank.console;

import cl.patocueck.dfrank.vo.GameVo;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;


/**
 *
 * @author pato
 */
public class Q3Console {
    
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
        
        boolean EnumWindows(WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);
        
        int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);
        
        HWND FindWindowA(String winClass, String title);
        
        int SendMessageA(HWND hWnd, int msg, int wParam, byte[] lParam );//recibe texto
        int SendMessageA(HWND hWnd, int msg, int wParam, int lParam );//Envia texto char a char
        
        HWND FindWindowExA(HWND hwndParent, HWND childAfter, String className, String windowName);
    }
    
    private final User32 user32 = User32.INSTANCE;
    private HWND hWndQ3 = null;
    private HWND hWndText = null;
    private HWND hWndLbl = null;
    private int WM_CHAR = 0x0102;
    private int WM_GETTEXTLENGTH = 0x000E;
    private int WM_GETTEXT = 0x000D;
    private int WM_SETTEXT = 0x000c;
    
    public void initialize() throws Exception{
        
        String sClassnameParent = "Q3 WinConsole";
        String sClassnameChild = "Edit";
        
        hWndQ3 = user32.FindWindowA(sClassnameParent, null);
        hWndText = user32.FindWindowExA(hWndQ3, null, sClassnameChild,null);
        hWndLbl = user32.FindWindowExA(hWndQ3, hWndText, sClassnameChild,null);
        
        if (hWndQ3 == null || hWndText == null || hWndLbl == null)
            throw new Exception("Error al encontrar Quake 3 Arena Defrag Server");
    }
    
    public boolean sendMessageToConsole(String message){
        char[] charArrayToSend = message.toCharArray();
        for (int i = 0; i < message.length(); i++)
            user32.SendMessageA( hWndText, (int) WM_CHAR, (int)charArrayToSend[i], 1 );
        
        user32.SendMessageA( hWndText, (int) WM_CHAR, (int)13, 1 );
        return true;
    }
    
    public String getConsoleText(){
        int size;
        byte[] console;
        
        size = user32.SendMessageA(hWndLbl, (int)WM_GETTEXTLENGTH, (int)0, (int)0);
        console = new byte[size +1];
        user32.SendMessageA(hWndLbl, WM_GETTEXT, size + 1, console);
        
        return Native.toString(console);
    }
    
    public boolean clearConsole(){
        user32.SendMessageA(this.hWndLbl, WM_SETTEXT, 0, 0);
        return true;
    }
        
    public GameVo getGameValues(){
        GameVo gamevO = new GameVo();
        
        gamevO.setMap(this.getMap());
        gamevO.setPhysic(this.getPhysic());
        
        return gamevO;
    }
    
    public String getMap(){
        String line;
        String[] lines;
        String map = null;
        
        try{
            clearConsole();
            sendMessageToConsole("mapname");
            Thread.sleep(100);
            lines = getConsoleText().split("\r\n");
            line = lines[lines.length -1];
            //"mapname" is:"St1" default:"nomap"
            map = line.substring(13);
            map = map.split("\"")[1].toUpperCase();
        }catch(Exception ex){
            ex.printStackTrace();
            return getMap();
        }
        
        return map;
    }
    
    public String getPhysic(){
        String line;
        String[] lines;
        String physic = null;
        
        try{
            clearConsole();
            sendMessageToConsole("df_promode");
            Thread.sleep(100);
            lines = getConsoleText().split("\r\n");
            line = lines[lines.length -1];
            
            //"df_promode" is:"1" default:"0"
            physic = line.substring(17,18);
            if (physic.equals("1")) physic = "CPM";
            else physic = "VQ3";
            
        }catch(Exception ex){
            ex.printStackTrace();
            return getPhysic();
        }
        
        
        return physic;
    }
}