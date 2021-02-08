package com.example.beachchair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SelectDeviceActivity extends AppCompatActivity {
    final static String BLE_MODULE_REAL_NAME = "HC-05";
    final static String BLE_MODULE_DISPLAY_NAME = "Dror's Beach Chair";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device);

        // Bluetooth Setup
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get List of Paired Bluetooth Device
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        List<Object> deviceList = new ArrayList<>();
        // There are paired devices. Get the name and address of each paired device.
        for (BluetoothDevice device : pairedDevices) {
            String deviceName = device.getName();
            //AMIT - show only possible option for beach chair
            if (deviceName.equals(BLE_MODULE_REAL_NAME)) {
                deviceName = BLE_MODULE_DISPLAY_NAME;
                String deviceHardwareAddress = device.getAddress(); // MAC address
                DeviceInfoModel deviceInfoModel = new DeviceInfoModel(deviceName,deviceHardwareAddress);
                deviceList.add(deviceInfoModel);
            }
        }

        if (deviceList.size() > 0) {
            // Display paired device using recyclerView
            RecyclerView recyclerView = findViewById(R.id.recyclerViewDevice);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            DeviceListAdapter deviceListAdapter = new DeviceListAdapter(this,deviceList);
            recyclerView.setAdapter(deviceListAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        else {
            alertNoDevices();
        }
    }

    void alertNoDevices() {
        View view = findViewById(R.id.recyclerViewDevice);
        Snackbar snackbar = Snackbar.make(view, "Activate Bluetooth or pair the Bluetooth device", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) { }
        });
        snackbar.show();
    }

}