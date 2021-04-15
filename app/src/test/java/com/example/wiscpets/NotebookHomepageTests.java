package com.example.wiscpets;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import org.junit.After;
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
public class NotebookHomepageTests {

    private MainActivityNotebookLanding activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivityNotebookLanding.class).create().resume().get();
        //Make sure there's at least one note in the list
        activity.displayNotes.add("Title: Test\nDate: Test");
    }

    @After
    public void tearDown() {
        while(activity.displayNotes.contains("Title:Test\nDate: Test")) {
            activity.displayNotes.remove("Title: Test\nDate: Test");
            activity.displayNotes.remove("Title: asdf\nDate: asdf");
            activity.displayNotes.remove("Title: jfjfjf\nDate: iefijf");
        }
    }

    @Test
    public void pageOpened() {
        assertNotNull(activity);
    }

    @Test
    public void addNoteButtonHitTest() {
        ImageView addNote = activity.findViewById(R.id.addButton);
        addNote.performClick();

        Intent expected = new Intent(activity, ThirdActivity.class);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    @Test
    public void noteClickTest() {

        ListView noteList = activity.findViewById(R.id.ListView);
        noteList.getAdapter().getView(0, null, null).performClick();

        Intent expected = new Intent(activity, ThirdActivity.class);
        expected.putExtra("noteid", 0);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expected.getIntExtra("noteid", -1), actual.getIntExtra("noteid", -2));
    }

    @Test
    public void deleteNoteTest() {
        //Make sure there's at least one note in the list
        activity.displayNotes.add("Title: Test\nDate: Test");
        ListView noteList = activity.findViewById(R.id.ListView);
        int originalLength = activity.displayNotes.size();

        noteList.getAdapter().getView(0, null, null).performLongClick();
        int newLength = activity.displayNotes.size();

        assertNotEquals(originalLength, newLength);
    }

    @Test
    public void emptySearchTest() {
        //Make sure there's at least one note in the list
        activity.displayNotes.add("Title: Test\nDate: Test");
        ListView noteList = activity.findViewById(R.id.ListView);
        int originalSize = noteList.getAdapter().getCount();

        SearchView searchBar = activity.findViewById(R.id.searchBar);
        searchBar.setQuery("", true);
        int newSize = noteList.getAdapter().getCount();

        assertEquals("List size shouldn't change", originalSize, newSize);
    }

    @Test
    public void nonemptySearchPassTest() {
        //Add many notes
        activity.displayNotes.add("Title: Test\nDate: Test");
        activity.displayNotes.add("Title: asdf\nDate: asdf");
        activity.displayNotes.add("Title: jfjfjf\nDate: iefijf");
        ListView noteList = activity.findViewById(R.id.ListView);
        int originalSize = noteList.getAdapter().getCount();

        SearchView searchBar = activity.findViewById(R.id.searchBar);
        searchBar.setQuery("Test", true);
        int newSize = noteList.getAdapter().getCount();

        assertNotEquals("List size should change", originalSize, newSize);
    }

    @Test
    public void nonemptySearchFailTest() {
        //Add many notes
        activity.displayNotes.add("Title: Test\nDate: Test");
        activity.displayNotes.add("Title: asdf\nDate: asdf");
        activity.displayNotes.add("Title: jfjfjf\nDate: iefijf");
        ListView noteList = activity.findViewById(R.id.ListView);

        SearchView searchBar = activity.findViewById(R.id.searchBar);
        searchBar.setQuery("Not present", true);
        int newSize = noteList.getAdapter().getCount();

        assertEquals("Search should return nothing", 0, newSize);
    }
}
