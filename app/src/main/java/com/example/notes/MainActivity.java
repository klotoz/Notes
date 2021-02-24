package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Observer {


    private static final int SELECT_PICTURE = 1;
    private boolean isLandscape;
    private NoteFragment noteFragment;
    private ArrayList<Note> noteList = new ArrayList<>();
    private static final String NOTE_LIST = "notes";
    private static final String NOTE = "note";
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        initNoteList();
        NotesListFragment listOfNotesFragment = NotesListFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(NOTE_LIST, noteList);
        listOfNotesFragment.setArguments(bundle);
        noteFragment = new NoteFragment();

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.listFragmentContainer, listOfNotesFragment)
                    .replace(R.id.noteFragmentContainer, noteFragment)
                    .commit();
            listOfNotesFragment.subscribe(noteFragment);
        } else {
            addFragment(listOfNotesFragment);
            listOfNotesFragment.subscribe(this);
        }
    }

    @Override
    public void openNote(Note note) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE, note);
        noteFragment.setArguments(bundle);
        addFragment(noteFragment);
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void initNoteList() {
        noteList.add(new Note("Название_1", "Описание_1"));
        noteList.add(new Note("Название_2", "Описание_2 Вот тут будет некрасиво дофига всего потому что я не понимаю, как это все будет выглядеть на экране,  и вообще, приятно хоть что-то написать на русском языке, пусть и с ошибками и опечатками) О! Кажется, моя графомания дает о себе знать))) Лучше бы так с кодом было!Вот тут будет некрасиво дофига всего потому что я не понимаю, как это все будет выглядеть на экране,  и вообще, приятно хоть что-то написать на русском языке, пусть и с ошибками и опечатками) О! Кажется, моя графомания дает о себе знать))) Лучше бы так с кодом было!Вот тут будет некрасиво дофига всего потому что я не понимаю, как это все будет выглядеть на экране,  и вообще, приятно хоть что-то написать на русском языке, пусть и с ошибками и опечатками) О! Кажется, моя графомания дает о себе знать))) Лучше бы так с кодом было!"));
        noteList.add(new Note("Название_3", "Описание_3"));
        noteList.add(new Note("Название_4", "Описание_4"));
        noteList.add(new Note("Название_5", "Описание_5"));

    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        iv = header.findViewById(R.id.set_image);

        iv.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    SELECT_PICTURE);
        }
        );
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (navigateFragment(id)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.set_image:
                Toast.makeText(MainActivity.this, "выбор картинки", Toast.LENGTH_SHORT).show();

            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "настройки", Toast.LENGTH_SHORT).show();
                addFragment(new FragmentAbout());
                return true;
            case R.id.action_add:
                Toast.makeText(MainActivity.this, "Добавляем новую заметку", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite:
                Toast.makeText(MainActivity.this, "Избранное", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (requestCode == SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = imageReturnedIntent.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    iv.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


