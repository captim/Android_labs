package it_school.sumdu.edu.localetext;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

    public class HelpActivity extends AppCompatActivity {

        private static final String TAG = HelpActivity.class.getSimpleName();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_help);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(view -> {
                String phoneNumber = getString(R.string.support_phone);
                callSupportCenter(phoneNumber);
            });
        }

        private void callSupportCenter(String phoneNumber) {
            String formattedNumber = String.format("tel: %s", phoneNumber);
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse(formattedNumber));
            if (dialIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(dialIntent);
            } else {
                Log.e(TAG, getString(R.string.dial_log_message));
            }
        }
    }
