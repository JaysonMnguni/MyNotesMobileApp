package com.example.mynotes.models;

public class Note {

    private int note_id;
    private String title;
    private String body;

    public Note(int note_id, String title, String body) {
        this.note_id = note_id;
        this.title = title;
        this.body = body;
    }

    public Note(String title, String body){
        this.title  = title;
        this.body   = body;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
