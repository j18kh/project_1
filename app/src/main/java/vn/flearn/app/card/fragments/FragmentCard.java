package vn.flearn.app.card.fragments;


import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import vn.flearn.app.card.R;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.AppUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCard extends Fragment {

    private Word word;
    private boolean isReview;

    public static FragmentCard getInstance(Word word , boolean isReview)
    {
        FragmentCard result = new FragmentCard();
        result.word = word;
        result.isReview = isReview;
        return result;
    }

    public FragmentCard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        ((TextView) view.findViewById(R.id.fragment_card_word)).setText(word.getName());
        ((TextView) view.findViewById(R.id.fragment_card_pronounce)).setText(word.getPronoun());
        ((TextView) view.findViewById(R.id.fragment_card_meaning)).setText(word.getMeaning());
        ((TextView) view.findViewById(R.id.fragment_card_type)).setText(word.getType());
        ((TextView) view.findViewById(R.id.fragment_card_example)).setText(word.getExample());
        ((TextView) view.findViewById(R.id.fragment_card_example_trans)).setText(word.getExampleTrans());
        ImageButton imageButton = ((ImageButton) view.findViewById(R.id.fragment_card_close));
        ((ViewFlipper)view.findViewById(R.id.fragment_card_viewFlipper)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), word.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        if (isReview)
            imageButton.setVisibility(View.GONE);
        ((ImageButton) view.findViewById(R.id.fragment_card_speak)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        AppUtils.getTextToSpeech(getContext()).speak((CharSequence) word.getName()
                                , TextToSpeech.QUEUE_FLUSH, null, null);
                    } else {
                        AppUtils.getTextToSpeech(getContext()).speak(word.getName()
                                , TextToSpeech.QUEUE_FLUSH, null);
                    }
                } catch (Exception exception) {
                    Log.d("debug", exception.getMessage());
                }

            }
        });
        return view;
    }

}
