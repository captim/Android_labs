package it_school.sumdu.edu.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String title;

    @NonNull
    private String content;


    public Note(int id, @NonNull  String title, @NonNull String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }


    @NonNull
    public String getContent() {
        return content;
    }


    public void setContent(@NonNull String content) {
        this.content = content;
    }
}