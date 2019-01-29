/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customObjects;

import entities.DbimportLine;
import entities.DbimportLineError;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lxra
 */

public class importObject {
    private DbimportLine lineObject;
    private List<DbimportLineError> listErrorObjects;
    private List<DbimportLineError> listWarningObjects;
    private String errorMessage = "";
    private String warningMessage = "";

    public boolean DAError = false;
    public boolean HDError = false;
    public boolean HMError = false;
    public boolean HWError = false;
    public boolean HCError = false;
    public boolean HLError = false;
    public boolean HAError = false;
    public boolean HTError = false;
    public boolean HSError = false;
    public boolean HOError = false;
    public boolean HFError = false;
    public boolean RCError = false;
    public boolean CFError = false;
    public boolean CSError = false;
    public boolean TFError = false;
    public boolean TSError = false;
    public boolean RDError = false;
    public boolean COError = false;
    public boolean CHError = false;
    public boolean CTError = false;
    public boolean CRError = false;
    public boolean CJError = false;
    public boolean CUError = false;
    public boolean SCError = false;
    public boolean HDWarning = false;
    public boolean RDWarning = false;

    public DbimportLine getLineObject() {
        return lineObject;
    }

    public void setLineObject(DbimportLine lineObject) {
        this.lineObject = lineObject;
    }

    public List<DbimportLineError> getListErrorObjects() {
        return listErrorObjects;
    }

    public void setListErrorObjects(List<DbimportLineError> listErrorObjects) {
        this.listErrorObjects = listErrorObjects;
    }

    public List<DbimportLineError> getListWarningObjects() {
        return listWarningObjects;
    }

