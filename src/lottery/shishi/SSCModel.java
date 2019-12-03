package lottery.shishi;

public class SSCModel {
    private String sequence;
    private String dateLine;
    private String openNums;


    public SSCModel(String sequence, String dateLine, String openNums) {
        this.sequence = sequence;
        this.dateLine = dateLine;
        this.openNums = openNums;
    }

    public SSCModel(String dateLine, String openNums) {
        this.dateLine = dateLine;
        this.openNums = openNums;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getDateLine() {
        return dateLine;
    }

    public void setDateLine(String dateLine) {
        this.dateLine = dateLine;
    }

    public String getOpenNums() {
        return openNums;
    }

    public void setOpenNums(String openNums) {
        this.openNums = openNums;
    }
}
