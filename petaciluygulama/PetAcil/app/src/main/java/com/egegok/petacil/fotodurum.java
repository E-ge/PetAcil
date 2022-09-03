package com.egegok.petacil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class fotodurum extends AppCompatActivity {
    //------
    //yarım kaldığım yer fotoğraf çekmek için kamera izni alıp çekilen fotoğafı firabaseye yazdırmak
    //------
    //firebase bağladığım eklentileri (kullandığım)
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ImageView petimage;
    TextView durumtext;
    Button vetcall, vetadres;
    //bu uri picasso ile kullanılıyor ve fotoğraf boyutlarını küçülterek programın çökme olasılığını ve veri yollama hızını maxsimize ediyor
    Uri imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fotodurum);

        //firebase store için kullanılan referanslar bunlar
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        petimage = findViewById(R.id.petimage);
        durumtext = findViewById(R.id.durumtext);
        vetcall = findViewById(R.id.vetcall);
        vetadres = findViewById(R.id.vetadres);


        petimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(fotodurum.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(fotodurum.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Intent galeri = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(galeri, 2);
                }
            }
        });

    }

    public void vetcagir (View view){
        if (durumtext.getText().toString().matches("")) {

            Toast.makeText(fotodurum.this, "Lütfen Hayvanın Durumu Hakkında Bilgi Veriniz..", Toast.LENGTH_LONG).show();

        } else {
            Intent intent = new Intent(fotodurum.this, vetcagir.class);
            startActivity(intent);
        }
    }

    public void adresulas(View view) {
        if (durumtext.getText().toString().matches("")) {

            Toast.makeText(fotodurum.this, "Lütfen Hayvanın Durumu Hakkında Bilgi Veriniz..", Toast.LENGTH_LONG).show();

        } else {
                Intent intent = new Intent(fotodurum.this, vetbekleme.class);
                startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle=data.getExtras();
        Bitmap bitmap=(Bitmap) bundle.get("data");
        petimage.setImageBitmap(bitmap);
    }
    /*    petimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utillity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Fotoğraf Çek"))
                        galleryIntent();
                    else if(userChoosenTask.equals("Galeriden Seç"))
                        cameraIntent();
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Fotoğraf Çek", "Galeriden Seç",
                "İptal" };

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(fotodurum.this);
        builder.setTitle("Fotoğraf Ekle!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utillity.checkPermission(fotodurum.this);

                if (items[item].equals("Fotoğraf Çek")) {
                    userChoosenTask ="Fotoğraf Çek";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Galeriden Seç")) {
                    userChoosenTask ="Galeriden Seç";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("İptal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

      private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        petimage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        petimage.setImageBitmap(bm);
    }
*/

    /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1 ){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent galeri= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeri,2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2 && requestCode==RESULT_OK && data != null){
            imageData=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageData);
                petimage.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    } */


    /*public void vetcallupload(View view){
        if(imageData !=null){
            //rastgele id atamak için uuid kullanıyoruzki firebase storda aynı id ile üst üste yazma olmasın
            UUID uuid = UUID.randomUUID();
            //storda nasıl tutulacağının formatı resim artı jpg
            String imageName = "images/" + uuid +".jpg";
            //stora kaydetme listiner yazımı bu bir methot
            storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                        }
                    });
                }
            });
        }
    }

    public void takePicture(View view){
        //resim çekmek için önce izin alıyoruz kameraya erişim icin izin yoksa izin soruyoruz tekrardan
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        } else{
            Intent intentCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentCam, 2);

        }
    }*/



    }
