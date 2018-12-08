package com.bfirestone.udacity.popularmovies.menu;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.bfirestone.udacity.popularmovies.R;

import butterknife.BindArray;
import butterknife.BindString;


public class SortingDialogFragment extends DialogFragment {
    @BindArray(R.array.pref_sort_by_labels)
    String[] sortByChoices;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(R.array.pref_sort_by_labels, 0,
                (dialog, which) -> {

        });

//        builder.setMessage(sortByChoices)
//                .setPositiveButton("Poplarity", (dialog, id) -> {
//                    // FIRE ZE MISSILES!
//                })
//                .setNegativeButton("Rating", (dialog, id) -> {
//                    // User cancelled the dialog
//                });
//        // Create the AlertDialog object and return it
        return builder.create();
    }
}
