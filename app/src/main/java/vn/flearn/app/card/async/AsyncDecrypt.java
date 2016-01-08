package vn.flearn.app.card.async;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import vn.flearn.app.card.R;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.SecretKey;

/**
 * Created by huytr on 08-01-2016.
 */
public class AsyncDecrypt extends AsyncTask<Void , Integer , Void> {

    private TextView[] widgets = new TextView[5];
    private String[] results = new String[5];
    private Word word;
    private String text;
    private String name;
    private Activity activity;
    private Spannable span;

    public AsyncDecrypt(TextView name ,
                        TextView pronoun ,
                        TextView meaning ,
                        TextView example ,
                        TextView exampleTrans ,
                        Word word ,
                        Activity activity) {
        widgets[0] = name;
        widgets[1] = pronoun;
        widgets[2] = meaning;
        widgets[3] = example;
        widgets[4] = exampleTrans;
        this.activity = activity;
        this.word = word;
    }

    @Override
    protected Void doInBackground(Void... params) {
        long start = System.currentTimeMillis();
        results[0] = SecretKey.decrypt(word.getName());
        publishProgress(0);
        results[1] = SecretKey.decrypt(word.getPronoun());
        publishProgress(1);
        results[2] = SecretKey.decrypt(word.getMeaning());
        publishProgress(2);
        results[3] = SecretKey.decrypt(word.getExample());
        span = editTextWordSpan(results[3], results[0]);
        publishProgress(3);
        results[4] = SecretKey.decrypt(word.getExampleTrans());
        publishProgress(4);
        Log.d("debug", "Background = " + (System.currentTimeMillis() - start) + "ms");
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (values[0].intValue() != 3)
            widgets[values[0].intValue()].setText(results[values[0].intValue()]);
        else
            widgets[values[0].intValue()].setText(span);
    }

    private Spannable editTextWordSpan(String text, String wordSpan) {
        String example = "Ex: " + text;
        String exampleLower = example.toLowerCase();
        String wordSpanLower = wordSpan.toLowerCase();
        int startIndex = exampleLower.indexOf(wordSpanLower);
        while (startIndex == -1) {
            wordSpanLower = wordSpanLower.substring(0, wordSpanLower.length() - 1);
            startIndex = example.indexOf(wordSpanLower);
        }
        int stopIndex = startIndex + wordSpan.length();
        while (stopIndex < example.length() && example.charAt(stopIndex) != ' ') {
            stopIndex++;
        }
        //use a loop to change text color
        Spannable wordtoSpan = new SpannableString(example);
        wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), startIndex, stopIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.text_grey)),
                0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Log.d("debug", "---Text = " + text);
        Log.d("debug", "---WordSpan = " + wordSpan);
        return wordtoSpan;
    }
}
