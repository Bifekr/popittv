package ir.popittv.myapplication.models;

public class CafeModel {

private int id;
private String package_name;
private String title_en;
private String kind;
private String type;
private String poster;

    public CafeModel(int id, String package_name, String title_en, String kind, String type, String poster) {
        this.id = id;
        this.package_name = package_name;
        this.title_en = title_en;
        this.kind = kind;
        this.type = type;
        this.poster = poster;
    }

    public CafeModel() {
    }

    public int getId() {
        return id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getTitle_en() {
        return title_en;
    }

    public String getKind() {
        return kind;
    }

    public String getType() {
        return type;
    }

    public String getPoster() {
        return poster;
    }
}
