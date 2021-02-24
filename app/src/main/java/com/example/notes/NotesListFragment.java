package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class NotesListFragment extends Fragment implements NoLiNot {

    private List<Observer> noteListObservers;
    private ArrayList<Note> noteList;
    private final NotesAdapter notesAdapter = new NotesAdapter(this::noteClicked);
    private static final String NOTE_LIST = "notes";

    public NotesListFragment() {
        noteListObservers = new ArrayList<>();
        noteList = new ArrayList<>();
    }

    public static NotesListFragment newInstance() {
        return new NotesListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            noteList = bundle.getParcelableArrayList(NOTE_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notesAdapter.setItems(noteList);
    }

    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new NotesDecorator(getResources().getDimensionPixelSize(R.dimen.padding_h)));
        recyclerView.setAdapter(notesAdapter);


    }

    @Override
    public void subscribe(Observer observer) {
        noteListObservers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        noteListObservers.remove(observer);
    }

    @Override
    public void noteClicked(Note note) {
        for (Observer noteListObserver : noteListObservers) {
            noteListObserver.openNote(note);
        }
    }


}