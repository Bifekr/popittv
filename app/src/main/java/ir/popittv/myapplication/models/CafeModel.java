package ir.popittv.myapplication.models;

public class CafeModel {

private int id;
private String package_name;
private String name;
private String kind;
private String type;
private String icon;

    public CafeModel(int id, String package_name, String name, String kind, String type, String icon) {
        this.id = id;
        this.package_name = package_name;
        this.name = name;
        this.kind = kind;
        this.type = type;
        this.icon = icon;
    }

    public CafeModel() {
    }

    public int getId() {
        return id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }

    public String getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }
}
