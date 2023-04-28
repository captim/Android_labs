package it_school.sumdu.edu.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
abstract class NoteRoomDatabase extends RoomDatabase {

    abstract NoteDao noteDao();

    private static NoteRoomDatabase INSTANCE;

    static NoteRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NoteRoomDatabase.class, "note_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback;

    static {
        sRoomDatabaseCallback = new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                db.delete("note_table", null, null);
                new PopulateDbAsync(INSTANCE).execute();
            }
        };
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final NoteDao mDao;
        String title = "Title example";
        String content = "Text example";

        PopulateDbAsync(NoteRoomDatabase db) {
            mDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            Note note = new Note(0, title, content);
            mDao.insert(note);
            return null;
        }
    }
}
