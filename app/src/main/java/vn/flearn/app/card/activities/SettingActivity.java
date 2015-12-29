package vn.flearn.app.card.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import vn.flearn.app.card.R;
import vn.flearn.app.card.utils.AppUtils;
import vn.flearn.app.card.utils.Constant;

public class SettingActivity extends AppCompatActivity {

    private CheckBox checkBox;
    private boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initViews();
        sound = AppUtils.getBooleanPreference(getApplication(), Constant.SOUND, true);
        checkBox.setChecked(sound);

        setupCheckBox();
    }

    private void setupCheckBox() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppUtils.setBooleanPreference(getApplication(), Constant.SOUND, isChecked);
            }
        });
    }

    private void initViews() {
        checkBox = (CheckBox) findViewById(R.id.activity_setting_checkbox_sound);
    }
}
