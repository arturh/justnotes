package com.github.arturh.justnotes;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;

public class ListNotesActivity extends Activity implements OnItemClickListener {

	private final class NotesAdapter extends BaseAdapter {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = mLayoutInflater.inflate(R.layout.listnotes_item,
					null);
			final TextView tvTitle = (TextView) view
					.findViewById(R.id.tvTitle);
			tvTitle.setText(getItem(position).toString());
			return view;
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).getId();
		}

		@Override
		public Note getItem(int position) {
			return mNotes.get(position);
		}

		@Override
		public int getCount() {
			return mNotes.size();
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}
	}

	private ListView lvNotes;
	private List<Note> mNotes;
	private BaseAdapter mAdapter;
	private LayoutInflater mLayoutInflater;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listnotes_layout);

		// find views
		lvNotes = (ListView) findViewById(R.id.lvNotes);

		// setListeners
		lvNotes.setOnItemClickListener(this);

		// fetch data
		mNotes = queryNotes();

		// load data
		mLayoutInflater = LayoutInflater.from(this);

		mAdapter = new NotesAdapter();
		lvNotes.setAdapter(mAdapter);
	}

	private List<Note> queryNotes() {
		return new Select().from(Note.class).execute();
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

		mNotes = queryNotes();
		mAdapter.notifyDataSetChanged();
	}
}
