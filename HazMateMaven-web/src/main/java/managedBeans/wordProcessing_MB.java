/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.similarityObject;
import ejb.DbCauseFacadeLocal;
import ejb.DbConsequenceFacadeLocal;
import ejb.DbControlFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbcommonWordFacadeLocal;
import ejb.DbindexedWordFacadeLocal;
import ejb.DbsystemParametersFacadeLocal;
import entities.DbcommonWord;
import entities.DbindexedWord;
import entities.DbindexedWordPK;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
@Named(value = "wordProcessing_MB")
@RequestScoped
public class wordProcessing_MB {

    @EJB
    private DbsystemParametersFacadeLocal dbsystemParametersFacade;
    @EJB
    private DbControlFacadeLocal dbControlFacade;
    @EJB
    private DbConsequenceFacadeLocal dbConsequenceFacade;
    @EJB
    private DbCauseFacadeLocal dbCauseFacade;
    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
    @EJB
    private DbindexedWordFacadeLocal dbindexedWordFacade;
    @EJB
    private DbcommonWordFacadeLocal dbcommonWordFacade;
    

    public wordProcessing_MB() {
    }

    public List<similarityObject> findSimilarity(String newDescription, String objectType) {
        return dbindexedWordFacade.findPotentialDuplicates(newDescription, objectType);
    }

    public void indexDatabase() {
        int result = dbindexedWordFacade.truncateTable();
        List<temporalObj> processingListHazards = new ArrayList<>();
        dbHazardFacade.findAll().forEach((tmp) -> {
            processingListHazards.add(new temporalObj(tmp.getHazardId(), tmp.getHazardDescription()));
        });
        if (!processList(processingListHazards, "hazard")) {
            System.err.println("There was an error processing the hazard table.");
        }

        List<temporalObj> processingListCauses = new ArrayList<>();
        dbCauseFacade.findAll().forEach((tmp) -> {
            processingListCauses.add(new temporalObj(tmp.getCauseId().toString(), tmp.getCauseDescription()));
        });
        if (!processList(processingListCauses, "cause")) {
            System.err.println("There was an error processing the cause table.");
        }

        List<temporalObj> processingListConsequences = new ArrayList<>();
        dbConsequenceFacade.findAll().forEach((tmp) -> {
            processingListConsequences.add(new temporalObj(tmp.getConsequenceId().toString(), tmp.getConsequenceDescription()));
        });
        if (!processList(processingListConsequences, "consequence")) {
            System.err.println("There was an error processing the consequence table.");
        }
        
        List<temporalObj> processingListControls = new ArrayList<>();
        dbControlFacade.findAll().forEach((tmp) -> {
            processingListControls.add(new temporalObj(tmp.getControlId().toString(), tmp.getControlDescription()));
        });
        if (!processList(processingListControls, "control")) {
            System.err.println("There was an error processing the control table.");
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The database has been reindexed using the current common words table."));
    }
    
    public void indexDescription(String id, String description, String objectType) {
        System.out.println(id + description + objectType);
        dbindexedWordFacade.removeObject(id, objectType);
        List<temporalObj> listObjects = new ArrayList<>();
        listObjects.add(new temporalObj(id, description));
        if (!processList(listObjects, objectType)) {
            System.err.println("There was an error processing the " + objectType + " table.");
        }
        System.out.println(listObjects.size());
    }

    private boolean processList(List<temporalObj> processingList, String entityName) {
        try {
            List<DbindexedWord> listIndexedWords = new ArrayList<>();
            List<String> listOfCommonWords = dbcommonWordFacade.findAll().stream().map(h -> h.getCommonWord()).collect(Collectors.toList());
            processingList.forEach((tmp) -> {
                String[] words = tmp.description.replaceAll("/", " ").replaceAll("[^A-z\\s\\d\\-][\\\\\\^]?", "").toLowerCase().split("\\s+");
                List<String> listOfIndexedWords = new ArrayList<>();
                int line = 1;
                for (String word : words) {
                    if (!listOfCommonWords.contains(word) && word.length() > 2 && !listOfIndexedWords.contains(word) && !isNumeric(word)) {
                        listOfIndexedWords.add(word);
                        listIndexedWords.add(new DbindexedWord(new DbindexedWordPK(tmp.id, line, entityName), word));
                        line++;
                    }
                }
            });
            dbindexedWordFacade.createBatch(listIndexedWords);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    class temporalObj {

        public String id;
        public String description;

        public temporalObj(String id, String description) {
            this.id = id;
            this.description = description;
        }
    }
    
    public void addCommonWord(String commonWord, List<DbcommonWord> commonWords) {
        if (!commonWords.stream().anyMatch(i -> i.getCommonWord().equalsIgnoreCase(commonWord))) {
            DbcommonWord newWord = new DbcommonWord();
            newWord.setCommonWord(commonWord.toLowerCase());
            dbcommonWordFacade.create(newWord);
            dbindexedWordFacade.removeWord(commonWord.toLowerCase());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The word has been successfully added."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The word is already in the common words table."));
        }
    }
    
    public void removeCommonWord(DbcommonWord commonWord) {
        try {
            dbcommonWordFacade.remove(commonWord);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The word has been successfully deleted. Remember to clear and reindex the database for this to take effect."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The word could not be deleted."));
            throw e;
        }
    }
    
    public List<String> getListCommonWords() {
        return dbcommonWordFacade.findAll().stream().map(i -> i.getCommonWord()).collect(Collectors.toList());
    }

}