    public void setListWarningObjects(List<DbimportLineError> listWarningObjects) {
        this.listWarningObjects = listWarningObjects;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public boolean isDAError() {
        return DAError;
    }

    public void setDAError(boolean DAError) {
        this.DAError = DAError;
    }

    public boolean isHDError() {
        return HDError;
    }

    public void setHDError(boolean HDError) {
        this.HDError = HDError;
    }

    public boolean isHMError() {
        return HMError;
    }

    public void setHMError(boolean HMError) {
        this.HMError = HMError;
    }

    public boolean isHWError() {
        return HWError;
    }

    public void setHWError(boolean HWError) {
        this.HWError = HWError;
    }

    public boolean isHCError() {
        return HCError;
    }

    public void setHCError(boolean HCError) {
        this.HCError = HCError;
    }

    public boolean isHLError() {
        return HLError;
    }

    public void setHLError(boolean HLError) {
        this.HLError = HLError;
    }

    public boolean isHAError() {
        return HAError;
    }

    public void setHAError(boolean HAError) {
        this.HAError = HAError;
    }

    public boolean isHTError() {
        return HTError;
    }

    public void setHTError(boolean HTError) {
        this.HTError = HTError;
    }

    public boolean isHSError() {
        return HSError;
    }

    public void setHSError(boolean HSError) {
        this.HSError = HSError;
    }

    public boolean isHOError() {
        return HOError;
    }

    public void setHOError(boolean HOError) {
        this.HOError = HOError;
    }

    public boolean isHFError() {
        return HFError;
    }

    public void setHFError(boolean HFError) {
        this.HFError = HFError;
    }

    public boolean isRCError() {
        return RCError;
    }

    public void setRCError(boolean RCError) {
        this.RCError = RCError;
    }

    public boolean isCFError() {
        return CFError;
    }

    public void setCFError(boolean CFError) {
        this.CFError = CFError;
    }

    public boolean isCSError() {
        return CSError;
    }

    public void setCSError(boolean CSError) {
        this.CSError = CSError;
    }

    public boolean isTFError() {
        return TFError;
    }

    public void setTFError(boolean TFError) {
        this.TFError = TFError;
    }

    public boolean isTSError() {
        return TSError;
    }

    public void setTSError(boolean TSError) {
        this.TSError = TSError;
    }

    public boolean isRDError() {
        return RDError;
    }

    public void setRDError(boolean RDError) {
        this.RDError = RDError;
    }

    public boolean isCOError() {
        return COError;
    }

    public void setCOError(boolean COError) {
        this.COError = COError;
    }

    public boolean isCHError() {
        return CHError;
    }

    public void setCHError(boolean CHError) {
        this.CHError = CHError;
    }

    public boolean isCTError() {
        return CTError;
    }

    public void setCTError(boolean CTError) {
        this.CTError = CTError;
    }

    public boolean isCRError() {
        return CRError;
    }

    public void setCRError(boolean CRError) {
        this.CRError = CRError;
    }

    public boolean isCJError() {
        return CJError;
    }

    public void setCJError(boolean CJError) {
        this.CJError = CJError;
    }

    public boolean isCUError() {
        return CUError;
    }

    public void setCUError(boolean CUError) {
        this.CUError = CUError;
    }

    public boolean isSCError() {
        return SCError;
    }

    public void setSCError(boolean SCError) {
        this.SCError = SCError;
    }

    public boolean isHDWarning() {
        return HDWarning;
    }

    public void setHDWarning(boolean HDWarning) {
        this.HDWarning = HDWarning;
    }

    public boolean isRDWarning() {
        return RDWarning;
    }

    public void setRDWarning(boolean RDWarning) {
        this.RDWarning = RDWarning;
    }

    public importObject() {
    }

    public importObject(DbimportLine lineObject, List<DbimportLineError> listRawErrors) {
        this.lineObject = lineObject;

        listErrorObjects = new ArrayList<>();
        listWarningObjects = new ArrayList<>();
        
        for (DbimportLineError errorObject : listRawErrors) {
            if (errorObject.getProcessErrorCode().getErrorAction().equals("E")) {
                listErrorObjects.add(errorObject);
                errorMessage += errorObject.getProcessErrorCode().getErrorName().concat(" error in the ").concat(errorObject.getProcessErrorLocation().toLowerCase()).concat(" field. ");
                switch(errorObject.getProcessErrorLocation()) {
                    case "hazardDate":
                        this.DAError = true;
                        break;
                    case "hazardDescription":
                        this.HDError = true;
                        break;
                    case "hazardComment":
                        this.HMError = true;
                        break;
                    case "hazardContext":
                        this.HCError = true;
                        break;
                    case "hazardWorkshop":
                        this.HWError = true;
                        break;
                    case "hazardLocation":
                        this.HLError = true;
                        break;
                    case "hazardActivity":
                        this.HAError = true;
                        break;
                    case "hazardType":
                        this.HTError = true;
                        break;
                    case "hazardStatus":
                        this.HSError = true;
                        break;
                    case "hazardOwner":
                        this.HOError = true;
                        break;
                    case "hazardHFReview":
                        this.HFError = true;
                        break;
                    case "hazardRiskClass":
                        this.RCError = true;
                        break;
                    case "hazardCurrentFreq":
                        this.CFError = true;
                        break;
                    case "hazardCurrentSev":
                        this.CSError = true;
                        break;
                    case "hazardTargetFreq":
                        this.TFError = true;
                        break;
                    case "hazardTargetSev":
                        this.TSError = true;
                        break;
                    case "relationDescription":
                        this.RDError = true;
                        break;
                    case "controlOwner":
                        this.COError = true;
                        break;
                    case "controlHierarchy":
                        this.CHError = true;
                        break;
                    case "controlRecommend":
                        this.CRError = true;
                        break;
                    case "controlJustify":
                        this.CJError = true;
                        break;
                    case "controlType":
                        this.CTError = true;
                        break;
                    case "controlStatus":
                        this.CUError = true;
                        break;
                    case "hazardSbs":
                        this.SCError = true;
                        break;
                    default:
                        break;
                }
            } else if (errorObject.getProcessErrorCode().getErrorAction().equals("W")) {
                listWarningObjects.add(errorObject);
                switch(errorObject.getProcessErrorLocation()) {
                    case "hazard":
                        this.HDWarning = true;
                        break;
                    case "Cause":
                    case "Consequence":
                    case "Control":
                        this.RDWarning = true;
                        break;
                    default:
                        break;
                }
            }
        }
        
        for (DbimportLineError errorObject : listErrorObjects) {
            errorMessage += errorObject.getProcessErrorCode().getErrorName().concat(" error in the ").concat(errorObject.getProcessErrorLocation().toLowerCase()).concat(" field. ");
        }

        for (DbimportLineError warningObject : listWarningObjects) {
            warningMessage += warningObject.getProcessErrorCode().getErrorName().concat(" warning in the ").concat(warningObject.getProcessErrorLocation().toLowerCase()).concat(" field. ");
        }
    }
}