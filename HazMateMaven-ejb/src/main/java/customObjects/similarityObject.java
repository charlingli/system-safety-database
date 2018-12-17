package customObjects;

/**
 *
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
public class similarityObject {

    private String objectId;
    private int averageDistance;
    private String[] coincidentWords;

    public similarityObject() {
    }

    public similarityObject(String objectId, int averageDistance, String[] coincidentWords) {
        this.objectId = objectId;
        this.averageDistance = averageDistance;
        this.coincidentWords = coincidentWords;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getAverageDistance() {
        return averageDistance;
    }

    public void setAverageDistance(int averageDistance) {
        this.averageDistance = averageDistance;
    }

    public String[] getCoincidentWords() {
        return coincidentWords;
    }

    public void setCoincidentWords(String[] coincidentWords) {
        this.coincidentWords = coincidentWords;
    }

    @Override
    public String toString() {
        return "similarityObject{" + "objectId=" + objectId + ", averageDistance=" + averageDistance + ", coincidentWords=" + String.join(", ", coincidentWords) + '}';
    }
}
