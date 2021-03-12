package com.example.notes.note;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.notes.Constants;
import com.example.notes.model.NoteModel;
import com.example.notes.R;
import com.example.notes.notelist.NotesListFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.content.Context.*;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static androidx.core.content.ContextCompat.getSystemService;


public class NoteFragment extends Fragment implements NoteFirestoreCallbacks {

    private static final String ARG_MODEL_KEY = "arg_model_key";

    public static Fragment newInstance(@Nullable NoteModel model) {
        Fragment fragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_MODEL_KEY, model);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String deleteId = "";
    private final NoteRepository repository = new NoteRepositoryImpl(this);
    private EditText editTitle;
    private EditText editDesc;
    private TextView editDate;
    private MaterialButton btnUpdate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTitle = view.findViewById(R.id.et_note_title);
        editDesc = view.findViewById(R.id.et_note_desc);
        editDate = view.findViewById(R.id.tv_note_date);
        btnUpdate = view.findViewById(R.id.btn_note_update);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        final String title = editTitle.getText().toString();
        final String desc = editDesc.getText().toString();
        update(title, desc);
            }
        });
        if (getArguments() != null) {
            NoteModel noteModel = getArguments().getParcelable(ARG_MODEL_KEY);


            if(noteModel != null){
                deleteId = noteModel.getId();
                editTitle.setText(noteModel.getTitle());
                editDesc.setText(noteModel.getDesc());
                editDate.setText(noteModel.getDate().toString());
            }

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_update) {
            final String title = editTitle.getText().toString();
            final String desc = editDesc.getText().toString();
            update(title, desc);
            return true;
        }
        if(item.getItemId() == R.id.action_copy){
            showToastMessage("Копируем");
        }
        if(item.getItemId() == R.id.action_send){
            showToastMessage("Пересылаем");
        }

        if (item.getItemId() == R.id.action_delete) {
            showAlertDialog();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

        public void update(
            @Nullable String title,
            @Nullable String desc) {
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc)) {
            if (getArguments() != null) {
                NoteModel noteModel = getArguments().getParcelable(ARG_MODEL_KEY);
                if (noteModel != null) {
                    repository.setNote(noteModel.getId(), title, noteModel.getDate(), desc);
                } else {
                    String id = UUID.randomUUID().toString();
                    Date date = new Date();
                    repository.setNote(id, title, date, desc);
                }
            }
        } else {
            showToastMessage("Поля не могут быть пустые");
        }

    }

    @Override
    public void onSuccess(@Nullable String message) {
        showToastMessage(message);
    }

    @Override
    public void onError(@Nullable String message) {
        showToastMessage(message);
    }

    private void showToastMessage(@Nullable String message) {
        if (message != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showAlertDialog(){
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.ad_title)
                .setMessage(R.string.ad_desc)
                .setPositiveButton(R.string.ad_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repository.onDeleteClicked(deleteId);
                        if (getActivity() != null) {
                            getActivity().onBackPressed();
                        }
                    }
                })
                .create()
                .show();
    }



}