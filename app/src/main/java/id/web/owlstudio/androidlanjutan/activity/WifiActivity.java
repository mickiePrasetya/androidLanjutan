package id.web.owlstudio.androidlanjutan.activity;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.owlstudio.androidlanjutan.R;

public class WifiActivity extends AppCompatActivity {

    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.swon)
    Switch swon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        ButterKnife.bind(this);

        // ketika switch berubah
        swon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // cek jika techeck
                if(isChecked){
                    setWifiOn(true);
                    Toast.makeText(WifiActivity.this, "Mengaktifkan WiFi", Toast.LENGTH_SHORT).show();

                } else {
                    setWifiOn(false);
                    Toast.makeText(WifiActivity.this, "Mengnonaktifkan WiFi", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private  void setWifiOn(boolean isOn){
        // Initiate Wifi Manager
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // kita cek kondisi wifi terakhir
        if(isOn) {
            wifiManager.setWifiEnabled(true);
        } else {
            wifiManager.setWifiEnabled(false);
        }
    }
}
