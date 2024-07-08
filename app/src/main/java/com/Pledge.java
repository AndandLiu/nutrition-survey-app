package com.jla388.sfu.greenfoodchallenge;

public class Pledge {
    private String key;
    private String name;
    private String municipality;
    private String avgSavings;
    private String pledgedAmount;
    private String userLogoLocation;

    public Pledge(){
        key = "none";
        name = "none";
        municipality = "none";
        avgSavings = "none";
        pledgedAmount = "none";
        userLogoLocation = "none";
    }

    public Pledge(String userKey , String userName , String userMunicipality , String userAvgSavings , String userPledgedAmount, String userLogo){
        this.key = userKey;
        this.name = userName;
        this.municipality = userMunicipality;
        this.avgSavings = userAvgSavings;
        this.pledgedAmount = userPledgedAmount;
        this.userLogoLocation = userLogo;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getAvgSavings() {
        return avgSavings;
    }

    public void setAvgSavings(String avgSavings) {
        this.avgSavings = avgSavings;
    }

    public String getPledgedAmount() {
        return pledgedAmount;
    }

    public void setPledgedAmount(String pledgedAmount) {
        this.pledgedAmount = pledgedAmount;
    }

    public String getUserLogoLocation() {
        return userLogoLocation;
    }

    public void setUserLogoLocation(String userLogoLocation) {
        this.userLogoLocation = userLogoLocation;
    }
}
