/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customObjects;

/**
 *
 * @author Juan David
 */
public class validateIdObject {

    private boolean validationFlag;
    private String answerString;

    public validateIdObject() {
    }
    
    public validateIdObject(boolean validationFlag, String errorString) {
        this.validationFlag = validationFlag;
        this.answerString = errorString;
    }

    public boolean isValidationFlag() {
        return validationFlag;
    }

    public void setValidationFlag(boolean validationFlag) {
        this.validationFlag = validationFlag;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    
}
