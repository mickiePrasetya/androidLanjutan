package id.web.owlstudio.androidlanjutan.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.web.owlstudio.androidlanjutan.R;

public class TelephoneActivity extends AppCompatActivity {

    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etTxtMessage)
    EditText etTxtMessage;
    @BindView(R.id.btnCall)
    Button btnCall;
    @BindView(R.id.btnSms)
    Button btnSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnCall)
    public void onBtnCallClicked() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            // Lakukan Panggilan ke Nomor Telepon
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + etPhoneNumber.getText().toString()));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Panggilan tidak diizinkan", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.btnSms)
    public void onBtnSmsClicked() {
        String number_phone = etPhoneNumber.getText().toString();
        String message = etTxtMessage.getText().toString();

        // TODO: SIapkan Intent ke SMS - Cara 1: DEPRECATED
        // Intent intent = new Intent(Intent.ACTION_VIEW);
        // intent.putExtra("sms_body", message);
        // intent.putExtra("address", number_phone);
        // intent.setType("vnd.android.dir/mms-sms");

        // TODO: CARA 2
        // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number_phone, null));
        // // mulai
        // startActivity(intent);

        // TODO: CARA 3
        // Uri uri = Uri.parse("smsto:" + number_phone);
        // Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        // intent.putExtra("sms_body", message);
        //
        // // mulai
        // startActivity(intent);


        // TODO: CARA 4 - Langsung SMS tanpa intent ke app lain
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number_phone, null, message, null, null);
            Toast.makeText(this, "Memgirim Pesan", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Gagal Memgirim Pesan", Toast.LENGTH_SHORT).show();
        }


    }
}
