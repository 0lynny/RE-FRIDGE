package ddwucom.mobile.finalproject.ma01_20191023;

import java.io.Serializable;

public class FoodDTO implements Serializable {

    private int _id;
    private String GROUP_NAME; //식품군
    private String DESC_KOR; //식품명
    private String SERVING_SIZE; //	총내용량
    private String NUTR_CONT1;	//열량(kcal)(1회제공량당)
    private String NUTR_CONT2;	//탄수화물(g)(1회제공량당)
    private String NUTR_CONT3;	//단백질(g)(1회제공량당)
    private String NUTR_CONT4;	//지방(g)(1회제공량당)
    private String NUTR_CONT5;	//당류(g)(1회제공량당)
    private String NUTR_CONT6;	//나트륨(mg)(1회제공량당)
    private String NUTR_CONT7;	//콜레스테롤(mg)(1회제공량당)
    private String NUTR_CONT8; //포화지방산(g)(1회제공량당)
    private String NUTR_CONT9;	//트랜스지방(g)(1회제공량당)

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getGROUP_NAME() {
        return GROUP_NAME;
    }

    public void setGROUP_NAME(String GROUP_NAME) {
        this.GROUP_NAME = GROUP_NAME;
    }

    public String getDESC_KOR() {
        return DESC_KOR;
    }

    public void setDESC_KOR(String DESC_KOR) {
        this.DESC_KOR = DESC_KOR;
    }

    public String getSERVING_SIZE() {
        return SERVING_SIZE;
    }

    public void setSERVING_SIZE(String SERVING_SIZE) {
        this.SERVING_SIZE = SERVING_SIZE;
    }

    public String getNUTR_CONT1() {
        return NUTR_CONT1;
    }

    public void setNUTR_CONT1(String NUTR_CONT1) {
        this.NUTR_CONT1 = NUTR_CONT1;
    }

    public String getNUTR_CONT2() {
        return NUTR_CONT2;
    }

    public void setNUTR_CONT2(String NUTR_CONT2) {
        this.NUTR_CONT2 = NUTR_CONT2;
    }

    public String getNUTR_CONT3() {
        return NUTR_CONT3;
    }

    public void setNUTR_CONT3(String NUTR_CONT3) {
        this.NUTR_CONT3 = NUTR_CONT3;
    }

    public String getNUTR_CONT4() {
        return NUTR_CONT4;
    }

    public void setNUTR_CONT4(String NUTR_CONT4) {
        this.NUTR_CONT4 = NUTR_CONT4;
    }

    public String getNUTR_CONT5() {
        return NUTR_CONT5;
    }

    public void setNUTR_CONT5(String NUTR_CONT5) {
        this.NUTR_CONT5 = NUTR_CONT5;
    }

    public String getNUTR_CONT6() {
        return NUTR_CONT6;
    }

    public void setNUTR_CONT6(String NUTR_CONT6) {
        this.NUTR_CONT6 = NUTR_CONT6;
    }

    public String getNUTR_CONT7() {
        return NUTR_CONT7;
    }

    public void setNUTR_CONT7(String NUTR_CONT7) {
        this.NUTR_CONT7 = NUTR_CONT7;
    }

    public String getNUTR_CONT8() {
        return NUTR_CONT8;
    }

    public void setNUTR_CONT8(String NUTR_CONT8) {
        this.NUTR_CONT8 = NUTR_CONT8;
    }

    public String getNUTR_CONT9() {
        return NUTR_CONT9;
    }

    public void setNUTR_CONT9(String NUTR_CONT9) {
        this.NUTR_CONT9 = NUTR_CONT9;
    }

}
