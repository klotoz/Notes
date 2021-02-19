package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class NotesListFragment extends Fragment implements NoLiNot {

    private final List<Observer> noteListObservers;
    private ArrayList<Note> noteList;
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

    private void initList(View view) {
        LinearLayout layout = (LinearLayout) view;
        for (Note note : noteList) {
            TextView textView = new TextView(getContext());
            textView.setText(note.getTitle());
            textView.setTextSize(getResources().getDimension(R.dimen.t_title_size));
            textView.setBackground(getResources().getDrawable(R.drawable.et_style));
            float paddingH = getResources().getDimension(R.dimen.padding_h);
            float paddingV = getResources().getDimension(R.dimen.padding_v);
            textView.setPaddingRelative((int) paddingH, (int) paddingV, (int) paddingH, (int) paddingV);
            layout.addView(textView);

            textView.setOnClickListener(view1 -> noteClicked(note));
        }
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