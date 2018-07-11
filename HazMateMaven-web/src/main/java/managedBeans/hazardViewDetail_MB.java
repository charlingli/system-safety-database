/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.DbHazard;
import entities.DbUser;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author lxra
 */
@Named(value = "hazardViewDetail_MB")
@ViewScoped
public class hazardViewDetail_MB implements Serializable {

    @ManagedProperty("#{hazard.list}")
    private List<DbHazard> hazardSearchedlist;
    private List<DbHazard> hazardSearchedlist1;
    private List<DbHazard> hazardSearchedlist2;

    private String something;

    public hazardViewDetail_MB() {
    }

    public List<DbHazard> getHazardSearchedlist() {
        return hazardSearchedlist;
    }

    public void setHazardSearchedlist(List<DbHazard> hazardSearchedlist) {
        this.hazardSearchedlist = hazardSearchedlist;
    }

    public String getSomething() {
        return something;
    }

    public void setSomething(String something) {
        this.something = something;
    }

    @PostConstruct
    public void init() {
        hazardSearchedlist = (List<DbHazard>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("hazardList");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("hazardList");
        something = "table";
    }

}
