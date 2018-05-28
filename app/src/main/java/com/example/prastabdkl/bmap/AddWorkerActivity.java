package com.example.prastabdkl.bmap;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prastabdkl.bmap.Database.DBHelper;
import com.example.prastabdkl.bmap.Database.bmapContract;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static com.example.prastabdkl.bmap.R.id.toolbar;

public class AddWorkerActivity extends AppCompatActivity {
    private DBHelper mydb;
    private Bitmap bm = null;

    TextView name;
    TextView phone;
    TextView email;
    TextView bank;
    TextView place;

    String Worker_name;
    String Worker_phone;
    String Worker_email;
    String Worker_bank;
    String Worker_place;
    int id_To_Update = 0;
    Toolbar toolbar;

    private int REQUEST_CAMERA = 100;
    private int SELECT_FILE = 200;
    ImageView imageView;

    private static final String LOG_TAG = AddWorkerActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_worker);
        imageView = (ImageView)findViewById(R.id.worker_image);

        name = (TextView) findViewById(R.id.NameEdit);
        phone = (TextView) findViewById(R.id.PhoneEdit);
        email = (TextView) findViewById(R.id.EmailEdit);
        bank = (TextView) findViewById(R.id.BankIdEdit);
        place = (TextView) findViewById(R.id.AddressEdit);

        mydb = new DBHelper(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Worker");

        //Event to take picture form gallery or take photo
        Button addpicbutton = (Button) findViewById(R.id.buttonAddPic);
        addpicbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectPhoto();

            }
        });

        //Event to enter the values into the database
        Button savebutton = (Button) findViewById(R.id.buttonAdd);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Worker_name = name.getText().toString();
                Worker_phone = phone.getText().toString();
                Worker_email = email.getText().toString();
                Worker_bank = bank.getText().toString();
                Worker_place = place.getText().toString();

                //mydb.insertWorker(Worker_name, Worker_phone, Worker_email, Worker_bank, Worker_place);
                insertIntoDatabase(Worker_name,Worker_phone,Worker_email,Worker_bank,Worker_place);

                Toast.makeText(AddWorkerActivity.this, "Worker Added", Toast.LENGTH_SHORT).show();
                Wages w;
                w = new Wages();
            }
        });
    }

    /*
    * This methods insert data in the database through Content Provider
    * */
    private void insertIntoDatabase(String worker_name, String worker_phone, String worker_email,
                                    String worker_bank, String worker_place){
        ContentValues values = null;
        values = new ContentValues();

        values.put(bmapContract.WorkerEntry.COLUMN_NAME, worker_name);
        values.put(bmapContract.WorkerEntry.COLUMN_PHONE, worker_phone);
        values.put(bmapContract.WorkerEntry.COLUMN_EMAIL, worker_email);
        values.put(bmapContract.WorkerEntry.COLUMN_BANK, worker_bank);
        values.put(bmapContract.WorkerEntry.COLUMN_CITY, worker_place);

        // insert data in database
        Uri WorkerUri
                = getContentResolver().insert(bmapContract.WorkerEntry.CONTENT_URI, values);
        long WorkerRowId = ContentUris.parseId(WorkerUri);
        Log.v(LOG_TAG, String.valueOf(WorkerRowId));

        if (WorkerRowId > 0){
            Toast.makeText(this, "Your data has been Added", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this,MainActivity.class));
        }

    }


        /*
        * Opens dialog box to prompt user to take photo or choose pic from gallery !9
        * */
        private void selectPhoto() {
            final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (items[which].equals("Take Photo")) {
                        // Compose camera intent
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    } else if (items[which].equals("Choose from Library")) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(
                                Intent.createChooser(intent, "Select File"),
                                SELECT_FILE);
                    } else if (items[which].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });

            // show dialog
            builder.show();
        }

    /**
     * This function handles result receiver by calling
     * startActivityForResult.
     *
     *  @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, stream);

                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(stream.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                imageView.setImageBitmap(thumbnail);
                bm = thumbnail;
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                String[] projection = { MediaStore.MediaColumns.DATA };
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null, null);

                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);

                final int REQUIRED_SIZE = 200;
                int scale = 1;

                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE) {
                    scale *= 2;
                }
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                imageView.setImageBitmap(bm);
            }
        }
    }
}
