package ca.cmpt276.cmpt276assignment3;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;



// Fragment that displays the message for when the player wins the game
// code is based of https://www.youtube.com/watch?v=y6StJRn-Y-A
public class MessageFragment extends AppCompatDialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // create the view to show

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.winning_layout, null);

        // Create a button listener

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {

                    requireActivity().finish();
                }
            }
        };

        // Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("You won!")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}
