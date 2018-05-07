package id.web.owlstudio.androidlanjutan.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.web.owlstudio.androidlanjutan.BuildConfig;
import id.web.owlstudio.androidlanjutan.R;

public class CameraActivity extends AppCompatActivity {

    @BindView(R.id.ivHasilFoto)
    ImageView ivHasilFoto;
    @BindView(R.id.fabCapture)
    FloatingActionButton fabCapture;

    private Uri fileUri;
    private String mCurrentPhotoPath;
    private static final int ACT_FOTO_CODE = 99;
    private static final String FOLDER_FOTO = "AplikasiKamera";
    private static final int CAMERA_REQUEST_CODE = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.camera_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.nav_share){

            //cek keberadaan gambar
            if (mCurrentPhotoPath == null) {
                Toast.makeText(this, "File belum ada", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Intent intent = new Intent(Intent.ACTION_SEND);

                // set tipe intent
                intent.setType("image/*");

                // bawa foto ke intent
                intent.putExtra(Intent.EXTRA_STREAM, fileUri);

                // tampilkan aplikasi sosmed
                startActivity(Intent.createChooser(intent, "Share foto"));
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fabCapture)
    public void onViewClicked() {
        // Tampilkan aplikasi yang memiliki fitur foto
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Menentukan direktori penyimpanan foto
        fileUri = ambilOutputMediaFileUri(ACT_FOTO_CODE);

        // mengambil direktori penyimpanan foto dari method
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        //Jalankan
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private Uri ambilOutputMediaFileUri(int actFotoCode) {
        // mengambil alamat directory file
        return FileProvider.getUriForFile(CameraActivity.this, BuildConfig.APPLICATION_ID + ".provider",
                ambilOutputMediaFile(actFotoCode));
    }

    private File ambilOutputMediaFile(int actFotoCode) {

        // Atur alamat penyimpanan foto (SDcard/pictures/folder_foto)
        File penyimpananMediaDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), FOLDER_FOTO);

        // Cek Keberadaan Folder
        if (!penyimpananMediaDir.exists()) {
            //Buat foldernya
            if (!penyimpananMediaDir.mkdir()) {
                Toast.makeText(this, "Gagal Membuat folder", Toast.LENGTH_SHORT).show();

                // stop progress dengan return
                return null;
            }
        }

        // Simpan format tanggal saar pengambilan gambar
        String waktu = new SimpleDateFormat("yyyyMMdd_hhMss", Locale.getDefault()).format(new Date());

        // Variable penampung File
        File mediaFile;

        // buat nama foto dengan waktu
        if(actFotoCode == ACT_FOTO_CODE){
            mediaFile = new File(penyimpananMediaDir.getPath() + File.separator + "IMG" + waktu + ".jpg");
        } else {
            return null;
        }

        // Simpan file ke path folder
        mCurrentPhotoPath = "file:" + mediaFile.getAbsolutePath();

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == CAMERA_REQUEST_CODE ) {
            // Tampilkan gambar ke Image View
            tampilGambar();
        } else if ( requestCode == RESULT_CANCELED ) {
            // jika user membatalkan memilih aplikasi pengambil foto
            Toast.makeText(this, "Batal mengambil Foto", Toast.LENGTH_SHORT).show();
        } else {
            // jika terjadi kesalahan
            Toast.makeText(this, "Gagal mengambil Foto", Toast.LENGTH_SHORT).show();
        }
    }

    private void tampilGambar(){
        // dapatkan alamat file
        Uri imageUri =Uri.parse(mCurrentPhotoPath);

        // ambil file
        File file = new File(imageUri.getPath());

        // cek keberadaan file
        if(file.exists()){
            try{
                InputStream ims = new FileInputStream(file);

                // tampilkan ke image view
                ivHasilFoto.setImageBitmap(BitmapFactory.decodeStream(ims));
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        } else {
            //jika foto tidak ada
            Toast.makeText(this, "Foto belum tersedia", Toast.LENGTH_SHORT).show();
        }
    }
}
