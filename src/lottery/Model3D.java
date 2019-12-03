package lottery;



public class Model3D  {
    private String dateLine;
    private String date;
    private String openNums;


    public Model3D(String dateLine, String date, String openNums) {
        this.dateLine = dateLine;
        this.date = date;
        this.openNums = openNums;
    }

    public String getDateLine() {
        return dateLine;
    }

    public void setDateLine(String dateLine) {
        this.dateLine = dateLine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpenNums() {
        return openNums;
    }

    public void setOpenNums(String openNums) {
        this.openNums = openNums;
    }


    
    
    
    public String toString() {
		return "Model3D [dateLine=" + dateLine + ", date=" + date
				+ ", openNums=" + openNums + "]";
	}
}
