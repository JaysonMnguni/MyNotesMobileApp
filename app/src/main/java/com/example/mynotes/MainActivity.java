package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mynotes.helpers.ApiLinksHelper;
import com.example.mynotes.helpers.NoteListRecyclerViewHelper;
import com.example.mynotes.helpers.StringResourceHelper;
import com.example.mynotes.models.Note;
import com.example.mynotes.utils.MyVolleySingletonUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton createNoteBtn;
    private SharedPreferences preferences;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RecyclerView.Adapter adapter;
    TextView displayUsername, txtNoNotes;

    private RequestQueue mRequestQueue;

    private ActionBar actionBar;
    private List<Note> noteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SET / GET PREFERENCES:
        preferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);

        // INITIATE / HOOK VIEW COMPONENTS:
        displayUsername     = findViewById(R.id.display_username);
        txtNoNotes          = findViewById(R.id.no_notes);
        createNoteBtn       = findViewById(R.id.create_note_btn);
        recyclerView        = findViewById(R.id.note_list_recycler_view);
        progressBar         = findViewById(R.id.get_not_progress_bar);

        // INITIALIZE REQUEST QUEUE:
        mRequestQueue =  MyVolleySingletonUtil.getInstance(MainActivity.this).getRequestQueue();

        // CONFIGURE RECYCLER VIEW:
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // SET NOTE LIST OBJECT:
        noteList    = new ArrayList<>();

        getUserNotes();

        setDisplayUsername();

        createNoteFloatingActionButton();

    }
    // END OF MAIN ACTIVITY ON CREATE METHOD.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    // END OF ON CREATE OPTIONS MENU METHOD.


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                goToProfileActivity();
                break;
        }
        // END OF SWITCH STATEMENT.
        return super.onOptionsItemSelected(item);
    }
    // END OF ON OPTIONS ITEM SELECTED.

    public void createNoteFloatingActionButton(){

        createNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateNoteActivity();
            }
            // END ON CLICK METHOD.
        });
        // END OF GO TO CREATE NOTE ON CLICK LISTENER OBJECT.
    }
    // END OF CREATE NOTE FLOATING ACTION BUTTON.
    public void setDisplayUsername(){
        String username
                = preferences.getString("first_name", "") + " " + preferences.getString("last_name", "");
        // SET AUTHENTICATED USERS NAME:
        displayUsername.setText("Welcome: " + username);
    }
    // END OF SET USERNAME DISPLAY.

    public void goToProfileActivity(){
        Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(goToProfile);
        //finish();
    }
    // END OF GO TO PROFILE ACTIVITY INTENT METHOD.

    public void goToCreateNoteActivity(){
        Intent goToCreateNote = new Intent(MainActivity.this, CreateNoteActivity.class);
        startActivity(goToCreateNote);
    }
    // END OF GO TO CREATE NOTE ACTIVITY METHOD.


    public void getUserNotes(){
        // SET USER DATA MAP OBJECT:
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", "email");

        JsonArrayRequest jsonArrayRequest
                = new JsonArrayRequest(Request.Method.GET, ApiLinksHelper.getMyNotesApiUri(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                for (int i = 0; i < response.length(); i++) {

                    try{
                        JSONObject responseObject = response.getJSONObject(i);
                        Note note
                                = new Note(responseObject.getInt("note_id"),
                                responseObject.getString("title"),
                                responseObject.getString("body"));
                        noteList.add(note);

                    }catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                    // END OF TRY BLOCK
                }
                // END OF RESPONSE FOR LOOP.
                adapter = new NoteListRecyclerViewHelper(noteList, MainActivity.this);

                recyclerView.setAdapter(adapter);

            }
            // END OF ON SUCCESS RESPONSE METHOD.
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.setVisibility(View.GONE);
                txtNoNotes.setVisibility(View.VISIBLE);
                Log.i("MainActivity", error.toString());
                Toast.makeText(MainActivity.this, "Failed to get notes", Toast.LENGTH_LONG).show();
            }
            // END OF ON ERROR RESPONSE METHOD.
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token", "");
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                headers.put("content-type", "application/json");
                return headers;
            }
        };
        //  END OF JSON ARRAY REQUEST OBJECT.

        // ADD / RUN REQUEST QUE:
        mRequestQueue.add(jsonArrayRequest);
    }
    // END OF GET USER NOTES METHOD.

}
// END OF MAIN ACTIVITY CLASS.