package com.team03.fitsup.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "FitSup.db";
	private static final int DATABASE_VERSION = 1;
	private static DatabaseHelper mInstance = null;
	
	public static DatabaseHelper getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = new DatabaseHelper(ctx.getApplicationContext());
		}
		return mInstance;
	}
	
	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	//Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		WorkoutRoutineTable.onCreate(database);
		ExerciseTable.onCreate(database);
		WorkoutRoutineExerciseTable.onCreate(database);
		AttributeTable.onCreate(database);
		ExerciseAttributeTable.onCreate(database);
		RecordTable.onCreate(database);
		fillTestData(database);
		
	}
	
	//Method is called during an upgrade of the database,
	//e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		WorkoutRoutineTable.onUpgrade(database, oldVersion, newVersion);
		ExerciseTable.onUpgrade(database, oldVersion, newVersion);
		WorkoutRoutineExerciseTable.onUpgrade(database, oldVersion, newVersion);
		AttributeTable.onUpgrade(database, oldVersion, newVersion);
		ExerciseAttributeTable.onUpgrade(database, oldVersion, newVersion);
		RecordTable.onUpgrade(database, oldVersion, newVersion);
	}
	
	//Seed Exercise Table
	private void fillTestData(SQLiteDatabase db) {
		//fill with Exercises
		/*1*/db.execSQL("insert into Exercises (name, description, category) values ('Running', 'bad for knees', 'Cardio')");
		/*2*/db.execSQL("insert into Exercises (name, description, category) values ('Swimming', 'good for knees', 'Cardio')");
		/*3*/db.execSQL("insert into Exercises (name, description, category) values ('Elliptical', 'good for knees', 'Cardio')");
		/*4*/db.execSQL("insert into Exercises (name, description, category) values ('Bench Press', 'with weights', 'Strength Training')");
		/*5*/db.execSQL("insert into Exercises (name, description, category) values ('Bicep Curls', 'with weights', 'Strength Training')");
		/*6*//*1*/db.execSQL("insert into Exercises (name, description, category) values ('Tricep Extensions', 'with weights', 'Strength Training')");
		/*7*/db.execSQL("insert into Exercises (name, description, category) values ('Jumping Jacks', 'bad for knees', 'Warmup')");
		/*8*/db.execSQL("insert into Exercises (name, description, category) values ('Stretching', 'good for knees', 'Warmup')");
		/*9*/db.execSQL("insert into Exercises (name, description, category) values ('Jump Rope', 'good for knees', 'Warmup')");
		
		//fill with Attributes
		
		//options of kilometers, meters, yards, etc, another table, with units?
		//add notes to records
		//possibly need to redesign database to accommodate reps and sets
		/*1*/db.execSQL("insert into Attributes (name, unit) values ('Time', 'minutes')");
		/*2*/db.execSQL("insert into Attributes (name, unit) values ('Distance', 'miles')"); //need to figure out how to change to h,m,s
		/*3*/db.execSQL("insert into Attributes (name, unit) values ('Set', 'count')");
		/*4*/db.execSQL("insert into Attributes (name, unit) values ('Reps', 'count')");
		/*5*/db.execSQL("insert into Attributes (name, unit) values ('Weight', 'pounds')");
		
		/*1*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (1,1)");
		/*2*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (1,2)");
		/*3*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (2,1)");
		/*4*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (2,2)");
		/*5*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (3,1)");
		/*6*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (3,2)");
		/*7*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (4,3)");
		/*8*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (4,4)");
		/*9*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (4,5)");
		/*10*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (5,3)");
		/*11*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (5,4)");
		/*12*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (5,5)");
		/*13*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (6,3)");
		/*14*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (6,4)");
		/*15*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (6,5)");
		/*16*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (7,3)");
		/*17*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (7,4)");
		/*18*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (8,1)");
		/*19*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (9,1)");
		/*20*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (9,3)");
		/*21*/db.execSQL("insert into ExerciseAttributes (exercise_id, attribute_id) values (9,4)");
		
	}
	//things to fix:
	/*person can only add exercise once
	 * fragments within addexercise edit so that fragments enable 2 behaviors
	 * rename classes so Activities have Activity at the end*/
}
