package com.example.mynotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mynotes.helpers.ApiLinksHelper;
import com.example.mynotes.helpers.StringResourceHelper;
import com.example.mynotes.utils.MyVolleySingletonUtil;

import java.util.HashMap;
import java.util.Map;

public class NoteDetailActivity extends AppCompatActivity {

    private TextView noteDtlTitle,  noteDtlBody;
    private SharedPreferences preferences;

    private RequestQueue requestQueue;
    private Button deleteNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        // SET / GET PREFERENCES OBJECT:
        preferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);

        // INITIATE REQUEST QUE:
        requestQueue = MyVolleySingletonUtil.getInstance(NoteDetailActivity.this).getRequestQueue();

        // HOOK / INITIATE VIEW ELEMENTS / COMPONENTS:
        noteDtlTitle    = findViewById(R.id.note_dtl_title);
        noteDtlBody     = findViewById(R.id.note_dtl_body);
        deleteNoteBtn   = findViewById(R.id.delete_note_btn);

        // GET INTEND DATA:
        String noteId   = getIntent().getStringExtra("note_id");
        String noteTitle = getIntent().getStringExtra("note_tile");
        String noteBody = getIntent().getStringExtra("note_body");

        // SET VALUES TO VIEW COMPONENTS:
        noteDtlTitle.setText(noteTitle);
        noteDtlBody.setText(noteBody);

        // DELETE NOTE ON CLICK LISTENER OBJECT:
        deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("NoteDetailActivity", "note id is: " + noteId);
                deleteNote(noteId);
            }
            // END OF ON CLICK METHOD.
        });
        // END OF DELETE NOTE ON CLICK LISTENER OBJECT.
    }
    // END OF ON CREATE METHOD.

    public void deleteNote(String note_Id){
        StringRequest request = new StringRequest(Request.Method.POST, ApiLinksHelper.deleteNoteApiUri(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("NoteDetailActivity","Noted Deleted Successfully!");
                Toast.makeText(NoteDetailActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                goToSuccessActivity();
            }
            // END OF ON RESPONSE METHOD.

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("NoteDetailActivity", "Failed to delete note with id of: " + note_Id);
                Toast.makeText(NoteDetailActivity.this, "Failed to delete note", Toast.LENGTH_LONG).show();
            }
            // END OF ON ERROR RESPONSE METHOD.
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("note_id", note_Id);
                return params;
            }
            // END OF GET PARAMS METHOD.

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
               String token = preferences.getString("token", "");
               Map<String, String> headers = new HashMap<>();
               headers.put("Authorization", "Bearer " + token);
               return headers;
            }
            // END OF GET HEADERS METHOD.
        };
        // END OF STRING REQUEST OBJECT.

        // ADD / SEND REQUEST:
        requestQueue.add(request);
    }
    // END OF DELETE NOTE METHOD.

    public void goToSuccessActivity(){
        Intent goToSuccess = new Intent(NoteDetailActivity.this, SuccessActivity.class);
        startActivity(goToSuccess);
        finish();
    }
    // END OF GO TO SUCCESS ACTIVITY METHOD.

}
// END OF NOTE DETAIL ACTIVITY CLASS.