package com.example.mynotes.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.NoteDetailActivity;
import com.example.mynotes.R;
import com.example.mynotes.models.Note;

import java.util.List;

public class NoteListRecyclerViewHelper extends RecyclerView.Adapter<NoteListRecyclerViewHelper.NoteListViewHolder> {

    private List<Note> noteListItems;
    private Context context;

    public NoteListRecyclerViewHelper(List<Note> noteListItems, Context context){
        this.noteListItems = noteListItems;
        this.context = context;
    }
    // END OF NOTE LIST RECYCLER VIEW HELPER CLASS CONSTRUCTOR.

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list, parent, false);
        return new NoteListViewHolder(v);
    }
    // END OF ON CREATE VIEW HOLDER METHOD.

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder holder, int position) {
        Note note = this.noteListItems.get(position);

        holder.noteId.setText(String.valueOf(note.getNote_id()));
        holder.noteTitle.setText(note.getTitle());
        holder.noteBody.setText(note.getBody());


        holder.noteItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "You clicked: " + note.getNote_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, NoteDetailActivity.class);
                intent.putExtra("note_id", String.valueOf(note.getNote_id()));
                intent.putExtra("note_tile", note.getTitle());
                intent.putExtra("note_body", note.getBody());
                context.startActivity(intent);
            }
        });

    }
    // END OF ON BIND VIEW HOLDER METHOD.

    @Override
    public int getItemCount() {
        return noteListItems.size();
    }
    // END OF GET COUNT ITEM METHOD.

    public class NoteListViewHolder extends RecyclerView.ViewHolder{
        public TextView noteId, noteTitle, noteBody;
        private LinearLayout noteItemLayout;
        public NoteListViewHolder(@NonNull View itemView) {
            super(itemView);
            noteId      = itemView.findViewById(R.id.note_id);
            noteTitle   = itemView.findViewById(R.id.note_title);
            noteBody    = itemView.findViewById(R.id.note_body);
            noteItemLayout = itemView.findViewById(R.id.noteItemLayout);
        }
        // END OF NOTE LIST VIEW HOLDER CONSTRUCTOR.
    }
    // END OF NOTE LIST VIEW HOLDER CLASS.
}
// END OF NOTE LIST RECYCLER VIEW HELPER CLASS.
