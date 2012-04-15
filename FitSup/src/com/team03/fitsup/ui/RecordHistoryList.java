package com.team03.fitsup.ui;

import com.team03.fitsup.R;
import com.team03.fitsup.data.DatabaseAdapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

public class RecordHistoryList extends ListActivity {
//	private static final String TAG = "ExerciseRecordUI";
//	private DatabaseAdapter mDbAdapter;
//	
//	 @Override public void onCreate(Bundle savedInstanceState) {
//	 super.onCreate(savedInstanceState);
//	 setContentView(R.layout.records_by_exercise_index);
//	 
//	  Log.v(TAG, "+++ ON CREATE +++");
//	  
//	  mDbAdapter = new DatabaseAdapter(getApplicationContext());
//	  mDbAdapter.open();
//	  
//	  wreRowId = (savedInstanceState == null) ? null : (Long)
//	 savedInstanceState.getSerializable(WorkoutRoutineExerciseTable.COLUMN_ID); if (wreRowId ==
//	  null) { Bundle extras = getIntent().getExtras(); wreRowId = extras !=
//	 null ? extras .getLong(WorkoutRoutineExerciseTable.COLUMN_ID) : null; }
//	 
//	 fillData(); 
//	 registerForContextMenu(getListView()); 
//	 }
//	 
//	 private void fillData() { // Get all of the notes from the database and
//	 //create the item list 
//	 Cursor recordsCursor =
//	 mDbAdapter.fetchAllDatesOfExercise(wreRowId);
//	 startManagingCursor(recordsCursor);
//	 
//	  String[] from = new String[] { RecordTable.COLUMN_DATE }; int[] to = new
//	  int[] { R.id.text1 };
//	  
//	  // Now create an array adapter and set it to display using our row
//	  SimpleCursorAdapter records = new SimpleCursorAdapter(this,
//	  R.layout.records_row, recordsCursor, from, to); 
//	  setListAdapter(records);
//	  // should i change text1 to something else 
//	  }
//	  
//	 @Override public boolean onCreateOptionsMenu(Menu menu) {
//	 super.onCreateOptionsMenu(menu); menu.add(0, INSERT_ID, 0,
//	 R.string.menu_insert); return true; }
//	 
//	 @Override public boolean onMenuItemSelected(int featureId, MenuItem item)
//	  { switch (item.getItemId()) { case INSERT_ID: createRecord(); return
//	  true; }
//	  
//	  return super.onMenuItemSelected(featureId, item); }
//	  
//	  @Override public void onCreateContextMenu(ContextMenu menu, View v,
//	  ContextMenuInfo menuInfo) { super.onCreateContextMenu(menu, v, menuInfo);
//	  getMenuInflater().inflate(R.menu.record_context_menu, menu); }
//	  
//	  @Override public boolean onContextItemSelected(MenuItem item) {
//	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
//	  .getMenuInfo(); Cursor c = mDbAdapter.fetchAllDatesOfExercise(wreRowId);
//	  startManagingCursor(c); c.moveToPosition(info.position); String date =
//	  c.getString(c .getColumnIndexOrThrow(RecordTable.COLUMN_DATE)); // does
//	  this //Cursor exercise_id = mDbAdapter.fetchExercisebyWRE(wreRowId);
//	  //long e_id = exercise_id.getLong(exercise_id
//	  //.getColumnIndexOrThrow(ExerciseTable.COLUMN_ID)); Cursor exercise_id =
//	  mDbAdapter.fetchExerciseIDBYWRE(wreRowId); long e_id =
//	  exercise_id.getLong
//	  (exercise_id.getColumnIndexOrThrow(WorkoutRoutineExerciseTable
//	  .COLUMN_EXERCISE_ID)); switch (item.getItemId()) { case
//	  R.id.menu_delete_r: mDbAdapter.deleteRecord(date, wreRowId); c.close();
//	  fillData(); return true; case R.id.menu_view_r: Intent i = new
//	  Intent(this, RecordView.class); i.putExtra(RecordTable.COLUMN_ID,
//	  info.id); i.putExtra(RecordTable.COLUMN_WRKT_RTNE_E_ID, wreRowId);
//	  i.putExtra(RecordTable.COLUMN_DATE, date);
//	  i.putExtra(ExerciseTable.COLUMN_ID, e_id); c.close();
//	  startActivityForResult(i, ACTIVITY_VIEW);
//	  
//	  /case R.id.menu_edit_r: Log.v(TAG, "boahboah"); Intent j = new
//	  Intent(this, ExerciseRecordEdit.class); j.putExtra(RecordTable.COLUMN_ID,
//	  info.id); j.putExtra(ExerciseTable.COLUMN_ID, e_id); c.close();
//	  
//	  startActivityForResult(j, ACTIVITY_EDIT); return true; } return
//	  super.onContextItemSelected(item); }
//	  
//	  public void createRecord() { Cursor exercise_id =
//	  mDbAdapter.fetchExerciseIDBYWRE(wreRowId); long e_id =
//	  exercise_id.getLong
//	  (exercise_id.getColumnIndexOrThrow(WorkoutRoutineExerciseTable
//	  .COLUMN_EXERCISE_ID)); Intent i = new Intent(this,
//	  ExerciseRecordEdit.class); i.putExtra(RecordTable.COLUMN_WRKT_RTNE_E_ID,
//	  wreRowId); i.putExtra(ExerciseTable.COLUMN_ID, e_id);
//	  startActivityForResult(i, ACTIVITY_CREATE); }
//	  
//	  @Override protected void onActivityResult(int requestCode, int
//	  resultCode, Intent intent) { super.onActivityResult(requestCode,
//	  resultCode, intent); fillData(); }
	 
}
