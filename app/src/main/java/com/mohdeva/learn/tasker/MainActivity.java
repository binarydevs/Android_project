package com.mohdeva.learn.tasker;import java.util.ArrayList;import java.util.Arrays;import java.util.List;import java.util.Locale;import android.content.ActivityNotFoundException;import android.content.Intent;import android.os.Bundle;import android.speech.RecognizerIntent;import android.support.v7.app.AppCompatActivity;import android.view.Gravity;import android.view.View;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.EditText;import android.widget.ImageButton;import android.widget.ListView;import android.widget.TextView;import android.widget.Toast;import java.util.ArrayList;import java.util.Arrays;import android.app.AlertDialog;import android.app.ListActivity;import android.content.Context;import android.content.DialogInterface;import android.widget.AdapterView;import android.widget.ArrayAdapter;import android.widget.AdapterView.OnItemClickListener;import android.widget.AdapterView.OnItemLongClickListener;public class MainActivity extends AppCompatActivity {    private ImageButton btnSpeak;    private EditText recSpeak;    private final int REQ_CODE_SPEECH_INPUT = 100;    private ListView lv;    private Button btn;    private int iterator = 0; //for tasks    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        recSpeak = (EditText) findViewById(R.id.TEXT);        btnSpeak = (ImageButton) findViewById(R.id.Speech);        btnSpeak.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                promptSpeechInput();            }        });        //adding listview        btn = (Button) findViewById(R.id.ADD);        lv = (ListView) findViewById(R.id.list);        // Initializing a new String Array        //to get from db        String[] tasks = new String[] {        };        // Create a List from String Array elements        final List<String> tasks_list = new ArrayList<String>(Arrays.asList(tasks));        // Create an ArrayAdapter from List        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>                (this, android.R.layout.simple_list_item_1, tasks_list);        // DataBind ListView with items from ArrayAdapter        lv.setAdapter(arrayAdapter);        btn.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                // Add new Items to List                String temp=recSpeak.getText().toString();                if(temp != null && !temp.isEmpty()) {                    tasks_list.add(temp);                    recSpeak.setText("");                /*                    notifyDataSetChanged ()                        Notifies the attached observers that the underlying                        data has been changed and any View reflecting the                        data set should refresh itself.                 */                    arrayAdapter.notifyDataSetChanged();                }                else{                    Toast.makeText(MainActivity.this,                            "No Task !", Toast.LENGTH_LONG).show();                }            }        });        // React to user clicks on item        // LongClick        lv.setOnItemLongClickListener(new OnItemLongClickListener() {            @Override            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {                String item = tasks_list.get(position);                tasks_list.remove(position);                arrayAdapter.notifyDataSetChanged();                Toast.makeText(MainActivity.this, "You Deleted : " + item, Toast.LENGTH_SHORT).show();                return true;            }        });//        short click//        lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {//            @Override//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//                String item = tasks_list.get(position);//                tasks_list.remove(position);//                arrayAdapter.notifyDataSetChanged();//                Toast.makeText(MainActivity.this, "You Deleted : " + item, Toast.LENGTH_SHORT).show();//            }//        });    }    //SPEECH STARTS HERE    //Ask Mohnish if any doubts    // Showing google speech input dialog    private void promptSpeechInput() {        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,                "Hi speak something");        try {            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);        } catch (ActivityNotFoundException a) {        }    }    // Receiving speech input    @Override    protected void onActivityResult(int requestCode, int resultCode, Intent data) {        super.onActivityResult(requestCode, resultCode, data);        switch (requestCode) {            case REQ_CODE_SPEECH_INPUT: {                if (resultCode == RESULT_OK && null != data) {                    ArrayList<String> result = data                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);                    recSpeak.setText(result.get(0));                }                break;            }        }    }}