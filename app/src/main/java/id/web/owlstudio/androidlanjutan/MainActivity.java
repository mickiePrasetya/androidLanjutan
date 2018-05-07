package id.web.owlstudio.androidlanjutan;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.owlstudio.androidlanjutan.activity.BluetoothActivity;
import id.web.owlstudio.androidlanjutan.activity.CameraActivity;
import id.web.owlstudio.androidlanjutan.activity.SpeechActivity;
import id.web.owlstudio.androidlanjutan.activity.TelephoneActivity;
import id.web.owlstudio.androidlanjutan.activity.WifiActivity;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.lvHomeMenu)
    ListView lvHomeMenu;

    public static int RC_CAMERA_AND_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // TODO 4: panggil method methodRequiresPermission()
        methodRequiresPermission();

        // TODO 1: Set Event Click Item List
        lvHomeMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this, CameraActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, TelephoneActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, BluetoothActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, WifiActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, SpeechActivity.class));
                        break;
                }
            }
        });
    }

    // TODO 2: Method/Function berfungsi memnuculkan Popup Permission
    // Jangan lupa untuk define uses-permission di manifest juga
    private void methodRequiresPermission() {
        // Permission yang akan kita gunakan
        String[] perms = {Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

        // TODO 3: Cek apakah permission sudah diberikan
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            Toast.makeText(this, "Izin diberikan", Toast.LENGTH_SHORT).show();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "Aplikasi ini membutuhkan akses Kamera",
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
    }
}
