package com.example.user.bitm_project.Moment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.bitm_project.ExpenseRecord.ExpenseRecordShow_Activity;
import com.example.user.bitm_project.Navigation_Activity;
import com.example.user.bitm_project.R;
import com.example.user.bitm_project.TravelEvent.EventShowActivity;
import com.example.user.bitm_project.TravelEvent.TravelEvent_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.text.DateFormat;
import java.util.Date;

public class MomentGalleryActivity extends AppCompatActivity {

    private EditText momentDetailsET;
    private ImageView imageView;
    private Button saveButton;
    private String mPhotoData;
    private Bitmap photo;
    private ProgressDialog progressDialog;


    private StorageReference mStoreRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth auth;
    private String rowId;
    private FirebaseUser user;
    private Uri selectImage_Uri = null;

    private String stringUri;

    public static final  int REQUEST_CAPTURE = 100,SELECT_FILE = 0;

    private int updateId;
    private String updateImage;
    private String updateDetails;
    String takingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_gallery);

        setTitle("Moment Gallery Page ");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        mStoreRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserInfo");
        mDatabaseRef.keepSynced(true);

        rowId = getIntent().getStringExtra("rowId");

        momentDetailsET = findViewById(R.id.momentDetails_id);
        imageView = findViewById(R.id.momentImage_id);
        saveButton = findViewById(R.id.saveMomentButton_id);

        progressDialog = new ProgressDialog(this);


        getCameraPermission();


        if (!hasCamara()){
            imageView.setEnabled(false);

        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveTo_FireBase();

            }
        });


    }

    public void TakePhoto(View view) {
        SelectImage();

    }

    private void SaveTo_FireBase() {


        progressDialog.setMessage("Moment Uploading ....");
        progressDialog.show();

        final String momentDetails = momentDetailsET.getText().toString();
        if (momentDetails.isEmpty())
        {
            momentDetailsET.setError("Please Enter Moment Details");
        }else if (!TextUtils.isEmpty((CharSequence) imageView))
        {
            Toast.makeText(MomentGalleryActivity.this, "Please Take a photo ", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                   //--------------------------

                if (selectImage_Uri != null) {

                    StorageReference fileRef = mStoreRef.child(selectImage_Uri.getLastPathSegment());
                    fileRef.putFile(selectImage_Uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Uri downloadUri = taskSnapshot.getUploadSessionUri();

                            stringUri = downloadUri.toString();


                            Toast.makeText(MomentGalleryActivity.this, "Image Successful", Toast.LENGTH_SHORT).show();
                            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                            String rooId = user.getUid();

                            String id = mDatabaseRef.push().getKey();
                            MomentGallery momentGallery = new MomentGallery(id,currentDateTimeString,momentDetails,stringUri);

                            mDatabaseRef.child(rooId).child("TravelEvents").child(rowId).child("MomentGallery").child(id).setValue(momentGallery)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                momentDetailsET.setText("");
                                                Toast.makeText(MomentGalleryActivity.this, "Moment Gallery Save Successfully Added ", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(MomentGalleryActivity.this, Gallery_Show_Activity.class);
                                                intent.putExtra("rowId",rowId);
                                                startActivity(intent);
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MomentGalleryActivity.this, "Moment Gallery Save Field "+"\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });


                        }
                    });
                }

                ///-------------------------


            }catch (Exception e)
            {
                progressDialog.dismiss();
                Toast.makeText(MomentGalleryActivity.this, "LogIn First", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public Boolean hasCamara(){


        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);

    }
    public void SelectImage(){
        final CharSequence [] items = {"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MomentGalleryActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")){

                    try{
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,REQUEST_CAPTURE);

                    }catch (Exception e){

                        Toast.makeText(MomentGalleryActivity.this, "Some missing", Toast.LENGTH_SHORT).show();

                    }

                }else if (items[i].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"),SELECT_FILE);
                }else if (items[i].equals("Cancel")){
                    dialog.dismiss();

                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK ){

            if (requestCode ==REQUEST_CAPTURE) {
                Bundle bundle = data.getExtras();
                photo = (Bitmap) bundle.get("data");
                mPhotoData = encodeToBase64(photo,Bitmap.CompressFormat.JPEG,100);
                imageView.setImageBitmap(photo);


            }else if (requestCode == SELECT_FILE){

                selectImage_Uri = data.getData();
                imageView.setImageURI(selectImage_Uri);

            }
        }


    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void getCameraPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAPTURE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MomentGalleryActivity.this, Navigation_Activity.class));

            return true;
        }
        if (id == R.id.galley_Show_id) {
            startActivity(new Intent(MomentGalleryActivity.this, Gallery_Show_Activity.class));

            return true;
        }
        if (id == R.id.travel_events_Show_id) {
            startActivity(new Intent(MomentGalleryActivity.this, EventShowActivity.class));

            return true;
        }
        if (id == R.id.expenseRecordShow_id) {
            startActivity(new Intent(MomentGalleryActivity.this, ExpenseRecordShow_Activity.class));

            return true;
        }
        if (id == R.id.logOut_id) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



   /* private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
*/

    private String getFile (Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

       }


  }
