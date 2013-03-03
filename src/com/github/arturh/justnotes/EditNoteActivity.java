package com.github.arturh.justnotes;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
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
		} else {
			mNote = new Note();
		}

		// load data
		etNote.setText(mNote.getContent());
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		final String content = etNote.getText().toString();
		mNote.setContent(content);
		mNote.save();
	}
}
