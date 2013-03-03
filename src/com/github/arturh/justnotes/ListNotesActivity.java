package com.github.arturh.justnotes;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListNotesActivity extends Activity implements OnItemClickListener {

	private ListView lvNotes;
	private List<Note> mNotes;
	private ArrayAdapter<Note> mAdapter;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listnotes_layout);

		// find views
		lvNotes = (ListView) findViewById(R.id.lvNotes);

		// setListeners
		lvNotes.setOnItemClickListener(this);

		// fetch data
		mNotes = new Select().from(Note.class).execute();

		// load data
		mAdapter = new ArrayAdapter<Note>(this,
				android.R.layout.simple_list_item_1, mNotes);
		lvNotes.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.listnotes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final int item_id = item.getItemId();
		if (item_id == R.id.listnotes_menu_new) {
			final Intent intent = new Intent(this, EditNoteActivity.class);
			startActivity(intent);
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if (parent == lvNotes) {
			// TODO
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		mAdapter.notifyDataSetChanged();
	}
}
