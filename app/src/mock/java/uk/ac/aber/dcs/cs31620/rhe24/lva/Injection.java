package uk.ac.aber.dcs.cs31620.rhe24.lva;

import android.content.Context;

import uk.ac.aber.dcs.cs31620.rhe24.lva.datasource.LVAInMemoryRoomDatabase;
import uk.ac.aber.dcs.cs31620.rhe24.lva.datasource.RoomDatabaseI;

/**
 * Injection.java
 *
 * Inject the in-memory database for mock build variant
 *
 * @author Rhys Evans
 * @version 4/12/2018
 */
public class Injection {

    public static RoomDatabaseI getDatabase(Context context){
        return LVAInMemoryRoomDatabase.getDatabase(context);
    }
}
