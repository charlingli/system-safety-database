/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customObjects;

/**
 *
 * @author Charling
 */
public class searchObject {

    private String fieldName;
    private String userInput;
    private String fieldType;
    private String entity1Name;
    private String entity2Name;
    private String entity3Name;
    private String tableAlias;
    private String relationType;
    private String FieldDescription;

    public searchObject(String fieldName, String userInput, String fieldType, String entity1Name, String entity2Name, String entity3Name, String tableAlias, String relationType, String FieldDescription) {
        this.fieldName = fieldName;
        this.userInput = userInput;
        this.fieldType = fieldType;
        this.entity1Name = entity1Name;
        this.entity2Name = entity2Name;
        this.entity3Name = entity3Name;
        this.tableAlias = tableAlias;
        this.relationType = relationType;
        this.FieldDescription = FieldDescription;
    }
    
    public searchObject() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getEntity1Name() {
        return entity1Name;
    }

    public void setEntity1Name(String entity1Name) {
        this.entity1Name = entity1Name;
    }

    public String getEntity2Name() {
        return entity2Name;
    }

    public void setEntity2Name(String entity2Name) {
        this.entity2Name = entity2Name;
    }

    public String getEntity3Name() {
        return entity3Name;
    }

    public void setEntity3Name(String entity3Name) {
        this.entity3Name = entity3Name;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getFieldDescription() {
        return FieldDescription;
    }

    public void setFieldDescription(String FieldDescription) {
        this.FieldDescription = FieldDescription;
    }

}
