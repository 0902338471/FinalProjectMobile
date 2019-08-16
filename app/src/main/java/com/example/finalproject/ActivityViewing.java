package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URI;

public class ActivityViewing extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    static final int AUTOCOMPLETE_REQUEST_CODE=100;
    private int Image_Request_Code=7;
    Uri FilePathUri;
    String Storage_Path="UploadedImage/";
    String Database_Path="UserImageDatabase";
    StorageReference storageReference;
    User mUser;
    DatabaseReference databaseReference;
    boolean imageReady=false;
    ImageView avatar;
    ImageView photoStatus;
    Button choosingImage;
    EditText nameActivity ;
    EditText status;
    EditText date;
    ProgressDialog progressDialog ;
    MyDate actiDate;
    String id;
    int indexDate;
    int sizeOfActi;
    String photoURL;
    boolean isChosenClick=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing);
        initiateViewComponent();
        onPreparingFirebase();
        onDisplayingData();
        onChoosingImage();
        onBackToSpecificDate();
    }

    private void initiateViewComponent() {
        avatar=findViewById(R.id.avatar);
        photoStatus=findViewById(R.id.content_image);
        choosingImage=(Button)findViewById(R.id.chooseImage);
        nameActivity = findViewById(R.id.nameActivity);
        status=findViewById(R.id.status);
        date=findViewById(R.id.date);
        progressDialog = new ProgressDialog(ActivityViewing.this);
    }

    private void onPreparingFirebase() {
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
    }

    private void onChoosingImage() {
        choosingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
            }
        });
    }

    private void onBackToSpecificDate() {
        Button backButton = findViewById(R.id.backbutton);
        final String message = getIntent().getStringExtra("positionClick");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backStringname = nameActivity.getText().toString();
                String backStatus=status.getText().toString();
                String backDate=date.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("nameActivityBack", backStringname);
                intent.putExtra("StatusBack",backStatus);
                intent.putExtra("positionClickBack", message);
                intent.putExtra("DateBack",backDate);
                UploadImageFileToFirebaseStorage();
                if(imageReady||isChosenClick==false)
                {
                    intent.putExtra("photoURLBack",photoURL);
                    Log.d("Upload","OK");
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Wating for data to be saved", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void onDisplayingData() {
       // FloatingActionButton fab = findViewById(R.id.fab);
        Intent passIntent=getIntent();
        //nameActivity.setText(passIntent.getStringExtra("nameActivity"));
        //status.setText(passIntent.getStringExtra("Status"));
        //mUser=(User)passIntent.getSerializableExtra("UserClass");
        actiDate=(MyDate)passIntent.getSerializableExtra("DateStructure");
        String passcontentPhoto=passIntent.getStringExtra("photoStatus");
        Log.d("passContent","aa"+passcontentPhoto);
        if(passcontentPhoto!=null)
        {
            Glide.with(this).load(passcontentPhoto).into(photoStatus);
            photoURL=passcontentPhoto;
        }
        if(actiDate==null)
            Log.d("nulldo","ss");
        id=passIntent.getStringExtra("UserUID");
        Log.d("here1","aa"+passIntent.getStringExtra("IndexPass"));
        Log.d("here2","aa"+passIntent.getStringExtra("SizePass"));
        //Log.d("here3","aa"+actiDate.getMonth());
        indexDate=Integer.parseInt(passIntent.getStringExtra("IndexPass"));
        sizeOfActi=Integer.parseInt(passIntent.getStringExtra("SizePass"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            FilePathUri = data.getData();
            isChosenClick=true;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                photoStatus.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                choosingImage.setText("Image Selected");
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
    public void UploadImageFileToFirebaseStorage() {
        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
           // progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            //progressDialog.show();

            // Creating second StorageReference.
            final StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.
                            String TempImageActi = nameActivity.getText().toString().trim();
                            String TempImageStatus=status.getText().toString().trim();
                            String TempImageDate=status.getText().toString().trim();
                            // Hiding the progressDialog after done uploading.
                            //progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            //myItemActivity imageUploadInfo = new myItemActivity(String.valueOf(sizeOfActi+1),TempImageActi,TempImageStatus,actiDate, taskSnapshot.getStorage().getDownloadUrl().toString());
                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();
                            storageReference2nd.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        photoURL=downloadUri.toString();
                                        /*databaseReference.child("Uploads").child(fileName).setValue(downloadUri.toString()).addOnCompleteListener(new OnCompleteListener < Void > () {
                                            @Override
                                            public void onComplete(@NonNull Task < Void > task) {

                                                if (task.isSuccessful())
                                                    Toast.makeText(Upload.this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                                else
                                                    Toast.makeText(Upload.this, "Upload failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });*/
                                    } else {
                                        //Toast.makeText(Upload.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            imageReady=true;
                            Log.d("imagehere","aa"+photoURL);
                            Log.d("anotherimage","aa"+storageReference2nd.getDownloadUrl());
                            // Adding image upload id s child element into databaseReference.
                            //databaseReference.child(id).child(String.valueOf(indexDate)).setValue(imageUploadInfo);
                            Log.d("Done","aa");
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            //progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(ActivityViewing.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            //progressDialog.setTitle("Image is Uploading...");

                        }
                    });
        }
        else {

           // Toast.makeText(ActivityViewing.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }
}
