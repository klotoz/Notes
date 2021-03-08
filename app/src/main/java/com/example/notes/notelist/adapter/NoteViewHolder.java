package com.example.notes.notelist.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.model.NoteModel;
import com.example.notes.R;
import com.google.android.material.textview.MaterialTextView;

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView materialTextView;
        private final NoteAdapterCallbacks callbacks;

        public NoteViewHolder(@NonNull View itemView, NoteAdapterCallbacks callbacks) {
            super(itemView);
            materialTextView = itemView.findViewById(R.id.tv_item_title);
            this.callbacks = callbacks;


        }


        public void onBind(int position, NoteModel model) {
            materialTextView.setText(model.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbacks.onItemClicked(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    callbacks.onLongItemClicked(getAdapterPosition());
                    return true;
                }
            });
        }

    }
