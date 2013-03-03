package com.github.arturh.justnotes;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class EditNoteActivity extends Activity implements
		OnEditorActionListener {
	public static final String EXTRA_NOTE_ID = "EXTRA_NOTE_ID";

	private EditText etNote;
	private Note mNote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editnote_layout);

		// find views
		etNote = (EditText) findViewById(R.id.etNote);

		// set listeners
		etNote.setOnEditorActionListener(this);

		// fetch data
		final Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(EXTRA_NOTE_ID)) {
			final long note_id = extras.getLong(EXTRA_NOTE_ID);
			mNote = Note.load(Note.class, note_id);
		} else {
			mNote = new Note();
		}

		// load data
		final String content = mNote.getContent();
		etNote.setText(content);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mNote != null) {
			final String content = etNote.getText().toString();
			mNote.setContent(content);
			mNote.save();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editnote, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final int item_id = item.getItemId();
		if (item_id == R.id.editnote_menu_delete) {
			mNote.delete();
			mNote = null;
			finish();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
}
