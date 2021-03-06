package com.fh.entity.system;

/**
 * 娃娃机实体类
 */
public class Doll {

    private String DOLL_ID;
    private String DOLL_NAME;
    private String DOLL_STATE;
    private String ROOM_ID;
    private String DOLL_SN;
    private Integer DOLL_GOLD;
    private String DOLL_URL;
    private String CAMERA_NAME_01;
    private String CAMERA_NAME_02;
    private String DOLL_CONVERSIONGOLD;

    public Doll() {

    }

    public Doll(String DOLL_ID, String DOLL_SN) {
        this.DOLL_ID = DOLL_ID;
        this.DOLL_SN = DOLL_SN;
    }

    public String getDOLL_CONVERSIONGOLD() {
        return DOLL_CONVERSIONGOLD;
    }

    public void setDOLL_CONVERSIONGOLD(String DOLL_CONVERSIONGOLD) {
        this.DOLL_CONVERSIONGOLD = DOLL_CONVERSIONGOLD;
    }

    public String getCAMERA_NAME_01() {
        return CAMERA_NAME_01;
    }

    public void setCAMERA_NAME_01(String CAMERA_NAME_01) {
        this.CAMERA_NAME_01 = CAMERA_NAME_01;
    }

    public String getCAMERA_NAME_02() {
        return CAMERA_NAME_02;
    }

    public void setCAMERA_NAME_02(String CAMERA_NAME_02) {
        this.CAMERA_NAME_02 = CAMERA_NAME_02;
    }

    public String getDOLL_ID() {
        return DOLL_ID;
    }

    public void setDOLL_ID(String DOLL_ID) {
        this.DOLL_ID = DOLL_ID;
    }

    public String getDOLL_NAME() {
        return DOLL_NAME;
    }

    public void setDOLL_NAME(String DOLL_NAME) {
        this.DOLL_NAME = DOLL_NAME;
    }

    public String getDOLL_STATE() {
        return DOLL_STATE;
    }

    public void setDOLL_STATE(String DOLL_STATE) {
        this.DOLL_STATE = DOLL_STATE;
    }

    public String getROOM_ID() {
        return ROOM_ID;
    }

    public void setROOM_ID(String ROOM_ID) {
        this.ROOM_ID = ROOM_ID;
    }

    public String getDOLL_SN() {
        return DOLL_SN;
    }

    public void setDOLL_SN(String DOLL_SN) {
        this.DOLL_SN = DOLL_SN;
    }

    public Integer getDOLL_GOLD() {
        return DOLL_GOLD;
    }

    public void setDOLL_GOLD(Integer DOLL_GOLD) {
        this.DOLL_GOLD = DOLL_GOLD;
    }

    public String getDOLL_URL() {
        return DOLL_URL;
    }

    public void setDOLL_URL(String DOLL_URL) {
        this.DOLL_URL = DOLL_URL;
    }
}
