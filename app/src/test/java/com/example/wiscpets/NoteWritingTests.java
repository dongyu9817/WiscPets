package com.example.wiscpets;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class NoteWritingTests {

    private ThirdActivity noteWriter;
    private Note note;


    @Before
    public void setup() {
        noteWriter = Robolectric.buildActivity(ThirdActivity.class).create().resume().get();
        note = new Note("Jan 3 21", "username", "Test", "This is a test note");
    }

    @Test
    public void pageLoads() {
        assertNotNull(noteWriter);
    }

    @Test
    public void newNoteSaveButtonHitTest() {
        noteWriter.noteid = -1;
        Button saveButton = noteWriter.findViewById(R.id.button2);
        EditText input = noteWriter.findViewById(R.id.editText4);

        input.setText("This is a test note");
        saveButton.performClick();

        Intent expected = new Intent(noteWriter, MainActivityNotebookLanding.class);
        Intent actual = Shadows.shadowOf(noteWriter).getNextStartedActivity();

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    @Test
    public void oldNoteSaveButtonHitTest() {
        noteWriter.noteid = 1;
        Button saveButton = noteWriter.findViewById(R.id.button2);
        EditText input = noteWriter.findViewById(R.id.editText4);

        input.setText("This is a test note");
        saveButton.performClick();

        Intent expected = new Intent(noteWriter, MainActivityNotebookLanding.class);
        Intent actual = Shadows.shadowOf(noteWriter).getNextStartedActivity();

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    @Test
    public void noteObjectCreatedTest() {
        assertNotNull(note);
    }

    @Test
    public void getNoteDateTest() {
        String date = note.getDate();

        assertEquals("Jan 3 21", date);
    }

    @Test
    public void getNoteUserTest() {
        String username = note.getUsername();

        assertEquals("username", username);
    }

    @Test
    public void getNoteTitleTest() {
        String title = note.getTitle();

        assertEquals("Test", title);
    }

    @Test
    public void getNoteContentTest() {
        String content = note.getContent();

        assertEquals("This is a test note", content);
    }
}
