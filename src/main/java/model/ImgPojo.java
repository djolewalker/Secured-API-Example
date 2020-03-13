/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author dimitrije
 */
public class ImgPojo {
    
    public String name;
    public String base64;

    public ImgPojo(String name, String base64) {
        this.name = name;
        this.base64 = base64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
    
  
}
