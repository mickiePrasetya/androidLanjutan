package id.web.owlstudio.androidlanjutan.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.web.owlstudio.androidlanjutan.R;

public class BluetoothActivity extends AppCompatActivity {

    @BindView(R.id.btnon)
    Button btnon;
    @BindView(R.id.btnoff)
    Button btnoff;
    @BindView(R.id.btnvisible)
    Button btnvisible;
    @BindView(R.id.btnpaired)
    Button btnpaired;
    @BindView(R.id.listv)
    ListView listv;
    @BindView(R.id.imageView)
    ImageView imageView;

    // Komponen pngatur Bluetooth
    BluetoothAdapter bluetoothAdapter;

    // Komponen untuk mnapung data device yang pernah terhubung
    Set<BluetoothDevice> paired_device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);

        // initiate bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @OnClick(R.id.btnon)
    public void onBtnonClicked() {
        // cek apakah sebelumnya sudah on
        if( bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth sudah aktif", Toast.LENGTH_SHORT).show();

        } else{
            // memanggil pengaturan bluetooth
            Intent hidupkan = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(hidupkan, 0);
            Toast.makeText(this, "Mengaktifkan Bluetooth", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnoff)
    public void onBtnoffClicked() {
        // cek apakah sebelumnya sudah on
        if( !bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth sudah tidak aktif", Toast.LENGTH_SHORT).show();

        } else{
            // memanggil pengaturan bluetooth
            bluetoothAdapter.disable();

            Toast.makeText(this, "Mematikan Bluetooth", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnvisible)
    public void onBtnvisibleClicked() {
        // memanggil pengaturan bluetooth
        Intent visible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(visible, 0);
        Toast.makeText(this, "Bluetooth dapat ditemukan di perangkat lain", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnpaired)
    public void onBtnpairedClicked() {
        // dapatkan data perangkat yang sudah pernah terhubung dari pengaturan bluetoot adapter
        paired_device = bluetoothAdapter.getBondedDevices();

        // tampung ke data array
        ArrayList list_perangkat = new ArrayList();

        // ambil data per satu data perangkat dan masukkan ke dalam list_perangkat
        // bt menjadi variable penampung dalam looping, dengan nama class BluetoothDevice
        for (BluetoothDevice bt: paired_device){
            // masukkan data ke dalam dapater listview
            list_perangkat.add(bt.getName());
        }

        // Masukkan ke adapter listview
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list_perangkat);

        // pasang adapter ke komponen list view
        listv.setAdapter(adapter);

    }
}
