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
import entities.DbindexedWord;
import entities.DbindexedWordPK;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
@Named(value = "wordProcessing_MB")
@RequestScoped
public class wordProcessing_MB {

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
        // Processing the entered text against the recognised common words in the database
        String[] words = newDescription.replaceAll("/", " ").replaceAll("[^A-z\\s\\d\\-][\\\\\\^]?", "").toLowerCase().split("\\s+");
        List<String> listOfCommonWords = dbcommonWordFacade.findAll().stream().map(h -> h.getCommonWord()).collect(Collectors.toList());
        List<String> listOfValues = new ArrayList<>();
        for (String word : words) {
            if (!listOfCommonWords.contains(word) && word.length() > 2 && !listOfValues.contains(word)) {
                listOfValues.add(word);
            }
        }

        // Getting from the database the hazards that matches with the typed words
        List<Object[]> resultantList = dbindexedWordFacade.findSimilarities(objectType, listOfValues);

        // Calculating the distance between the stored data and the new data
        List<similarityObject> listPotentialDuplicates = new ArrayList<>();
        resultantList.forEach((tmp) -> {
            int percentageFromIndexed = (Integer.parseInt(tmp[1].toString()) * 100) / Integer.parseInt(tmp[2].toString());
            int percentageFromNew = (Integer.parseInt(tmp[1].toString()) * 100) / listOfValues.size();
            int averageDistance = (percentageFromIndexed + percentageFromNew) / 2;
            //System.out.println(tmp[0].toString() + " " + tmp[1].toString() + " " + tmp[2].toString() + " " + tmp[3].toString() + " " + averageDistance);
            //System.out.println("percentageFromIndexed -> ( " + tmp[1].toString() + " / " + tmp[2].toString() + " * 100 ) = " + percentageFromIndexed);
            //System.out.println("percentageFromNew -> ( " + tmp[1].toString() + " / " + listOfValues.size() + " * 100 ) = " + percentageFromNew);
            if (averageDistance > 75) {
                listPotentialDuplicates.add(new similarityObject(tmp[0].toString(), averageDistance, tmp[3].toString().split(",")));
            }
        });
        return listPotentialDuplicates;
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
            System.out.println(listIndexedWords.size());
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

}
