package com.rmt.pispeech;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.drive.Drive;

import java.util.ArrayList;



    public class speechMain extends Activity {

        private static final int REQUEST_CODE = 1234;
        private ListView resultList;
        Button speakButton;
        EditText ipAddress;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_speech_main);

            speakButton = (Button) findViewById(R.id.speakButton);

            resultList = (ListView) findViewById(R.id.listView);
            ipAddress = (EditText) findViewById(R.id.ipAddress);


            // Disable button if no recognition service is present
            PackageManager pm = getPackageManager();
            List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
            if (activities.size() == 0) {
                speakButton.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Recognizer Not Found",
                        Toast.LENGTH_LONG).show();
            }


            speakButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startVoiceRecognitionActivity();
                }
            });

        }

        private void startVoiceRecognitionActivity() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "i hear your voice......");
            startActivityForResult(intent, REQUEST_CODE);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                ArrayList<String> matches = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultList.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, matches));
                Intent i = new Intent(this, speechSender.class);
                i.putExtra("match", matches.get(0));
                i.putExtra("ip", ipAddress.getText().toString());
                startActivity(i);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
