package com.example.notes.note;

import androidx.annotation.NonNull;

import java.util.Date;

public interface NoteRepository {

    void setNote(
            @NonNull String id,
            @NonNull String title,
            @NonNull Date date,
            @NonNull String desc
    );

    void onDeleteClicked(@NonNull String id);
}
