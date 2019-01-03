/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbcommonWordFacadeLocal;
import ejb.DbindexedWordFacadeLocal;
import entities.DbcommonWord;
import entities.DbindexedWord;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author lxra
 */
@Named(value = "wordView_MB")
@ViewScoped
public class wordView_MB implements Serializable {

    @EJB
    private DbcommonWordFacadeLocal dbcommonWordFacade;

    @EJB
    private DbindexedWordFacadeLocal dbindexedWordFacade;

    private List<DbcommonWord> listCommonWords;
    private List<DbcommonWord> showCommonWords;
    private String commonWord;
    private boolean addFlag;
    
    private List<DbindexedWord> listIndexedWords;
    private List<DbindexedWord> showIndexedWords;

    public List<DbcommonWord> getListCommonWords() {
        return listCommonWords;
    }

    public void setListCommonWords(List<DbcommonWord> listCommonWords) {
        this.listCommonWords = listCommonWords;
    }

    public List<DbcommonWord> getShowCommonWords() {
        return showCommonWords;
    }

    public void setShowCommonWords(List<DbcommonWord> showCommonWords) {
        this.showCommonWords = showCommonWords;
    }

    public List<DbindexedWord> getListIndexedWords() {
        return listIndexedWords;
    }

    public void setListIndexedWords(List<DbindexedWord> listIndexedWords) {
        this.listIndexedWords = listIndexedWords;
    }

    public List<DbindexedWord> getShowIndexedWords() {
        return showIndexedWords;
    }

    public void setShowIndexedWords(List<DbindexedWord> showIndexedWords) {
        this.showIndexedWords = showIndexedWords;
    }

    public String getCommonWord() {
        return commonWord;
    }

    public void setCommonWord(String commonWord) {
        this.commonWord = commonWord;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }

    public wordView_MB() {
    }
    
    @PostConstruct
    public void init() {
        setListCommonWords(dbcommonWordFacade.findAll());
        setListIndexedWords(dbindexedWordFacade.findAll());
        cancel();
    }
    
    public void showAdd() {
        addFlag = true;
    }
    
    public void hideAdd() {
        init();
    }
    
    public void cancel() {
        setCommonWord("");
        addFlag = false;
    }
}
