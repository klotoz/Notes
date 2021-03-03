package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final List<Note> noteList = new ArrayList<>();
    private final Observer observer;
    private final Fragment fragment;

    public NotesAdapter(Observer observer, Fragment fragment) {
        this.observer = observer;
        this.fragment = fragment;
    }


    public void setItems(List<Note> items) {
        noteList.clear();
        noteList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notelist, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.onBind(noteList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }


    class NotesViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView textView;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_title);
            registerContextMenu(itemView);
        }

        public void onBind(Note model, int position) {
            textView.setText(model.getTitle());
            itemView.setOnClickListener(v -> observer.openNote(model));

        }
    }

    private void registerContextMenu(View itemView) {
        if (fragment != null) {
            fragment.registerForContextMenu(itemView);
        }
    }


}
