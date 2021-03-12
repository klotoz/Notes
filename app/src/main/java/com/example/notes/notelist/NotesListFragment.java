package com.example.notes.notelist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notes.notelist.adapter.NoteAdapter;
import com.example.notes.note.NoteFragment;
import com.example.notes.model.NoteModel;
import com.example.notes.notelist.adapter.NoteAdapterCallbacks;
import com.example.notes.notelist.adapter.NoteItemCallback;
import com.example.notes.notelist.adapter.NotesDecorator;
import com.example.notes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class NotesListFragment extends Fragment implements NoteAdapterCallbacks, NotesFirestoreCallbacks, NoteBottomSheetFragment.OnClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;
    private final NoteAdapter adapter = new NoteAdapter(new NoteItemCallback(), this);
    private final List<NoteModel> noteModelList = new ArrayList<>();
    private final NotesListRepository repository = new NotesListRepositoryImpl(this);

    public static NotesListFragment newInstance() {
        return new NotesListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAdd = view.findViewById(R.id.fab_addNote);
        recyclerView = view.findViewById(R.id.rv_notes_list);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.addItemDecoration(new NotesDecorator(getResources().getDimensionPixelSize(R.dimen.padding_h)));
        recyclerView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(null);
            }
        });
        repository.requestNotes();

    }

    @Override
    public void onItemClicked(int position) {
        NoteModel model = noteModelList.get(position);
        replaceFragment(model);
    }

    @Override
    public void onLongItemClicked(int position) {
        NoteModel model = noteModelList.get(position);
        new NoteBottomSheetFragment().create(model).show(getChildFragmentManager(), null);
    }

    public void replaceFragment(@Nullable NoteModel model) {
        Fragment fragment = NoteFragment.newInstance(model);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment)
                .addToBackStack(null)
                .commit();


    }


    @Override
    public void onSuccessNotes(@NonNull List<NoteModel> items) {
        noteModelList.clear();
        noteModelList.addAll(items);
        adapter.submitList(items);
    }

    @Override
    public void onErrorNotes(@Nullable String message) {
        showToast(message);
    }

    private void showToast(@Nullable String message) {
        if (message != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTitleClicked(String title) {
        showToast(title);
    }
}