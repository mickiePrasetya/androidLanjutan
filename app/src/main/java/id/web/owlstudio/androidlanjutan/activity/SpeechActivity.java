package id.web.owlstudio.androidlanjutan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.web.owlstudio.androidlanjutan.R;

public class SpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    @BindView(R.id.tvHasilSTT)
    TextView tvHasilSTT;
    @BindView(R.id.etBahanTTS)
    EditText etBahanTTS;
    @BindView(R.id.btnSTT)
    ImageView btnSTT;
    @BindView(R.id.btnTTS)
    ImageView btnTTS;

    // Deklarasi Komponen
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        ButterKnife.bind(this);

        //inisiasi komponen
        textToSpeech = new TextToSpeech(SpeechActivity.this, this);
    }

    @OnClick(R.id.btnSTT)
    public void onBtnSTTClicked() {
        //
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Mulai bicara...");

        try {
            // mulai
            startActivityForResult(intent, 0);
        }catch(Exception e) {
            Toast.makeText(this, "Google Speech tidak tersedia", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnTTS)
    public void onBtnTTSClicked() {
        mulaiBicara();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            // ambil data suara yang masuk dari dialog google
            ArrayList<String> hasilSuara = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            // tampilkam ke textview
            tvHasilSTT.setText(hasilSuara.get(0));

            // Langsung putar audio
            etBahanTTS.setText(hasilSuara.get(0));
            mulaiBicara();
        }
    }

    @Override
    public void onInit(int status) {
        // jika text to speech dapat digunakan
        if (status == TextToSpeech.SUCCESS) {
            //Atur bahasa ke bahasa Indonesia
            Locale indoLang = new Locale("id-ID");

            //variable untuk memasukkan bahasa ke TTS
            int result = textToSpeech.setLanguage(indoLang);

            // jika bahasa tidak tersedia di perangkat atau perangkat tidak mendukung
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // tampilkan Toast
                Toast.makeText(this, "Bahasa tidak ditemukan!", Toast.LENGTH_SHORT).show();

            } else {
                // jalankan method
                mulaiBicara();
            }
        }
    }

    private void mulaiBicara() {
        // tampung textnya
        String teks = etBahanTTS.getText().toString();

        // proses tts
        textToSpeech.speak(teks, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(textToSpeech != null){
            //hentikan
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
