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
import javax.faces.bean.ViewScoped;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
 
@ManagedBean(name = "visualisation_MB")
@ViewScoped
public class visualisation_MB {
     
    private DefaultDiagramModel model;
    
    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
    private List<DbHazard> listDbHazard;
    private DbHazard selectedHazard;
    private List<DbHazardCause> selectedCauses;
    private List<DbHazardConsequence> selectedConsequences;
    private List<DbControlHazard> selectedControls;
    
    private String selectedId;

    public String getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
    }
 
    @PostConstruct
    public void init() {
        
    }
     
    public DiagramModel getModel() {
        return model;
    }
    
    private Connection createConnection(EndPoint from, EndPoint to, List<String> labels, List<String> recommends, boolean prevent) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(8, 8, 1, 1));
        
        if(labels != null) {
            String classType;
            if (prevent) {
                classType = "ui-diagram-prevent";
                int nlabel = labels.size();
                for (int i = (nlabel / 2); i < nlabel; i ++) {
                    conn.getOverlays().add(new LabelOverlay("<div class='" + classType + "'></div>"
                            + "<div style='transform: translateX(-" + 100.0 / 3 + "%)' class='ui-diagram-text'>"
                            + "<span class='ui-diagram-recommend-" + recommends.get(i).substring(0, 3) + "' + title='" + recommends.get(i) + "'>" + recommends.get(i) + "</span>"
                            + labels.get(i)
                            + "<span class='ui-diagram-tooltip'>" + labels.get(i) + "</span></div>", null, (i + 1.0) / (nlabel  + 1)));
                }
            } else {
                classType = "ui-diagram-mitigate";
                int nlabel = labels.size();
                for (int i = 0; i < nlabel; i ++) {
                    conn.getOverlays().add(new LabelOverlay("<div class='" + classType + "'></div>"
                            + "<div style='transform: translateX(-" + 100.0 / 3 + "%)' class='ui-diagram-text'>"
                            + "<span class='ui-diagram-recommend-" + recommends.get(i).substring(0, 3) + "' + title='" + recommends.get(i) + "'>" + recommends.get(i) + "</span>"
                            + labels.get(i)
                            + "<span class='ui-diagram-tooltip'>" + labels.get(i) + "</span></div>", null, (i + 1.0) / (nlabel  + 1)));
                }
            }
        }
        return conn;
    }
    
    public void constructModelObject(String hazardId) {
        listDbHazard = dbHazardFacade.findByName("hazardId", hazardId);
        selectedHazard = listDbHazard.get(0);
        selectedCauses = dbHazardFacade.getHazardCause(selectedHazard.getHazardId());
        selectedConsequences = dbHazardFacade.getHazardConsequence(selectedHazard.getHazardId());
        selectedControls = dbHazardFacade.getControlHazard(selectedHazard.getHazardId());
        
        List<String> pStrings = new ArrayList<String>();
        List<String> pRecommend = new ArrayList<String>();
        List<String> mStrings = new ArrayList<String>();
        List<String> mRecommend = new ArrayList<String>();
        
        model = new DefaultDiagramModel();
        
        for (Element element : model.getElements()) {
            model.removeElement(element);
        }
        
        model.setMaxConnections(-1);
        
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#0095a8',lineWidth:2}");
        model.setDefaultConnector(connector);
        
        int nPControls = 0;
        int nMControls = 0;
        
        for (DbControlHazard selectedControl : selectedControls) {
            
            if (selectedControl.getControlType().equals("P")) {
                nPControls += 1;
                pStrings.add(selectedControl.getDbControl().getControlDescription());
                pStrings.add(0, "");
                pRecommend.add(selectedControl.getControlRecommendId().getControlRecommendName());
                pRecommend.add(0, "");
            } else {
                nMControls += 1;
                mStrings.add(0, selectedControl.getDbControl().getControlDescription());
//                mStrings.add("");
                mRecommend.add(0, selectedControl.getControlRecommendId().getControlRecommendName());
//                mRecommend.add("");
            }
        }
        
        Element hazard = new Element(selectedHazard.getHazardDescription(), 4 + (nPControls + 1.0) / (nPControls + nMControls + 2) * 72 + "vw", "36vh");
        hazard.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
        hazard.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
        hazard.setStyleClass("ui-diagram-hazard");
        model.addElement(hazard);
        
        for (int i = 0; i < selectedCauses.size(); i ++) {
            Element cause = new Element(selectedCauses.get(i).getDbCause().getCauseDescription(), "4vw", ((i + 1.0) / (selectedCauses.size() + 1)) * 64 + "vh");
            cause.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
            cause.setStyleClass("ui-diagram-cause");
            model.addElement(cause);
            if (i == 0) {
                model.connect(createConnection(cause.getEndPoints().get(0), hazard.getEndPoints().get(0), pStrings, pRecommend, true));
            } else {
                model.connect(createConnection(cause.getEndPoints().get(0), hazard.getEndPoints().get(0), null, null, true));
            }
        }
        
        for (int i = 0; i < selectedConsequences.size(); i ++) {
            Element consequence = new Element(selectedConsequences.get(i).getDbConsequence().getConsequenceDescription(), "72vw", ((i + 1.0) / (selectedConsequences.size() + 1)) * 72 + "vh");
            consequence.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
            consequence.setStyleClass("ui-diagram-consequence");
            model.addElement(consequence);
            if (i == 0) {
                model.connect(createConnection(hazard.getEndPoints().get(1), consequence.getEndPoints().get(0), mStrings, mRecommend, false));
            } else {
                model.connect(createConnection(hazard.getEndPoints().get(0), consequence.getEndPoints().get(0), null, null, true));
            }
        }
        System.out.println("Model created");
    }
}