package com.example.notes;

public interface NoLiNot {

    void subscribe(Observer observer);

    void unsubscribe(Observer observer);

    void noteClicked(Note note);

}
