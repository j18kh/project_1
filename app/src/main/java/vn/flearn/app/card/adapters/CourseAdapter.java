package vn.flearn.app.card.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import vn.flearn.app.card.R;
import vn.flearn.app.card.activities.SubcourseActivity;
import vn.flearn.app.card.models.Course;
import vn.flearn.app.card.utils.Constant;

/**
 * Created by hkhoi on 12/28/15.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {

    private Activity context;
    private List<Course> data;

    public CourseAdapter(Activity context, List<Course> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public CourseAdapter.CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourseHolder(LayoutInflater.from(context).inflate(R.layout.item_course , parent , false));
    }

    @Override
    public void onBindViewHolder(CourseAdapter.CourseHolder holder, int position) {
        Course course = data.get(position);
        holder.name.setText(course.getDescription());
        holder.words.setText(course.getLevel() + " từ");

        if (position % 2 == 0) {
            holder.relativeLayout.setBackgroundResource(R.color.bg_row_even);
        } else {
            holder.relativeLayout.setBackgroundResource(R.color.bg_row_odd);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder {

        TextView name , words;
        RelativeLayout relativeLayout;

        public CourseHolder(View itemView) {
            /* TODO: Flow controlling 2 */
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.item_course_textView_name);
            words = (TextView) itemView.findViewById(R.id.item_course_textview_words);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_course_relativeLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , SubcourseActivity.class);
                    Course course = data.get(getAdapterPosition());
                    intent.putExtra(Constant.NAME, course.getID());
                    intent.putExtra(Constant.DESCRIPTION , course.getDescription());
                    context.startActivity(intent);
                    context.finish();
                }
            });
        }
    }
}
