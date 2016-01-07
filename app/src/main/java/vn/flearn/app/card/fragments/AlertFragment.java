package vn.flearn.app.card.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import vn.flearn.app.card.R;
import vn.flearn.app.card.async.AsyncReset;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlertFragment extends DialogFragment {


    public AlertFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.alert_reset)
                .setPositiveButton("Thiết lập lại" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        (new AsyncReset(getContext())).execute();
                    }
                })
                .setNegativeButton("Hủy bỏ" , new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext() , "Hủy bỏ" , Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }
}
