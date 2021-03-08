package com.example.notes.notelist;

import androidx.annotation.NonNull;

public interface NotesListRepository {

    void requestNotes();

    void onDeleteClicked(@NonNull String id);
}
