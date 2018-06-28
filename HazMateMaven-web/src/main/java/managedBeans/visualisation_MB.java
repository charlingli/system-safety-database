/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;
import ejb.DbHazardFacadeLocal;
import entities.DbControlHazard;
import entities.DbHazard;
import entities.DbHazardCause;
import entities.DbHazardConsequence;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
 
@ManagedBean(name = "visualisation_MB")
@RequestScoped
public class visualisation_MB {
     
    private DefaultDiagramModel model;
    
    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
    private List<DbHazard> listDbHazard;
    private DbHazard selectedHazard;
    private List<DbHazardCause> selectedCauses;
    private List<DbHazardConsequence> selectedConsequences;
    private List<DbControlHazard> selectedPrevents;
    
    // Note: This is incorrect. Every hazard-cause and hazard-consequence pair 
    //  should have their own controls instead of each hazard-control pair.
    //  Currently, the database is set up such that controls only belong to a
    //  hazard, not a hazard-cause or hazard-consequence pair.
    private List<String> tempPrevents = new ArrayList<String>();
 
    @PostConstruct
    public void init() {
        listDbHazard = dbHazardFacade.findAllHazards();
        selectedHazard = listDbHazard.get(0);
        selectedCauses = dbHazardFacade.getHazardCause(selectedHazard.getHazardId());
        selectedConsequences = dbHazardFacade.getHazardConsequence(selectedHazard.getHazardId());
        selectedPrevents = dbHazardFacade.getControlHazard(selectedHazard.getHazardId());
        
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#0095a8',lineWidth:2}");
        model.setDefaultConnector(connector);
        
        Element hazard = new Element(selectedHazard.getHazardDescription(), "40%", "45%");
        hazard.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
        hazard.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
        hazard.setStyleClass("ui-diagram-hazard");
        model.addElement(hazard);
        
        for (DbControlHazard selectedPrevent : selectedPrevents) {
            System.out.println(selectedPrevent.getDbControl().getControlDescription());
            tempPrevents.add(selectedPrevent.getDbControl().getControlDescription());
        }
        
        for (int i = 0; i < selectedCauses.size(); i ++) {
            Element cause = new Element(selectedCauses.get(i).getDbCause().getCauseDescription(), "5%", ((i + 1.0) / (selectedCauses.size() + 1)) * 90 + "%");
            cause.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
            cause.setStyleClass("ui-diagram-cause");
            model.addElement(cause);
            if (i == 0) {
                model.connect(createConnection(cause.getEndPoints().get(0), hazard.getEndPoints().get(0), tempPrevents, true));
            } else {
                model.connect(createConnection(cause.getEndPoints().get(0), hazard.getEndPoints().get(0), null, true));
            }
        }
        
        for (int i = 0; i < selectedConsequences.size(); i ++) {
            Element consequence = new Element(selectedConsequences.get(i).getDbConsequence().getConsequenceDescription(), "85%", ((i + 1.0) / (selectedConsequences.size() + 1)) * 90 + "%");
            consequence.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
            consequence.setStyleClass("ui-diagram-consequence");
            model.addElement(consequence);
            model.connect(createConnection(hazard.getEndPoints().get(1), consequence.getEndPoints().get(0), tempPrevents, false));
        }
    }
     
    public DiagramModel getModel() {
        return model;
    }
     
    private Connection createConnection(EndPoint from, EndPoint to, List<String> labels, boolean prevent) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(8, 8, 1, 1));
        String classType;
        if (prevent) {
            classType = "ui-diagram-prevent";
        } else {
            classType = "ui-diagram-mitigate";
        }
        
        
        if(labels != null) {
            float nlabel = labels.size();
            for (int i = 0; i < nlabel; i ++) {
                conn.getOverlays().add(new LabelOverlay(labels.get(i), classType, (i + 1) / (nlabel + 1)));
            }
        }
        return conn;
    }
}