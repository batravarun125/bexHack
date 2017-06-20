package com.example.ayush.resturantninjas.Main;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.resturantninjas.R;
import com.example.ayush.resturantninjas.RestrauntActivity.BurgerKing;
import com.example.ayush.resturantninjas.RestrauntActivity.Dominos;
import com.example.ayush.resturantninjas.RestrauntActivity.KhanaKhazana;
import com.example.ayush.resturantninjas.RestrauntActivity.RVFoodAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RVAdapter.ClickListner {
    Resturant resturant;
    Context context;
    public List<Resturant> resturants;
    private static final int PERMISSIONS_REQUEST_CODE = 100 ;
    private static String[] mPermissions = { Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String TAG = MainActivity.class.getSimpleName();

    public Button mButton;
    public TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;



        if (!havePermissions()) {
            Log.i("Permission", "Requesting permissions needed for this app.");
            requestPermissions();
        }

        if(!isBlueEnable()){
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(bluetoothIntent);
        }


        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        String s = getIntent().getStringExtra("rglist");
        if(TextUtils.isEmpty(s)) {
            resturants = new ArrayList<>();
            resturants.add(new Resturant("bebe", "Dominos", 0));
            resturants.add(new Resturant("bebe", "Khana Khazana", 0));
            resturants.add(new Resturant("bebe", "Mc Donalds", 0));
        }
        else{
            resturants=new ArrayList<>();
            resturants.add(new Resturant("bebe", "ABC", 0));
        }
        RVAdapter adapter = new RVAdapter(this,resturants);
        adapter.setClickListner(this);
        rv.setAdapter(adapter);


    }



    @Override
    public void ItemClicked(View view, int position) {
        Intent intent;
        switch (position)
        {
            case 0:intent = new Intent(getApplicationContext(), Dominos.class);
                    intent.putExtra("Position",position);
                    startActivity(intent);
                    break;
            case 1: intent = new Intent(getApplicationContext(),KhanaKhazana.class);
                    intent.putExtra("Position",position);
                    startActivity(intent);
                    break;
            case 2:intent = new Intent(getApplicationContext(),BurgerKing.class);
                    intent.putExtra("Position",position);
                    startActivity(intent);
                    break;


        }
    }
    private boolean isBlueEnable() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        return bluetoothAdapter.isEnabled();

    }
    private boolean havePermissions() {
        for(String permission:mPermissions){
            if(ActivityCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                return  false;
            }
        }
        return true;
    }
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,mPermissions, PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_refresh){
            refresh();
        }
        return super.onOptionsItemSelected(item);
    }
    public  void refresh(){
        if (!MyApp.getInstance().regionList.isEmpty()){
            int size = MyApp.getInstance().regionList.size();
            String myRegionName = MyApp.getInstance().regionNameList.get(size-1);
            Log.d("lalalalala", String.valueOf(MyApp.getInstance().regionNameList.size()));
            if(myRegionName.equals("Dominos")){
                Log.d("lalala","Iside Dominos");
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("rglist",myRegionName);
                startActivity(intent);
                MyApp.getInstance().showNotification("Welcome to Dominos");
            }

            else if(myRegionName.equals("Mc Donalds")){
                Log.d("lalala","Iside Mc");
                Intent intent = new Intent(getApplicationContext(),BurgerKing.class);
                startActivity(intent);
                MyApp.getInstance().showNotification("Welcome to Burger King");

            }
            else if(myRegionName.equals("Khaana Khazaana")){

                Log.d("lalala","Inside Khaana khazaana");
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                MyApp.getInstance().showNotification("Welcome to Khaana Khazaana");

            }


            Toast.makeText(MainActivity.this, "Welcome to "+myRegionName, Toast.LENGTH_SHORT).show();



        }
    }
}
