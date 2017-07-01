package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.data.PetContract.PetEntry;

import org.w3c.dom.Text;

/**
 * Created by chira on 6/30/2017.
 */

public class PetCursorAdapter extends CursorAdapter {
    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Find the textviews that are to be set with data
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextview = (TextView) view.findViewById(R.id.summary);

        //Extract data from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_PET_NAME));
        String breed = cursor.getString(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_PET_BREED));
        //Set the extracted data to their respective TextViews
        nameTextView.setText(name);
        summaryTextview.setText(breed);
    }
}
