package vn.flearn.app.card.fragments;


import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import vn.flearn.app.card.R;
import vn.flearn.app.card.adapters.CardAdapter;
import vn.flearn.app.card.async.AsyncDatabase;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.AppUtils;
import vn.flearn.app.card.utils.Constant;
import vn.flearn.app.card.utils.OnSwipeTouchListener;

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
    private ViewPager viewPager;
    private int position;

    public static FragmentCard getInstance(Word word, boolean isReview, ViewPager viewPager,
                                           int position) {
        FragmentCard result = new FragmentCard();
        result.word = word;
        result.isReview = isReview;
        result.viewPager = viewPager;
        result.position = position;
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
        if (!isReview) {
            close.setVisibility(View.GONE);
        }
        else {
            /* TODO: RETURN NEUTRAL WORD */
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation dismiss = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout_dialog);

                    dismiss.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            try {
                                CardAdapter cardAdapter = (CardAdapter) viewPager.getAdapter();
                                cardAdapter.removePage(position);
                            } catch (Exception e) {
                                Log.d("debug", "---ERROR: " + e.getMessage() + " || " + e.getCause());
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    viewFlipper.startAnimation(dismiss);
                }
            });
        }

        /* TODO: Playing around with Card */
        viewFlipper.setOnTouchListener(new OnSwipeTouchListener() {
            @Override
            public void onSwipeTop() {
                Log.d("debug", "Is review: " + isReview);

                if (isReview) {
                    return;
                }
                AppUtils.setBooleanPreference(getContext(), Constant.WORD_DONE, true);
                word.setColor(Constant.WORD_COLOR_DONE);

                Animation upAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_top);
                upAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        Toast.makeText(getContext(), "Đã thuộc :)", Toast.LENGTH_SHORT).show();
                        (new AsyncDatabase(getContext() , word.getID() , Constant.WORD_COLOR_DONE)).execute();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.d("debug", "---End animation");
                        CardAdapter cardAdapter = (CardAdapter) viewPager.getAdapter();
                        cardAdapter.removePage(position);
                        /* TODO: Card swipe top */
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                viewFlipper.startAnimation(upAnimation);
                super.onSwipeTop();
            }

            @Override
            public void onSwipeBottom() {
                Log.d("debug", "Is review: " + isReview);

                if (isReview) {
                    return;
                }
                AppUtils.setBooleanPreference(getContext(), Constant.WORD_DONE, true);
                word.setColor(Constant.WORD_COLOR_DIFFICULT);

                Animation upAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom);
                upAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        Toast.makeText(getContext(), "Khó thuộc :(", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.d("debug", "---End animation");
                        CardAdapter cardAdapter = (CardAdapter) viewPager.getAdapter();
                        cardAdapter.removePage(position);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                viewFlipper.startAnimation(upAnimation);
                super.onSwipeBottom();
            }

            @Override
            public void onTap() {
                super.onTap();
                viewFlipper.showNext();
                Log.d("debug", "--Tap tap mofo!");
            }
        });


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
