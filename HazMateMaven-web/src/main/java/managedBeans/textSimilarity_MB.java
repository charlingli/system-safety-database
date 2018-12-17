/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.similarityObject;
import ejb.DbcommonWordFacadeLocal;
import ejb.DbindexedWordFacadeLocal;
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
@Named(value = "textSimilarity_MB")
@RequestScoped
public class textSimilarity_MB {

    @EJB
    private DbindexedWordFacadeLocal dbindexedWordFacade;
    @EJB
    private DbcommonWordFacadeLocal dbcommonWordFacade;

    public textSimilarity_MB() {
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
        System.out.println(resultantList.size()); // for delete

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

}
