package vn.flearn.app.card.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import vn.flearn.app.card.R;

public class PublicFunction {

    public static void showDialog(Context context, String title, String msg,
                                  DialogInterface.OnClickListener listenerOk,
                                  DialogInterface.OnClickListener listenerCancel) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(R.string.dialog_wording_ok, listenerOk);
        alertDialogBuilder.setNegativeButton(R.string.dialog_wording_cancel, listenerCancel);

        alertDialogBuilder.setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void showDialog(Context context, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(R.string.dialog_wording_ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialogBuilder.setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void showDialog(Context context, String title, String msg, String btnText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(btnText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialogBuilder.setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void showDialog(Context context, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(R.string.dialog_wording_ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialogBuilder.setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void showDialog(Context context, String title, String msg,
            DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(R.string.dialog_wording_ok, listener);
        alertDialogBuilder.setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            return cm.getActiveNetworkInfo().isConnected();
        }
        return false;
    }

    /*public static ProgressDialog getProgressDialog(Context context, String text) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(text);
        dialog.setCancelable(true);
        return dialog;
    }*/

    public static ProgressDialog getProgressDialog(Context context, String text) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(text);
        dialog.setCancelable(true);
        return dialog;
    }

    public static void dismissProgresDialog(ProgressDialog dialog) {
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        dialog.dismiss();
    }

    public static int getResoruceIdFromName(Context context, String name, String resourceType) {
        return context.getResources().getIdentifier(name, resourceType, context.getPackageName());
    }

    /**
     * Dialog prompting a forced upgrade
     *
     * @param context
     */
    public static void showNecessaryUpdateDialog(Context context) {
        final Context lContext = context;
        Dialog mUpdateDialog = new Dialog(context, R.style.dialog);
        mUpdateDialog.setContentView(R.layout.update_necessary_dialog);

        // Acknowledge button
        Button update = (Button) mUpdateDialog.findViewById(R.id.update_button);
        update.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Uri uri = Uri.parse(lContext.getResources().getString(R.string.google_play_url));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                lContext.startActivity(intent);
            }

        });

        mUpdateDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mUpdateDialog.setCancelable(false);
        mUpdateDialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mUpdateDialog.show();
    }

    /**
     * Determine if there is a phone number that you specified in the device
     *
     * @param context
     * @param phonenumber
     * @return
     */
    static public boolean hasPhoneNumberInDevice(Context context, String phonenumber) {
        ContentResolver resolver = context.getContentResolver();
        Cursor phoneNumberCursor = resolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.NUMBER + "=?", new String[] { phonenumber },
                null);

        return (phoneNumberCursor.getCount() > 0);
    }

    static public void showGeneralDialog(Context context, String title, String messeage,
            String buttonText, final OnClickListener onClickListener, boolean cancelable) {

        final Dialog mGeneralDialog = new Dialog(context, R.style.dialogFade);
        mGeneralDialog.setContentView(R.layout.general_dialog);

        final TextView titleTextView = (TextView) mGeneralDialog
                .findViewById(R.id.dialog_text_title);
        titleTextView.setText(title);

        final TextView messeageTextView = (TextView) mGeneralDialog
                .findViewById(R.id.dialog_messeage_text);
        messeageTextView.setText(messeage);

        final Button button = (Button) mGeneralDialog.findViewById(R.id.dialog_button);
        button.setText(buttonText);
        if (onClickListener != null) {
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setOnClickListener(onClickListener);
                    view.performClick();
                    mGeneralDialog.dismiss();
                }
            });
        } else {
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGeneralDialog.dismiss();
                }
            });
        }

        mGeneralDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mGeneralDialog.setCancelable(cancelable);
        mGeneralDialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mGeneralDialog.show();

    }

    public static byte[] asByteArray(String hex) {
        // Generate a byte array of length of 1/2 of the length of the string.
        byte[] bytes = new byte[hex.length() / 2];

        // Number of elements of the byte array, I repeat the process.
        for (int index = 0; index < bytes.length; index++) {
            // And stored in the array by converting to a byte hexadecimal string.
            bytes[index] = (byte) Integer.parseInt(hex.substring(index * 2, (index + 1) * 2), 16);
        }

        // I return an array of bytes.
        return bytes;
    }

    // Converting a bytes array to string of hex character
    public static String byteArrayToHexString(byte[] b) {
        int len = b.length;
        String data = new String();

        for (int i = 0; i < len; i++) {
            data += Integer.toHexString((b[i] >> 4) & 0xf);
            data += Integer.toHexString(b[i] & 0xf);
        }
        return data;
    }

    // Converting a string of hex character to bytes
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s
                    .charAt(i + 1), 16));
        }
        return data;
    }

    public static String readFile(Context context) {
        String json = "";

        try {
            InputStream is = context.getAssets().open("course.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    /**
     * @Function name: getUUID
     * @Description Get UUID
     *
     * @param activity
     *            {@link android.app.Activity}
     * @return {@link String} UUID
     *
     * */
    public static String getUUID(Activity activity) {
//        TelephonyManager tm = (TelephonyManager) activity.getBaseContext()
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        String IMEI = "" + tm.getDeviceId();

        final String androidId =
                Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);

        UUID uuid;
        // Use the Android ID unless it's broken, in which case fallback on deviceId,
        // unless it's not available, then fallback on a random number which we store
        // to a prefs file
        try {
            if (!"9774d56d682e549c".equals(androidId)) {
                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
            } else {
                final String deviceId =
                        ((TelephonyManager) activity.getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();
                uuid = deviceId!=null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return uuid.toString();

//        if (IMEI != null && IMEI.length() > 0) {
//            String tmSerial, androidId;
//
//            tmSerial = "" + tm.getSimSerialNumber();
//            androidId = ""
//                    + android.provider.Settings.Secure.getString(
//                    activity.getContentResolver(),
//                    android.provider.Settings.Secure.ANDROID_ID);
//
//            UUID deviceUuid = new UUID(androidId.hashCode(),
//                    ((long) IMEI.hashCode() << 32) | tmSerial.hashCode());
//            String deviceId = UUID.randomUUID().toString()
//                    + deviceUuid.toString();
//            Log.d("deviceId deviceId", deviceId);
//            return deviceId;
//        }
//
//        return null;
    }

}
