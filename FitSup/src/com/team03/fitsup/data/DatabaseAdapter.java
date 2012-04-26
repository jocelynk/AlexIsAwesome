package com.team03.fitsup.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DatabaseAdapter {

	private static final String TAG = "Database Adapter";
	private static final boolean DEBUG = true;

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private Context mCtx;

	public DatabaseAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public DatabaseAdapter open() throws SQLException {
		mDbHelper = DatabaseHelper.getInstance(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	// closes the database
	public void close() {
		mDbHelper.close();
	}

	// ++++Attribute Queries++++

	public Cursor fetchAttribute(String name) {
		Cursor mCursor =

		mDb.query(true, AttributeTable.TABLE_ATTR,
				new String[] { AttributeTable.COLUMN_ID },
				AttributeTable.COLUMN_NAME + " = ? ", new String[] { name },
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ++++ExerciseAttribute Queries++++

	public Cursor fetchExerciseAttribute(long a_id, long e_id) {
		Cursor mCursor =

		mDb.query(true, ExerciseAttributeTable.TABLE_EXERCISE_ATTR,
				new String[] { ExerciseAttributeTable.COLUMN_ID },
				ExerciseAttributeTable.COLUMN_ATTR_ID + " = ? AND "
						+ ExerciseAttributeTable.COLUMN_EXERCISE_ID + " = ? ",
				new String[] { String.valueOf(a_id), String.valueOf(e_id) },
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ++++Record Queries++++

	// public Cursor fetchExerciseHistory()
	// {
	// select Record._id, Record.date, Record.value
	//
	// }
	public long createRecord(String date, double value, long exercise_attr_id,
			long wr_e_id, String description) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(RecordTable.COLUMN_DATE, date);
		initialValues.put(RecordTable.COLUMN_VALUE, value);
		initialValues.put(RecordTable.COLUMN_E_ATTR_ID, exercise_attr_id);
		initialValues.put(RecordTable.COLUMN_WRKT_RTNE_E_ID, wr_e_id);
		initialValues.put(RecordTable.COLUMN_DESCRIPTION, description);


		return mDb.insert(RecordTable.TABLE_RECORD, null, initialValues);
	}

	public boolean updateRecord(String date, double value,
			long exercise_attr_id, long wr_e_id, String description) {
		ContentValues args = new ContentValues();
		args.put(RecordTable.COLUMN_DATE, date);
		args.put(RecordTable.COLUMN_VALUE, value);
		args.put(RecordTable.COLUMN_E_ATTR_ID, exercise_attr_id);
		args.put(RecordTable.COLUMN_WRKT_RTNE_E_ID, wr_e_id);
		args.put(RecordTable.COLUMN_DESCRIPTION, description);

		return mDb.update(
				RecordTable.TABLE_RECORD,
				args,
				RecordTable.COLUMN_DATE + " = ? AND "
						+ RecordTable.COLUMN_E_ATTR_ID + " = ? AND "
						+ RecordTable.COLUMN_WRKT_RTNE_E_ID + " = ?",
				new String[] { date, String.valueOf(exercise_attr_id),
						String.valueOf(wr_e_id) }) > 0;
	}

	public Cursor fetchRecord(String date, long exercise_attr_id, long wr_e_id) {

		Cursor mCursor =

		mDb.query(
				true,
				RecordTable.TABLE_RECORD,
				new String[] { RecordTable.COLUMN_ID, RecordTable.COLUMN_VALUE },
				RecordTable.COLUMN_DATE + " = ? AND "
						+ RecordTable.COLUMN_E_ATTR_ID + " = ? AND "
						+ RecordTable.COLUMN_WRKT_RTNE_E_ID + " = ?",
				new String[] { date, String.valueOf(exercise_attr_id),
						String.valueOf(wr_e_id) }, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchAllDatesOfExercise(long wreRowId) {

		return mDb.query(RecordTable.TABLE_RECORD, new String[] {
				RecordTable.COLUMN_ID, RecordTable.COLUMN_DATE },
				RecordTable.COLUMN_WRKT_RTNE_E_ID + " = ? ",
				new String[] { String.valueOf(wreRowId) },
				RecordTable.COLUMN_DATE, null, null);
	}

	public Cursor fetchAttributeOfRecord(long mRowId) {

		String[] tables = new String[] { RecordTable.TABLE_RECORD,
				ExerciseAttributeTable.TABLE_EXERCISE_ATTR,
				AttributeTable.TABLE_ATTR };

		String eQuery = "SELECT Attributes." + AttributeTable.COLUMN_NAME
				+ " FROM " + TextUtils.join(", ", tables) + " WHERE "
				+ "Records." + RecordTable.COLUMN_ID + " = ? AND " + "Records."
				+ RecordTable.COLUMN_E_ATTR_ID + " = " + "ExerciseAttributes."
				+ ExerciseAttributeTable.COLUMN_ID + " AND "
				+ "ExerciseAttributes." + ExerciseAttributeTable.COLUMN_ATTR_ID
				+ " = " + "Attributes." + AttributeTable.COLUMN_ID;

		Cursor mCursor = mDb.rawQuery(eQuery,
				new String[] { String.valueOf(mRowId) });

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchAllRecordAttrByDate(String date, long wreRowId) {

		return mDb.query(RecordTable.TABLE_RECORD, new String[] {
				RecordTable.COLUMN_ID, RecordTable.COLUMN_VALUE, RecordTable.COLUMN_DESCRIPTION },
				RecordTable.COLUMN_WRKT_RTNE_E_ID + " = ? AND "
						+ RecordTable.COLUMN_DATE + " = ? ", new String[] {
						String.valueOf(wreRowId), date }, null, null, null);
	}

	public boolean deleteRecord(String date, long wreRowId) {
		return mDb.delete(RecordTable.TABLE_RECORD, RecordTable.COLUMN_DATE
				+ " = ? AND " + RecordTable.COLUMN_WRKT_RTNE_E_ID + " = ? ",
				new String[] { date, String.valueOf(wreRowId) }) > 0;
	} // if errors check this method

	public boolean deleteRecordsByWRE(long wreRowId) {
		return mDb.delete(RecordTable.TABLE_RECORD,
				RecordTable.COLUMN_WRKT_RTNE_E_ID + " = ? ",
				new String[] { String.valueOf(wreRowId) }) > 0;

	}

	public boolean deleteRecordByWR(long wrRowId) {
		return mDb
				.delete(RecordTable.TABLE_RECORD,
						RecordTable.COLUMN_WRKT_RTNE_E_ID
								+ " IN  (SELECT "
								+ RecordTable.COLUMN_WRKT_RTNE_E_ID
								+ " FROM "
								+ WorkoutRoutineExerciseTable.TABLE_WORKOUTROUTINE_EXERCISE
								+ " JOIN " + RecordTable.TABLE_RECORD
								+ " ON WorkoutRoutineExercises."
								+ WorkoutRoutineExerciseTable.COLUMN_ID
								+ " = Records."
								+ RecordTable.COLUMN_WRKT_RTNE_E_ID + " WHERE "
								+ WorkoutRoutineExerciseTable.COLUMN_WORKOUT_ID
								+ " = ?)",
						new String[] { String.valueOf(wrRowId) }) > 0;

	} // need to figure out what to do with date and how to add either
		// datepicker

	// or calendar, fragments? when you click on date?
	// ++++WorkoutRoutine Queries++++

	public long createWorkout(String name, String description) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(WorkoutRoutineTable.COLUMN_NAME, name);
		initialValues.put(WorkoutRoutineTable.COLUMN_DESCRIPTION, description);

		return mDb.insert(WorkoutRoutineTable.TABLE_WORKOUTROUTINE, null,
				initialValues);
	}

	public boolean deleteWorkout(long rowID) {
		return mDb.delete(WorkoutRoutineTable.TABLE_WORKOUTROUTINE,
				WorkoutRoutineTable.COLUMN_ID + " = " + rowID, null) > 0;
	}

	public Cursor fetchAllWorkouts() {

		return mDb.query(WorkoutRoutineTable.TABLE_WORKOUTROUTINE,
				new String[] { WorkoutRoutineTable.COLUMN_ID,
						WorkoutRoutineTable.COLUMN_NAME,
						WorkoutRoutineTable.COLUMN_DESCRIPTION }, null, null,
				null, null, null);
	}

	public Cursor fetchWorkout(long rowId) throws SQLException {

		Cursor mCursor = mDb.query(true,
				WorkoutRoutineTable.TABLE_WORKOUTROUTINE, new String[] {
						WorkoutRoutineTable.COLUMN_ID,
						WorkoutRoutineTable.COLUMN_NAME,
						WorkoutRoutineTable.COLUMN_DESCRIPTION },
				WorkoutRoutineTable.COLUMN_ID + " = ? ",
				new String[] { String.valueOf(rowId) }, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public boolean updateWorkout(long rowId, String name, String description) {
		ContentValues args = new ContentValues();
		args.put(WorkoutRoutineTable.COLUMN_NAME, name);
		args.put(WorkoutRoutineTable.COLUMN_DESCRIPTION, description);

		return mDb.update(WorkoutRoutineTable.TABLE_WORKOUTROUTINE, args,
				WorkoutRoutineTable.COLUMN_ID + "=" + rowId, null) > 0;
	}

	// ++++WorkoutRoutineExercise Queries++++

	public Cursor fetchExercisebyWRE(long wreId) {
		String eQuery = "SELECT Exercises." + ExerciseTable.COLUMN_NAME
				+ ", WorkoutRoutineExercises."
				+ WorkoutRoutineExerciseTable.COLUMN_EXERCISE_ID + " FROM "
				+ ExerciseTable.TABLE_EXERCISE + " JOIN "
				+ WorkoutRoutineExerciseTable.TABLE_WORKOUTROUTINE_EXERCISE
				+ " ON Exercises." + ExerciseTable.COLUMN_ID
				+ " = WorkoutRoutineExercises."
				+ WorkoutRoutineExerciseTable.COLUMN_EXERCISE_ID
				+ " WHERE WorkoutRoutineExercises."
				+ WorkoutRoutineExerciseTable.COLUMN_ID + " = ?";

		Cursor mCursor = mDb.rawQuery(eQuery,
				new String[] { String.valueOf(wreId) });

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	/*
	 * public Cursor fetchExercisebyWRE(long wreId) { String eQuery =
	 * "SELECT Exercises." + ExerciseTable.COLUMN_NAME +
	 * ", WorkoutRoutineExercises." +
	 * WorkoutRoutineExerciseTable.COLUMN_EXERCISE_ID + " FROM " +
	 * ExerciseTable.TABLE_EXERCISE + " JOIN " +
	 * WorkoutRoutineExerciseTable.TABLE_WORKOUTROUTINE_EXERCISE +
	 * " ON Exercises." + ExerciseTable.COLUMN_ID +
	 * " = WorkoutRoutineExercises." +
	 * WorkoutRoutineExerciseTable.COLUMN_EXERCISE_ID + " JOIN " +
	 * RecordTable.TABLE_RECORD + " ON WorkoutRoutineExercises." +
	 * WorkoutRoutineExerciseTable.COLUMN_ID + " = Records." +
	 * RecordTable.COLUMN_WRKT_RTNE_E_ID + " WHERE " +
	 * RecordTable.COLUMN_WRKT_RTNE_E_ID + " = ? GROUP BY " +
	 * RecordTable.COLUMN_WRKT_RTNE_E_ID;
	 * 
	 * Cursor mCursor = mDb.rawQuery(eQuery, new String[] {
	 * String.valueOf(wreId) });
	 * 
	 * if (mCursor != null) { mCursor.moveToFirst(); } return mCursor; }
	 */

	public boolean deleteExerciseFromWorkout(long mRowId) {
		return mDb.delete(
				WorkoutRoutineExerciseTable.TABLE_WORKOUTROUTINE_EXERCISE,
				WorkoutRoutineExerciseTable.COLUMN_ID + " = " + mRowId, null) > 0;
	}

	public Cursor fetchAllWorkoutExercises(long wRowId) {
		String[] columns = new String[] {
				WorkoutRoutineExerciseTable.COLUMN_ID,
				ExerciseTable.COLUMN_NAME };
		columns[0] = "WorkoutRoutineExercises." + columns[0];
		columns[1] = "Exercises." + columns[1];
		String[] tables = new String[] { ExerciseTable.TABLE_EXERCISE,
				WorkoutRoutineTable.TABLE_WORKOUTROUTINE,
				WorkoutRoutineExerciseTable.TABLE_WORKOUTROUTINE_EXERCISE };

		String eQuery = "SELECT " + TextUtils.join(", ", columns) + " FROM "
				+ TextUtils.join(", ", tables) + " WHERE " + "WorkoutRoutines."
				+ WorkoutRoutineTable.COLUMN_ID + " = "
				+ "WorkoutRoutineExercises."
				+ WorkoutRoutineExerciseTable.COLUMN_WORKOUT_ID + " AND "
				+ "Exercises." + ExerciseTable.COLUMN_ID + " = "
				+ "WorkoutRoutineExercises."
				+ WorkoutRoutineExerciseTable.COLUMN_EXERCISE_ID + " AND "
				+ "WorkoutRoutineExercises."
				+ WorkoutRoutineExerciseTable.COLUMN_WORKOUT_ID + " = ?";
		Cursor mCursor = mDb.rawQuery(eQuery,
				new String[] { String.valueOf(wRowId) });

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchWorkoutExercise(long wRowId, long eRowId)
			throws SQLException {

		String[] columns = new String[] { ExerciseTable.COLUMN_ID,
				ExerciseTable.COLUMN_NAME, ExerciseTable.COLUMN_DESCRIPTION,
				ExerciseTable.COLUMN_CATEGORY };
		String[] tables = new String[] { ExerciseTable.TABLE_EXERCISE,
				WorkoutRoutineTable.TABLE_WORKOUTROUTINE,
				WorkoutRoutineExerciseTable.TABLE_WORKOUTROUTINE_EXERCISE };
		for (int i = 0; i < columns.length; i++) {
			columns[i] = ExerciseTable.TABLE_EXERCISE + "." + columns[i];
		}
		String eQuery = "SELECT " + TextUtils.join(", ", columns) + " FROM "
				+ TextUtils.join(", ", tables) + " WHERE "
				+ ExerciseTable.TABLE_EXERCISE + " = ? AND "
				+ WorkoutRoutineTable.TABLE_WORKOUTROUTINE + " = ?";
		Cursor mCursor = mDb
				.rawQuery(
						eQuery,
						new String[] { String.valueOf(eRowId),
								String.valueOf(wRowId) });
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public long createWorkoutExercise(long wRowId, long eRowId) {
		ContentValues initialValues = new ContentValues();
		initialValues
				.put(WorkoutRoutineExerciseTable.COLUMN_WORKOUT_ID, wRowId);
		initialValues.put(WorkoutRoutineExerciseTable.COLUMN_EXERCISE_ID,
				eRowId);

		return mDb.insert(
				WorkoutRoutineExerciseTable.TABLE_WORKOUTROUTINE_EXERCISE,
				null, initialValues);
	}

	public boolean deleteWorkoutExercise(long wRowId) {
		return mDb.delete(
				WorkoutRoutineExerciseTable.TABLE_WORKOUTROUTINE_EXERCISE,
				WorkoutRoutineExerciseTable.COLUMN_WORKOUT_ID + " = " + wRowId,
				null) > 0;

	}

	/*
	 * public Cursor fetchExerciseIDBYWRE(long wreRowId) { Cursor mCursor = mDb
	 * .query(WorkoutRoutineExerciseTable.TABLE_WORKOUTROUTINE_EXERCISE, new
	 * String[] { WorkoutRoutineExerciseTable.COLUMN_EXERCISE_ID },
	 * WorkoutRoutineExerciseTable.COLUMN_ID + " = ?", new String[] {
	 * String.valueOf(wreRowId) }, null, null, null); if (mCursor != null) {
	 * mCursor.moveToFirst(); } return mCursor; }
	 */

	// gets Id of WorkoutRoutineExercises by WorkoutRoutineId
	public Cursor fetchWRE(long wreId) {
		Cursor mCursor = mDb.query(
				WorkoutRoutineExerciseTable.TABLE_WORKOUTROUTINE_EXERCISE,
				new String[] { WorkoutRoutineExerciseTable.COLUMN_ID },
				WorkoutRoutineExerciseTable.COLUMN_WORKOUT_ID + " = ?",
				new String[] { String.valueOf(wreId) }, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ++++Exercise Queries++++

	public Cursor fetchAllCategories() {

		return mDb.query(ExerciseTable.TABLE_EXERCISE, new String[] {
				ExerciseTable.COLUMN_ID, ExerciseTable.COLUMN_CATEGORY }, null,
				null, ExerciseTable.COLUMN_CATEGORY, null, null);
	}

	public Cursor fetchAllExercisesByCategory(String category) {

		return mDb.query(ExerciseTable.TABLE_EXERCISE, new String[] {
				ExerciseTable.COLUMN_ID, ExerciseTable.COLUMN_NAME },
				ExerciseTable.COLUMN_CATEGORY + " IS ? ",
				new String[] { category }, null, null, null);
	}

}
