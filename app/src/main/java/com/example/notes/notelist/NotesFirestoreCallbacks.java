package com.example.notes.notelist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.model.NoteModel;

import java.util.List;

public interface NotesFirestoreCallbacks {

    void onSuccessNotes(@NonNull List<NoteModel> items);
    void onErrorNotes(@Nullable String message);
}
