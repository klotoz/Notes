package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NoteFragment extends Fragment implements Observer {

    private static final String NOTE = "note";
    private TextView titleTextView;
    private TextView descTextView;
    private TextView dateTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTextView = view.findViewById(R.id.tv_title);
        descTextView = view.findViewById(R.id.tv_desc);
        dateTextView = view.findViewById(R.id.tv_date);
        Bundle bundle = getArguments();
        if (bundle != null) {
            openNote(bundle.getParcelable(NOTE));
        }
    }

    @Override
    public void openNote(Note note) {
        titleTextView.setText(note.getTitle());
        descTextView.setText(note.getDesc());
        dateTextView.setText(note.getDate());
    }
}