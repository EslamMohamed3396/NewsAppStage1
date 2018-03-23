package com.example.eslam.newsappstage1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public static class NewsAppPerefernceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);

            Preference chooseBy = findPreference(getString(R.string.chooseBy_Key));
            bindPreferenceSummaryToValue(chooseBy);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            String stringValue = newValue.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefindex = listPreference.findIndexOfValue(stringValue);
                if (prefindex > 0) {
                    CharSequence[] lable = listPreference.getEntries();
                    preference.setSummary(lable[prefindex]);
                } else {
                    preference.setSummary(stringValue);
                }

            }

            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());

            String preferenceString = preferences.getString(preference.getKey(), "");

            onPreferenceChange(preference, preferenceString);

        }
    }
}
