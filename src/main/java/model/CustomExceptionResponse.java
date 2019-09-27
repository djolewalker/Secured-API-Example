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
public class CustomExceptionResponse {

    private String Title;

    private int StatusCode;

    private String message;

    public CustomExceptionResponse(int StatusCode, String message, String Title) {
        this.StatusCode = StatusCode;
        this.message = message;
        this.Title = Title;
    }

    public CustomExceptionResponse(String message) {
        this.message = message;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
