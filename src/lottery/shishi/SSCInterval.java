package lottery.shishi;

import java.util.ArrayList;
import java.util.LinkedList;

public class SSCInterval {
    private String sscNumsType;
    private int openCount;
    private LinkedList<Integer> intervalList;
    private int currentInterval;

    public SSCInterval(String sscNumsType, int openCount, LinkedList<Integer> intervalList, int currentInterval) {
        this.sscNumsType = sscNumsType;
        this.openCount = openCount;
        this.intervalList = intervalList;
        this.currentInterval = currentInterval;
    }


    public SSCInterval() {
    }

    public int getCurrentInterval() {
        return currentInterval;
    }

    public void setCurrentInterval(int currentInterval) {
        this.currentInterval = currentInterval;
    }

    public String getSscNumsType() {
        return sscNumsType;
    }

    public void setSscNumsType(String sscNumsType) {
        this.sscNumsType = sscNumsType;
    }

    public int getOpenCount() {
        return openCount;
    }

    public void setOpenCount(int openCount) {
        this.openCount = openCount;
    }

    public LinkedList<Integer> getIntervalList() {
        return intervalList;
    }

    public void setIntervalList(LinkedList<Integer> intervalList) {
        this.intervalList = intervalList;
    }

}
