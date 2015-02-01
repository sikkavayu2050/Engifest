package com.dtu.engifest.util;

/**
 * Created by naman on 18/12/14.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ScrollView;

import com.dtu.engifest.R;


public class DialogUtils {



    public static void showAboutDtu(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about_dtu");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new AboutDtuDialog().show(ft, "dialog_about");
    }

    public static class AboutDtuDialog extends DialogFragment {

        public AboutDtuDialog() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            ScrollView aboutBodyView = (ScrollView) layoutInflater.inflate(R.layout.dialog_about_dtu, null);

            return new AlertDialog.Builder(getActivity())

                    .setView(aboutBodyView)
                    .create();
        }

    }

    public static void showAboutEngifest(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about_dtu");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new AboutEngifestDialog().show(ft, "dialog_about");
    }

    public static class AboutEngifestDialog extends DialogFragment {

        public AboutEngifestDialog() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            ScrollView aboutBodyView = (ScrollView) layoutInflater.inflate(R.layout.dialog_about_engifest, null);

            return new AlertDialog.Builder(getActivity())

                    .setView(aboutBodyView)
                    .create();
        }

    }



}
