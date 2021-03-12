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
import com.example.notes.note.NoteFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NoteBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_MODEL_KEY = "arg_model_key";

    public static BottomSheetDialogFragment create(@Nullable NoteModel model) {
        BottomSheetDialogFragment fragment = new NoteBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_MODEL_KEY, model);
        fragment.setArguments(bundle);
        return fragment;
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
        TextView titleDialog = view.findViewById(R.id.tv_dialog_title);
        TextView descDialog = view.findViewById(R.id.tv_dialog_desc);

        if (getArguments() != null) {
            NoteModel noteModel = getArguments().getParcelable(ARG_MODEL_KEY);
            if (noteModel != null) {
                titleDialog.setText(noteModel.getTitle());
                descDialog.setText(noteModel.getDesc());
                titleDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onTitleClicked(titleDialog.getText().toString());
                        dismiss();
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
