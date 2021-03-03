package com.example.notes;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class NoteFragment extends Fragment implements Observer, DatePickerDialog.OnDateSetListener {

    private static final String NOTE = "note";
    private EditText titleEditText;
    private EditText descEditText;
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
        titleEditText = view.findViewById(R.id.et_title);
        descEditText = view.findViewById(R.id.et_desc);
        descEditText.setMovementMethod(new ScrollingMovementMethod());
        dateTextView = view.findViewById(R.id.tv_date);
        Bundle bundle = getArguments();
        if (bundle != null) {
            openNote(bundle.getParcelable(NOTE));
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dateTextView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showDatePicker();
                                            }
                                        }
        );
    }

    @Override
    public void openNote(Note note) {
        titleEditText.setText(note.getTitle());
        descEditText.setText(note.getDesc());
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

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(getActivity(), this, year, month, day).show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = String.format("%s.%s.%s", dayOfMonth, month + 1, year);
        dateTextView.setText(date);
    }
}