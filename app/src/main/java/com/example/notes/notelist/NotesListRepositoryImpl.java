package com.example.notes.notelist;

import androidx.annotation.NonNull;

import com.example.notes.Constants;
import com.example.notes.model.NoteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class NotesListRepositoryImpl implements NotesListRepository {

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final NotesFirestoreCallbacks callbacks;

    public NotesListRepositoryImpl(NotesFirestoreCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void requestNotes() {
        firebaseFirestore
                .collection(Constants.TABLE_NAME_NOTES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult() != null) {
                            List<NoteModel> items = task.getResult().toObjects(NoteModel.class);
                            callbacks.onSuccessNotes(items);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callbacks.onErrorNotes(e.getMessage());
                    }
                });
    }

    @Override
    public void onDeleteClicked(@NonNull String id) {
        firebaseFirestore
                .collection(Constants.TABLE_NAME_NOTES)
                .document(id)
                .delete();
    }
}
