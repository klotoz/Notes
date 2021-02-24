package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class NoteFragment extends Fragment implements Observer {

    private static final String NOTE = "note";
    private TextView titleTextView;
    private TextView descTextView;
    private TextView dateTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_note, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTextView = view.findViewById(R.id.tv_title);
        descTextView = view.findViewById(R.id.tv_desc);
        descTextView.setMovementMethod(new ScrollingMovementMethod());
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_setCh) {
            Toast.makeText(getContext(), "Редактируем", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.action_send) {
            Toast.makeText(getContext(), "Отправляем другу", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.action_copy) {
            Toast.makeText(getContext(), "Копируем текст", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.action_delete) {
            Toast.makeText(getContext(), "Удаляем заметку", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}