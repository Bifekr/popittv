package ir.popittv.myapplication.repository;

public class MainRepository {

    private static MainRepository mainRepository;
    public static MainRepository getInstance(){
        if (mainRepository==null){
            mainRepository =new MainRepository();
        }
        return getInstance();
    }

private MainRepository(){}
}
