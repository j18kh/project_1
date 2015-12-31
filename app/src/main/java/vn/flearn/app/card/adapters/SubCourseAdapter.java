package vn.flearn.app.card.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.flearn.app.card.R;
import vn.flearn.app.card.activities.WordSlideActivity;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.Constant;

/**
 * Created by hkhoi on 12/28/15.
 */
public class SubCourseAdapter extends RecyclerView.Adapter<SubCourseAdapter.SubCourseHolder> {

    private Context context;
    private List<Word> words;
    private List<Integer> subpackage;
    private int sizeLast;
    private String title;

    public SubCourseAdapter(Context context, List<Word> words , String title) {
        this.context = context;
        this.words = words;
        this.title = title;

        resetData(this.words);
    }

    public void resetData(List<Word> words) {
        this.words = words;
        int size = words.size() / Constant.MAX_SUB_COURSE_LIMIT;
        sizeLast = words.size() % Constant.MAX_SUB_COURSE_LIMIT;
        if (sizeLast > 0)
            ++size;
        subpackage = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            subpackage.add(0);
        }
        for (int i = 0; i < words.size(); ++i) {
            if (words.get(i).getColor().equals(Constant.WORD_COLOR_DONE)) {
                int index = i / Constant.MAX_SUB_COURSE_LIMIT;
                int old = subpackage.get(index);
                subpackage.set(index, old + 1);
            }
        }
    }

    @Override
    public SubCourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SubCourseHolder(LayoutInflater.from(context).inflate(R.layout.item_sub_course, parent, false));
    }

    @Override
    public void onBindViewHolder(SubCourseHolder holder, int position) {
        holder.name.setText("Phần " + (position + 1));
        if (position == subpackage.size() - 1 && sizeLast > 0)
            holder.ratio.setText(subpackage.get(position) + "/" + sizeLast);
        else
            holder.ratio.setText(subpackage.get(position) + "/" + Constant.MAX_SUB_COURSE_LIMIT);

        if (position % 2 == 0) {
            holder.relativeLayout.setBackgroundResource(R.color.bg_row_even);
        } else {
            holder.relativeLayout.setBackgroundResource(R.color.bg_row_odd);
        }
    }

    @Override
    public int getItemCount() {

        Log.d("debug", "" + subpackage.size());
        return subpackage.size();
    }

    public class SubCourseHolder extends RecyclerView.ViewHolder {

        TextView name, ratio;
        RelativeLayout relativeLayout;

        public SubCourseHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.item_sub_course_name);
            ratio = (TextView) itemView.findViewById(R.id.item_sub_course_ratio);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_sub_course_relativeLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WordSlideActivity.class);
                    List<Word> pass = new ArrayList<Word>();
                    int start = getAdapterPosition() * Constant.MAX_SUB_COURSE_LIMIT;
                    int end = 0;

                    if (getAdapterPosition() == subpackage.size() - 1) {
                        end = words.size() - 1;
                    } else {
                        end = start + Constant.MAX_SUB_COURSE_LIMIT - 1;
                    }

                    Log.d("debug", "---Package start: " + start);
                    Log.d("debug", "---Package end: " + end);

                    for (int i = start; i <= end; ++i) {
                        Word current = words.get(i);
                        if (current.getColor().equals(Constant.WORD_COLOR_NEUTRAL)) {
                            pass.add(current);
                        }
                    }

                    intent.putParcelableArrayListExtra(Constant.SUBCOURSE_PACKAGE, (ArrayList) pass);
                    intent.putExtra(Constant.SUBTITLE , "Phần " + (getAdapterPosition() + 1));
                    intent.putExtra(Constant.DESCRIPTION, title);
                    intent.putExtra(Constant.REVIEW , false);
                    context.startActivity(intent);
                }
            });
        }
    }
}
