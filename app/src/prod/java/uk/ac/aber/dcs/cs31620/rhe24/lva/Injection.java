package uk.ac.aber.dcs.cs31620.rhe24.lva;

import android.content.Context;

import uk.ac.aber.dcs.cs31620.rhe24.lva.datasource.LVAPersistentRoomDatabase;
import uk.ac.aber.dcs.cs31620.rhe24.lva.datasource.RoomDatabaseI;

/**
 * Injection.java
 *
 * Inject database for production build variant
 *
 * @author Rhys Evans
 * @version 4/12/2018
 */
public class Injection {

    public static RoomDatabaseI getDatabase(Context context){
        return LVAPersistentRoomDatabase.getDatabase(context);
    }
}
