package com.team03.fitsup.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.team03.fitsup.R;
import com.team03.fitsup.data.DatabaseAdapter;
import com.team03.fitsup.data.ExerciseTable;
import com.team03.fitsup.data.WorkoutRoutineExerciseTable;
import com.team03.fitsup.data.WorkoutRoutineTable;

public class WorkoutRoutineView extends SherlockListActivity {

	private static final String TAG = "WorkoutRoutineView";
	private static final boolean DEBUG = true;

	private DatabaseAdapter mDbAdapter;
	private TextView mNameText;
	private TextView mDescriptionText;
	private Long mRowId;
	private Long eRowId;

	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_VIEW = 1;
	
	private static final int INSERT_ID = Menu.FIRST;

	// private static final int DELETE_ID = Menu.FIRST + 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workouts_options);

		if (DEBUG)
			Log.v(TAG, "+++ ON CREATE +++");

		mDbAdapter = new DatabaseAdapter(getApplicationContext());
		mDbAdapter.open();

		mNameText = (TextView) findViewById(R.id.name);
		mDescriptionText = (TextView) findViewById(R.id.description);

		mRowId = (savedInstanceState == null) ? null
				: (Long) savedInstanceState
						.getSerializable(WorkoutRoutineTable.COLUMN_ID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras
					.getLong(WorkoutRoutineTable.COLUMN_ID) : null;
		}

		// suggested by alex.
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		
		populateFields();
		fillData();
		registerForContextMenu(getListView());
		
		
		// confirmButton.setOnClickListener(new View.OnClickListener() {

		// public void onClick(View view) {
		// setResult(RESULT_OK);
		// finish();
		// }

		// });
	}

	private void populateFields() {
		if (mRowId != null) {
			Cursor workout = mDbAdapter.fetchWorkout(mRowId);
			startManagingCursor(workout);
			mNameText.setText(workout.getString(workout.getColumnIndexOrThrow(WorkoutRoutineTable.COLUMN_NAME)));
			mDescriptionText.setText(workout.getString(workout.getColumnIndexOrThrow(WorkoutRoutineTable.COLUMN_DESCRIPTION)));
			
			// suggested by alex
			getSupportActionBar().setTitle(mNameText.getText());
			if (!TextUtils.isEmpty(mDescriptionText.getText())) {
				getSupportActionBar().setSubtitle(mDescriptionText.getText());
			}
		}
	}

	private void fillData() {
		Cursor exercisesCursor = mDbAdapter.fetchAllWorkoutExercises(mRowId);
		startManagingCursor(exercisesCursor); // this is deprecated for above
												// 2.3, look up solution later,
												// something Alex said

		String[] from = new String[] { ExerciseTable.COLUMN_NAME };
		int[] to = new int[] { R.id.text1 };

		// Now create an array adapter and set it to display using our row
		SimpleCursorAdapter exercises = new SimpleCursorAdapter(this,
				R.layout.exercises_row, exercisesCursor, from, to);
		setListAdapter(exercises);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// suggested by alex.
		//   changed options menu to XML
		getSupportMenuInflater().inflate(R.menu.workout_view_options_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// suggested by alex.
		//   changed options menu to XML
		switch (item.getItemId()) {
		case R.id.add_exercise:
			createWorkoutExercise();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.wr_exercise_context_menu, menu);
	}

	@Override
	// Change to View Exercise, Add Record, Delete Exercise
	public boolean onContextItemSelected(android.view.MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

		switch (item.getItemId()) {
		case R.id.menu_delete:
			mDbAdapter.deleteExerciseFromWorkout(info.id);
			mDbAdapter.deleteRecordsByWRE(info.id);
			fillData();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	
	public void createWorkoutExercise() {
		Intent i = new Intent(this, WorkoutExerciseEdit.class);
		i.putExtra(WorkoutRoutineTable.COLUMN_ID, mRowId);
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor exercise_id = mDbAdapter.fetchExercisebyWRE(id);
		for (String col : exercise_id.getColumnNames())
			Log.v(TAG, col);

		String name = exercise_id.getString(exercise_id
				.getColumnIndexOrThrow(ExerciseTable.COLUMN_NAME));

		long e_id = exercise_id
				.getLong(exercise_id
						.getColumnIndexOrThrow(WorkoutRoutineExerciseTable.COLUMN_EXERCISE_ID));

		Intent i = new Intent(this, ExerciseRecordUI.class);
		i.putExtra(WorkoutRoutineExerciseTable.COLUMN_ID, id);
		i.putExtra(ExerciseTable.COLUMN_NAME, name);
		i.putExtra(WorkoutRoutineExerciseTable.COLUMN_EXERCISE_ID, e_id);
		startActivityForResult(i, ACTIVITY_VIEW);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		fillData();
	}
}
