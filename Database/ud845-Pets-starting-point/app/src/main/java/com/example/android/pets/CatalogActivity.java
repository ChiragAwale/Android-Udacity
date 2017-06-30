package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetContract.PetEntry;
import com.example.android.pets.data.PetDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {
    PetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new PetDbHelper(this);

        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {


        String projection [] = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT
        };

        Cursor cursor = getContentResolver().query(
                PetEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
        //Stores the data received from database to cursor object
        //Cursor cursor = db.query(PetEntry.TABLE_NAME,projection,null,null,null,null,null);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
            displayView.append("ID-Name-Breed-Weight-Gender\n");

            //Stores column indexes to respective variables for extracting data needed from cursor object
            int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);
            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);

            //Extracts needed data from every position in the cursor object and displays it in the screen
            while(cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                displayView.append(currentID+"-"+currentName+"-"+currentBreed
                        +"-"+currentWeight+"-"+currentGender+"\n");
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
    //When the user comes back to the activity after finishing up adding a pet, this method helps to refresh views
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deletePet();
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Used for inserting a dummy data to the database
    public void insertPet() {
        ContentValues petInfo = new ContentValues();
        petInfo.put(PetEntry.COLUMN_PET_NAME, "Toto");
        petInfo.put(PetEntry.COLUMN_PET_BREED, "Terrier");
        petInfo.put(PetEntry.COLUMN_PET_GENDER, 1);
        petInfo.put(PetEntry.COLUMN_PET_WEIGHT, "7 KG");

        Uri uri = getContentResolver().insert(PetContract.PetEntry.CONTENT_URI,petInfo);


//         // Insert the new row, returning the primary key value of the new row
//        long newRowId = db.insert(PetEntry.TABLE_NAME, null, petInfo);
//        Log.v("CATALOG ACTIVITY rowid ", newRowId+"" );
    }
    //Deletes all data from database
    public void deletePet(){
        getContentResolver().delete(PetEntry.CONTENT_URI,null,null);
        displayDatabaseInfo();
    }
}