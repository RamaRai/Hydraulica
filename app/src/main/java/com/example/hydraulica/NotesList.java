package com.example.hydraulica;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class NotesList extends ListActivity implements LoaderCallbacks<Cursor> {

    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;
    // private Cursor cursor;
    private SimpleCursorAdapter adapter;

    // LOGTAG used to log in LogCat for debugging purpose
    public static final String LOGTAG = "Hydraulica2019_RamaRai";

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notes_list);

        //Logging in the LOCat
        Log.v(LOGTAG, "Displaying Notes List");

        this.getListView().setDividerHeight(2);
        //fillData here is to fetch only firstname to fill the listview
        fillData();
        registerForContextMenu(getListView());
    }

    // Create the menu based on the XML defintion
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.listmenu, menu);
        return true;
    }

    // Reaction to the menu selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert:
                Log.v(LOGTAG, "Creating a new record in Database");
                createTodo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Opens the Address Detail activity for the same record clicked in list to edit
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, NotesDetailActivity.class);
        Uri editUri = Uri.parse(HydraulicaContentProvider.CONTENT_URI + "/" + id);
        i.putExtra(HydraulicaContentProvider.CONTENT_ITEM_TYPE, editUri);

        // Activity returns an result if called with startActivityForResult
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    //Long press the List item generates the context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Menu Option Delete Address
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }


    //Action on clicking the Delete Address option from Context Menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Uri uri = Uri.parse(HydraulicaContentProvider.CONTENT_URI + "/" + info.id);
                getContentResolver().delete(uri, null, null);
                Log.v(LOGTAG, "Deleted the Record from Database");

                //Here fillData will only fetch the firstnames from the Database in listview to refresh view after delete
                fillData();

                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createTodo() {
        //Creating intent object to start new activity for Address Details
        Intent i = new Intent(this, NotesDetailActivity.class);
        //Creating a New Record
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    // Called with the result of the other activity
    // requestCode was the origin request code send to the activity
    // resultCode is the return code, 0 is everything is ok
    // intend can be used to get data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    //The function here fetches ONLY name from the data table and fills the list view with it
    private void fillData() {
        // Fields from the database (projection, must include the _id column for the adapter to work
        //Fetching Name from Database Table
        String[] from = new String[] { HydraulicaTableHandler.COLUMN_NAME };
        // Fields on the UI to which we map, to mapped to a field on listview and it is filled by 'from'
        int[] to = new int[] { R.id.label };

        getLoaderManager().initLoader(0, null, NotesList.this);
        adapter = new SimpleCursorAdapter(this, R.layout.notes_row, null, from, to, 0);

        setListAdapter(adapter);
    }

    //Very important methods onLoadFinished and onLoaderReset to populate the list
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { HydraulicaTableHandler.COLUMN_ID, HydraulicaTableHandler.COLUMN_NAME };
        CursorLoader cursorLoader = new CursorLoader(NotesList.this,
                HydraulicaContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }


}
