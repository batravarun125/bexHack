package com.example.ayush.resturantninjas.Main;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.ayush.resturantninjas.R;
import com.example.ayush.resturantninjas.RestrauntActivity.BurgerKing;
import com.example.ayush.resturantninjas.RestrauntActivity.Dominos;
import com.example.ayush.resturantninjas.RestrauntActivity.KhanaKhazana;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by divyam on 12/29/16.
 */
public class MyApp extends Application implements BeaconConsumer {

    private static MyApp instance = null;
    private BeaconManager beaconManager;
    private static final Identifier nameSpaceId = Identifier.parse("0x5dc33487f02e477d4058");

    public ArrayList<String> regionNameList;
    public ArrayList<Region> regionList;
    public static Integer present;
    public HashMap<String,Region> ssnRegionMap;
    public static int count =0;


    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
//                String myRegionName = region.getUniqueId();
//                Toast.makeText(MyApp.this, "ENter Region"+myRegionName, Toast.LENGTH_SHORT).show();
//
//
//                if(myRegionName=="Dominos"){
//                    present = 1;
//                    Intent intent = new Intent(getApplicationContext(),Dominos.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    Toast.makeText(MyApp.this, "ENter Region"+myRegionName, Toast.LENGTH_SHORT).show();
//
//
//                }
//
//                else if(myRegionName=="Mc Donalds"){
//                    present = 2;
//                    Intent intent = new Intent(getApplicationContext(),BurgerKing.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//
//                    Toast.makeText(MyApp.this, "ENter Region"+myRegionName, Toast.LENGTH_SHORT).show();
//
//                }
//
//                else if(myRegionName=="Khaana Khazaana"){
//                    present = 3;
//                    Log.d("mytag","Khaana khazaana");
//                    Intent intent = new Intent(getApplicationContext(),KhanaKhazana.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//
//                    Toast.makeText(MyApp.this, "ENter Region"+myRegionName, Toast.LENGTH_SHORT).show();
//
//                }
//

            }

            @Override
            public void didExitRegion(Region region) {

            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {
                String regionName = region.getUniqueId();

                    showNotification("Welcome to food Court");

                count =1;


                Toast.makeText(MyApp.this, regionName, Toast.LENGTH_SHORT).show();

                Log.d("mytag", String.valueOf(regionList.size()));

                if(i==INSIDE){
                    Log.i("TAG","Inside "+regionName);
                    regionNameList.add(regionName);
                    regionList.add(region);

//                    String myRegionName = regionName;
//                        if(myRegionName.equals("Dominos")){
//                            present = 1;
//                            Intent intent = new Intent(getApplicationContext(),Dominos.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//
//                        }
//                        else if(myRegionName.equals("Mc Donalds")){
//                            present = 2;
//                            Intent intent = new Intent(getApplicationContext(),BurgerKing.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//
//                        }
//                        else if(myRegionName.equals("Khaana Khazaana")){
//                            present = 3;
//                            Log.d("mytag","Khaana khazaana");
//                            Intent intent = new Intent(getApplicationContext(),KhanaKhazana.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//
//                        }

                }

                else if(i == OUTSIDE ){
                    //Log.i("TAG","Outside"+regionName);

                    if(regionList.contains(region)){
                        regionList.remove(region);
                        if(regionList.size()==0)
                        {
                            present=0;
                        }
                        else
                        {
                            //Let the Last activity remain for the viewer
                        }
                    }
                    if(regionNameList.contains(regionName)){
                        regionNameList.remove(regionName);
                    }
                }

            }
        });

        try {
            for(String key:ssnRegionMap.keySet()) {
                Region region = ssnRegionMap.get(key);
                beaconManager.startMonitoringBeaconsInRegion(region);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public static void showNotification(String message){
        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(instance)
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("Welcome!") // title for notification
                .setContentText(message) // message for notification
                .setAutoCancel(true); // clear notification after click
        NotificationManager mNotificationManager =
                (NotificationManager) instance.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

    }
    public static MyApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        present = 0;
        setUpBeacon();
    }

    private void setUpBeacon() {

        ssnRegionMap = new HashMap<>();
        regionList = new ArrayList<>();
        regionNameList = new ArrayList<>();

        String res_1 = "Hotel Leela";
        String res_2 =  "Hotel Trident";
        String res_3 = "Skydiving";

        ssnRegionMap.put("0x0117c55fc452",new Region(res_1,nameSpaceId,Identifier.parse("0x0117c55fc452"),null));
        ssnRegionMap.put("0x0117c555c65f",new Region(res_2,nameSpaceId,Identifier.parse("0x0117c555c65f"),null));
        ssnRegionMap.put("0x0117c55ec086",new Region(res_3,nameSpaceId,Identifier.parse("0x0117c55ec086"),null));

        beaconManager = BeaconManager.getInstanceForApplication(this);

        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));

        new BackgroundPowerSaver(this);
        beaconManager.bind(this);

    }



}
