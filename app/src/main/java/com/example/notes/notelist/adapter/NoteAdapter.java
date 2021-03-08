package com.example.notes.notelist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.notes.model.NoteModel;
import com.example.notes.R;

public class NoteAdapter extends ListAdapter<NoteModel, NoteViewHolder> {

    private final NoteAdapterCallbacks callbacks;

    public NoteAdapter(
            @NonNull DiffUtil.ItemCallback<NoteModel> diffCallback,
            @NonNull NoteAdapterCallbacks callbacks) {
        super(diffCallback);
        this.callbacks = callbacks;

    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, callbacks);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.onBind(position, getItem(position));
    }


}
