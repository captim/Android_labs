package it_school.sumdu.edu.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private final NoteDao noteDao;
    private final LiveData<List<Note>> mAllNotes;

    NoteRepository(Application application){
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        noteDao = db.noteDao();
        mAllNotes = noteDao.getAllNotes();
    }

    LiveData<List<Note>> getAllNotes(){
        return mAllNotes;
    }

    public void insert(Note note){
        new InsertAsyncTask(noteDao).execute(note);
    }

    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void>{

        private final NoteDao mAsyncTaskDao;

        InsertAsyncTask(NoteDao noteDao) {
            mAsyncTaskDao = noteDao;
        }

        @Override
        protected Void doInBackground(final Note... notes) {
            mAsyncTaskDao.insert(notes[0]);
            return null;
        }
    }

}