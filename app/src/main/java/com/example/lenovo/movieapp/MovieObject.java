package com.example.lenovo.movieapp;

public class MovieObject {
    private String ORIGINAL_TITLE ;
    private String RELEASE_DATE ;
    private String POSTER_PATH ;
    private String OVER_VIEW;
    private  String VOT_AVERAGE;
    private String ID;
    public MovieObject(){}

    public MovieObject(String original_title, String release_date, String back_drop_path, String over_view ,String votAverage,String ID) {
        this.ORIGINAL_TITLE = original_title;
        this.RELEASE_DATE   = release_date;
        this.POSTER_PATH    = back_drop_path;
        this.OVER_VIEW      = over_view;
        this.VOT_AVERAGE     = votAverage;
        this.ID=ID;
    }

    public String getPOSTER_PATH(){
        return POSTER_PATH;
    }
    public String getORIGINAL_TITLE(){
        return ORIGINAL_TITLE;
    }
    public String getRELEASE_DATE(){
        return RELEASE_DATE;
    }
    public String getOVER_VIEW(){
        return OVER_VIEW;
    }
    public String getVOT_AVERAGE(){return VOT_AVERAGE;}
    public void setPOSTER_PATH(String p){
        this.POSTER_PATH = p;
    }
    public void setRELEASE_DATE(String r){
        this.RELEASE_DATE = r;
    }
    public void setORIGINAL_TITLE(String o){
        this.ORIGINAL_TITLE = o;
    }
    public void setOVER_VIEW(String v){
        this.OVER_VIEW=v;
    }
    public void setVOT_AVERAGE(String vote){VOT_AVERAGE=vote;}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
