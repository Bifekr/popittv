package ir.popittv.myapplication.models;

public class UserDataModel {

    private String success;
    private int user_id;
    private String name;
    private String phone;
    private String code;

    public UserDataModel() {
    }

    public UserDataModel(String success, int user_id, String name, String phone, String code) {
        this.success = success;
        this.user_id = user_id;
        this.name = name;
        this.phone = phone;
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getCode() {
        return code;
    }
}
