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
import android.widget.ListView;
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


        String projection[] = {
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

        //Find the list view which is to be populated with data
        ListView listView = (ListView) findViewById(R.id.list_view_pet);

        //Setup the adapter to create list items
        PetCursorAdapter petCursorAdapter = new PetCursorAdapter(this, cursor);
        //Attach the adapter to list view to populate it with data
        listView.setAdapter(petCursorAdapter);


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

        Uri uri = getContentResolver().insert(PetContract.PetEntry.CONTENT_URI, petInfo);


//         // Insert the new row, returning the primary key value of the new row
//        long newRowId = db.insert(PetEntry.TABLE_NAME, null, petInfo);
//        Log.v("CATALOG ACTIVITY rowid ", newRowId+"" );
    }

    //Deletes all data from database
    public void deletePet() {
        getContentResolver().delete(PetEntry.CONTENT_URI, null, null);
        displayDatabaseInfo();
    }
}