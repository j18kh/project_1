package vn.flearn.app.card.fragments;


import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
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
import vn.flearn.app.card.adapters.WordSlideAdapter;
import vn.flearn.app.card.animation.AnimationFactory;
import vn.flearn.app.card.async.AsyncDecrypt;
import vn.flearn.app.card.async.AsyncUpdateColor;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.AppUtils;
import vn.flearn.app.card.utils.Constant;
import vn.flearn.app.card.utils.OnSwipeTouchListener;
import vn.flearn.app.card.utils.SoundManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordFragment extends Fragment {

    private Word word;
    private WordSlideAdapter adapter;
    private boolean isReview;
    private TextToSpeech textToSpeech;
    private boolean isDone;
    private Toast upToast;
    private Toast downToast;

    public static WordFragment getInstance(WordSlideAdapter wordSlideAdapter,
                                           Word word,
                                           boolean isReview) {
        WordFragment wordFragment = new WordFragment();
        wordFragment.word = word;
        wordFragment.adapter = wordSlideAdapter;
        wordFragment.isReview = isReview;
        wordFragment.textToSpeech = wordSlideAdapter.getTextToSpeech();
        wordFragment.isDone = wordSlideAdapter.getIsDone();
        return wordFragment;
    }

    public WordFragment() {
        // Required empty public constructor
    }

    private void setupToasts() {
        upToast = Toast.makeText(getContext(), R.string.word_done, Toast.LENGTH_SHORT);
        upToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 200);
        downToast = Toast.makeText(getContext(), R.string.word_difficult, Toast.LENGTH_SHORT);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        long start = System.currentTimeMillis();
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        setupToasts();

        //  Get Elements
        final TextView name = (TextView) view.findViewById(R.id.fragment_card_word);
        final TextView pronounce = (TextView) view.findViewById(R.id.fragment_card_pronounce);
        final TextView meaning = (TextView) view.findViewById(R.id.fragment_card_meaning);
        final TextView type = (TextView) view.findViewById(R.id.fragment_card_type);
        final TextView example = (TextView) view.findViewById(R.id.fragment_card_example);
        final TextView exampleTrans = (TextView) view.findViewById(R.id.fragment_card_example_trans);
        final ImageButton close = (ImageButton) view.findViewById(R.id.fragment_card_close);
        final ImageButton speak = (ImageButton) view.findViewById(R.id.fragment_card_speak);
        final ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.fragment_card_viewFlipper);


        //  Bind Elements
        type.setText(word.getType());
        //(new AsyncDecrypt(name , pronounce , meaning , example , exampleTrans , word , getActivity())).execute();

        viewFlipper.setOnTouchListener(new OnSwipeTouchListener() {
            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
                if (isReview)
                    return;
                AppUtils.setBooleanPreference(getContext(), Constant.WORD_DONE, true);
                word.setColor(Constant.WORD_COLOR_DONE);

                Animation upAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_top);
                upAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        /* TODO: Set up toast */
                        upToast.show();
                        (new AsyncUpdateColor(getContext(), word.getID(), Constant.WORD_COLOR_DONE)).execute();
                        int current = AppUtils.getIntegerPreference(getContext(), Constant.COUNT_DONE, 0);
                        AppUtils.setIntegerPreference(getContext(), Constant.COUNT_DONE, current + 1);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.d("debug", "---End animation");
                        /* TODO: Card swipe top */
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                viewFlipper.startAnimation(upAnimation);
                viewFlipper.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.removePage();
                    }
                }, 350);
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
                if (isReview) {
                    return;
                }
                AppUtils.setBooleanPreference(getContext(), Constant.WORD_DONE, true);
                word.setColor(Constant.WORD_COLOR_DIFFICULT);

                Animation upAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom);
                upAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        downToast.show();
                        int current = AppUtils.getIntegerPreference(getContext(), Constant.COUNT_DIFFICULT, 0);
                        (new AsyncUpdateColor(getContext(), word.getID(), Constant.WORD_COLOR_DIFFICULT)).execute();
                        AppUtils.setIntegerPreference(getContext(), Constant.COUNT_DIFFICULT, current + 1);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.d("debug", "---End animation");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                viewFlipper.startAnimation(upAnimation);
                viewFlipper.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.removePage();
                    }
                }, 350);
            }

            @Override
            public void onTap() {
                super.onTap();
                if (AppUtils.getBooleanPreference(getContext(), Constant.SOUND, true)) {
                    SoundManager sound = SoundManager.getInstance(getContext());
                    sound.play(R.raw.page_flip_side);
                }
                viewFlipper.showNext();
            }
        });

        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.RIGHT_LEFT,
                Constant.FLIP_TRANSITION_DURATION);

        if (!isReview) {
            close.setVisibility(View.GONE);
        } else {
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation dismiss = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout_dialog);
                    dismiss.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            (new AsyncUpdateColor(getContext(), word.getID(), Constant.WORD_COLOR_NEUTRAL)).execute();
                            if (isDone) {
                                int current = AppUtils.getIntegerPreference(getContext(), Constant.COUNT_DONE, 0);
                                AppUtils.setIntegerPreference(getContext(), Constant.COUNT_DONE, current - 1);
                            } else {
                                int current = AppUtils.getIntegerPreference(getContext(), Constant.COUNT_DIFFICULT, 0);
                                AppUtils.setIntegerPreference(getContext(), Constant.COUNT_DIFFICULT, current - 1);

                            }
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    viewFlipper.startAnimation(dismiss);
                    viewFlipper.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.removePage();
                        }
                    }, 400);
                }
            });


        }

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    /* TODO: Speak that goddamn word!!! */
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeech.speak((CharSequence) word.getName()
                                , TextToSpeech.QUEUE_FLUSH, null, null);
                    } else {
                        textToSpeech.speak(word.getName()
                                , TextToSpeech.QUEUE_FLUSH, null);
                    }
                    if (!textToSpeech.isSpeaking()) {
                        Toast.makeText(getContext(), R.string.please_wait, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception exception) {
                    Toast.makeText(getContext(), R.string.error_tts, Toast.LENGTH_SHORT).show();
                    Log.d("debug", exception.getMessage());
                }

            }
        });
        Log.d("debug" , "Create Fragment = " + (System.currentTimeMillis() - start) + "ms");
        return view;
    }


}
