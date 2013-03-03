package com.github.arturh.justnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class EditNoteActivity extends Activity {
	private final class ConfirmDeletePositiveButtonListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(final DialogInterface dialog, final int which) {
			mNote.delete();
			mNote = null;

			Toast.makeText(EditNoteActivity.this, "note deleted",
					Toast.LENGTH_SHORT).show();

			finish();
		}
	}

	private final class MyTextWatcher implements TextWatcher {
		private boolean isFirst = true;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (isFirst) {
				isFirst = false;
			} else {
				mNeedsSave = true;
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	}

	public static final String EXTRA_NOTE_ID = "EXTRA_NOTE_ID";

	private EditText etNote;
	private Note mNote;
	private boolean mNeedsSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editnote_layout);

		// find views
		etNote = (EditText) findViewById(R.id.etNote);

		// set listeners
		etNote.addTextChangedListener(new MyTextWatcher());

		// fetch data
		final Intent intent = getIntent();
		final Bundle extras = intent.getExtras();
		final String action = intent.getAction();
		final String text = intent.getStringExtra(Intent.EXTRA_TEXT);
		if (Intent.ACTION_SEND.equals(action) && text != null) {
			mNote = new Note();
			mNote.setContent(text);
			mNeedsSave = true;
		} else if (extras != null && extras.containsKey(EXTRA_NOTE_ID)) {
			final long note_id = extras.getLong(EXTRA_NOTE_ID);
			mNote = Note.load(Note.class, note_id);
			mNeedsSave = false;
		} else {
			mNote = new Note();
			mNeedsSave = true;

			Toast.makeText(this, R.string.editnote_toast_new_note,
					Toast.LENGTH_SHORT).show();
		}

		// load data
		final String content = mNote.getContent();
		etNote.setText(content);

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mNote != null && mNeedsSave) {
			final String content = etNote.getText().toString();
			mNote.setContent(content);
			mNote.save();

			Toast.makeText(this, R.string.editnote_toast_notesaved,
					Toast.LENGTH_SHORT).show();
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

			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setMessage("Confirm delete note?")
					.setPositiveButton(android.R.string.yes,
							new ConfirmDeletePositiveButtonListener())
					.setNegativeButton(android.R.string.no, null).show();

			return true;
		} else if (item_id == R.id.editnote_menu_share) {

			final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share note");
			intent.putExtra(android.content.Intent.EXTRA_TEXT, etNote.getText()
					.toString());
			startActivity(intent);

			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
}
