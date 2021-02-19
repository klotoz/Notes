package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Observer {

    private boolean isLandscape;
    private NoteFragment noteFragment;
    private ArrayList<Note> noteList = new ArrayList<>();
    private static final String NOTE_LIST = "notes";
    private static final String NOTE = "note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNoteList();
        NotesListFragment listOfNotesFragment = NotesListFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(NOTE_LIST, noteList);
        listOfNotesFragment.setArguments(bundle);
        noteFragment = new NoteFragment();

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.listFragmentContainer, listOfNotesFragment)
                    .replace(R.id.noteFragmentContainer, noteFragment)
                    .commit();
            listOfNotesFragment.subscribe(noteFragment);
        } else {
            setFragToContainer(listOfNotesFragment);
            listOfNotesFragment.subscribe(this);
        }
    }

    @Override
    public void openNote(Note note) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE, note);
        noteFragment.setArguments(bundle);
        setFragToContainer(noteFragment);
    }

    private void setFragToContainer(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void initNoteList() {
        noteList.add(new Note("Название_1", "Описание_1"));
        noteList.add(new Note("Название_2", "Описание_2 Вот тут будет некрасиво дофига всего потому что я не понимаю, как это все будет выглядеть на экране,  и вообще, приятно хоть что-то написать на русском языке, пусть и с ошибками и опечатками) О! Кажется, моя графомания дает о себе знать))) Лучше бы так с кодом было!"));
        noteList.add(new Note("Название_3", "Описание_3"));
        noteList.add(new Note("Название_4", "Описание_4"));
        noteList.add(new Note("Название_5", "Описание_5"));

    }
}