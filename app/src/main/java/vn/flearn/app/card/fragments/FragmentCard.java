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

    private TextView name;
    private TextView pronounce;
    private TextView meaning;
    private TextView type;
    private TextView example;
    private TextView exampleTrans;
    private ViewFlipper viewFlipper;
    private ImageButton close;
    private ImageButton speak;
    private View view;

    public static FragmentCard getInstance(Word word, boolean isReview) {
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
        view = inflater.inflate(R.layout.fragment_card, container, false);
        initViews();
        setupContents();
        return view;
    }

    private void setupContents() {
        name.setText(word.getName());
        pronounce.setText(word.getPronoun());
        meaning.setText(word.getMeaning());
        type.setText(word.getType());
        example.setText(word.getExample());
        exampleTrans.setText(word.getExampleTrans());
        if (isReview) {
            close.setVisibility(View.GONE);
        }

        speak.setOnClickListener(new View.OnClickListener() {
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

        /* TODO: Add flipper animation */
        viewFlipper.setInAnimation(getContext(), R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(), R.anim.slide_out_left);

        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showNext();
            }
        });
    }

    private void initViews() {
        name = (TextView) view.findViewById(R.id.fragment_card_word);
        pronounce = (TextView) view.findViewById(R.id.fragment_card_pronounce);
        meaning = (TextView) view.findViewById(R.id.fragment_card_meaning);
        type = (TextView) view.findViewById(R.id.fragment_card_type);
        example = (TextView) view.findViewById(R.id.fragment_card_example);
        exampleTrans = (TextView) view.findViewById(R.id.fragment_card_example_trans);
        close = (ImageButton) view.findViewById(R.id.fragment_card_close);
        speak = (ImageButton) view.findViewById(R.id.fragment_card_speak);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.fragment_card_viewFlipper);
    }

}
