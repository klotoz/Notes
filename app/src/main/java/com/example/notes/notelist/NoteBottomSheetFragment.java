package com.example.notes.notelist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.model.NoteModel;
import com.example.notes.note.NoteFirestoreCallbacks;
import com.example.notes.note.NoteFragment;
import com.example.notes.note.NoteRepository;
import com.example.notes.note.NoteRepositoryImpl;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class NoteBottomSheetFragment extends BottomSheetDialogFragment implements NoteFirestoreCallbacks {

    private static final String ARG_MODEL_KEY = "arg_model_key";
    private final NoteRepository repository = new NoteRepositoryImpl(this);

    public static BottomSheetDialogFragment create(@Nullable NoteModel model) {
        BottomSheetDialogFragment fragment = new NoteBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_MODEL_KEY, model);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSuccess(@Nullable String message) {

    }

    @Override
    public void onError(@Nullable String message) {

    }

    interface OnClickListener {
        void onTitleClicked(String title);
    }

    private OnClickListener clickListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        clickListener = (OnClickListener) getParentFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputEditText titleDialog = view.findViewById(R.id.tv_dialog_title);
        TextInputEditText descDialog = view.findViewById(R.id.tv_dialog_desc);
        MaterialButton btn_setCh = view.findViewById(R.id.btn_dialog_setCh);
        MaterialButton btn_saveCh = view.findViewById(R.id.btn_dialog_update);

        if (getArguments() != null) {
            NoteModel noteModel = getArguments().getParcelable(ARG_MODEL_KEY);
            if (noteModel != null) {
                titleDialog.setText(noteModel.getTitle());
                descDialog.setText(noteModel.getDesc());
                btn_setCh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        titleDialog.setEnabled(true);
                        descDialog.setEnabled(true);
                    }
                });
                btn_saveCh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        repository.setNote(noteModel.getId(), titleDialog.getText().toString(), noteModel.getDate(), descDialog.getText().toString());


                    }
                });

            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
