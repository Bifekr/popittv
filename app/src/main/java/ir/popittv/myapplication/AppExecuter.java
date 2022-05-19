package ir.popittv.myapplication;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecuter {

    private static AppExecuter appExecuter;
    public static AppExecuter getAppExecuter(){
        if (appExecuter==null){
            appExecuter = new AppExecuter();
        }
        return appExecuter;
    }


    private final ScheduledExecutorService mNetworkIo  = Executors.newScheduledThreadPool(3);
    public ScheduledExecutorService networkIo(){
        return mNetworkIo;
    }

}
