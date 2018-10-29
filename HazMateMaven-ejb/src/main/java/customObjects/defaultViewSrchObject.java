/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customObjects;

import entities.DbHazard;

/**
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
public class defaultViewSrchObject {

    private DbHazard hazardObj;
    private Long sumOfRateTimesWeighting;
    private Long sumOfWeight;
    private Long rateCount;
    private String missingInfo;

    public defaultViewSrchObject() {
    }

    public defaultViewSrchObject(DbHazard hazardObj, Long sumOfRateTimesWeighting, Long sumOfWeight, Long rateCount, String missingInfo) {
        this.hazardObj = hazardObj;
        this.sumOfRateTimesWeighting = sumOfRateTimesWeighting;
        this.sumOfWeight = sumOfWeight;
        this.rateCount = rateCount;
        this.missingInfo = missingInfo;
    }

    public DbHazard getHazardObj() {
        return hazardObj;
    }

    public void setHazardObj(DbHazard hazardObj) {
        this.hazardObj = hazardObj;
    }

    public Long getSumOfRateTimesWeighting() {
        return sumOfRateTimesWeighting;
    }

    public void setSumOfRateTimesWeighting(Long sumOfRateTimesWeighting) {
        this.sumOfRateTimesWeighting = sumOfRateTimesWeighting;
    }

    public Long getSumOfWeight() {
        return sumOfWeight;
    }

    public void setSumOfWeight(Long sumOfWeight) {
        this.sumOfWeight = sumOfWeight;
    }

    public Long getRateCount() {
        return rateCount;
    }

    public void setRateCount(Long rateCount) {
        this.rateCount = rateCount;
    }

    public String getMissingInfo() {
        return missingInfo;
    }

    public void setMissingInfo(String missingInfo) {
        this.missingInfo = missingInfo;
    }

    @Override
    public String toString() {
        return "defaultViewSrchObject{" + "hazardObj=" + hazardObj + ", sumOfRateTimesWeighting=" + sumOfRateTimesWeighting + ", sumOfWeight=" + sumOfWeight + ", rateCount=" + rateCount + ", missingInfo=" + missingInfo + '}';
    }
    
}
