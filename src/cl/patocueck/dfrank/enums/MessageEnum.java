/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.patocueck.dfrank.enums;

/**
 *
 * @author pato
 */
public enum MessageEnum {
    
    FIRST_TIME("01","%s ^7Congrats, you make your ^3first time ^7with: ^3%s^7."),
    IMPROVE("02","%s ^7Congrats, you ^3improve ^7your time ^7with: ^3%s^7."),
    BROKE_SERVER_RC("03","%s ^7Congrats!!!, you ^2broke ^7the server ^2RECORD!!! ^7with: ^3%s^7."),
    NOT_IMPROVE("03","%s ^7:(!!!, you dont improve your time."),	
    NOK("99","%s ^7problem processing your ^1time^7. contact ^1Administrator^7.");
        
    private String code;
    private String menssage;

    private MessageEnum(String code, String menssage){
            this.code = code;
            this.menssage = menssage;
    }

    public String getCode() {
            return code;
    }
    public void setCode(String code) {
            this.code = code;
    }
    public String getMessage() {
            return menssage;
    }
    public void setMenssage(String menssage) {
            this.menssage = menssage;
    }
}
