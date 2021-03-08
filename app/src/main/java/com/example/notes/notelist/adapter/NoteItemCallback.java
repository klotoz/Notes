package com.example.notes.notelist.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.notes.model.NoteModel;

public class NoteItemCallback extends DiffUtil.ItemCallback<NoteModel> {



        @Override
        public boolean areItemsTheSame(@NonNull NoteModel oldItem, @NonNull NoteModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull NoteModel oldItem, @NonNull NoteModel newItem) {

                return oldItem.getTitle().equals(newItem.getTitle()) &&
                        oldItem.getDesc().equals(newItem.getDesc()) && oldItem.getDate().equals(oldItem.getDate());
        }
    }

