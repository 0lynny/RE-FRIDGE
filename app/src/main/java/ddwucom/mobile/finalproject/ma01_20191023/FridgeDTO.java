package ddwucom.mobile.finalproject.ma01_20191023;

import java.io.Serializable;

public class FridgeDTO implements Serializable {

    private long id;
    private String title; //음식명
    private String expireDate; //유통기한
    private String intakeDate; // 섭취가능기한
    private String memo; //메모

    private String image; // 이미지

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(String intakeDate) {
        this.intakeDate = intakeDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "FridgeDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", intakeDate='" + intakeDate + '\'' +
                ", memo='" + memo + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}