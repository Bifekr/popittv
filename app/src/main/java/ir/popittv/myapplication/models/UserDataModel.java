package ir.popittv.myapplication.models;



public class UserDataModel {

    private String success;

    private int id_user;
    private String name;
    private String phone;
    private String code;
    private String  transactionId;
    private String firstDate;
    private String amount;
    private Long expireDate;


    public UserDataModel() {
    }

    public UserDataModel(String success, int id_user, String name, String phone, String code, String transactionId, String firstDate, String amount,Long expireDate) {
        this.success = success;
        this.id_user = id_user;
        this.name = name;
        this.phone = phone;
        this.code = code;
        this.transactionId = transactionId;
        this.firstDate = firstDate;
        this.amount = amount;
        this.expireDate = expireDate;
    }

    public String getSuccess() {
        return success;
    }

    public int getId_user() {
        return id_user;
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

    public String getTransactionId() {
        return transactionId;
    }

    public String getFirstDate() {
        return firstDate;
    }

    public String getAmount() {
        return amount;
    }

    public Long getExpireDate() {
        return expireDate;
    }
}
