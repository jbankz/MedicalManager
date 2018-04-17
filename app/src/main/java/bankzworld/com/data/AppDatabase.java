package bankzworld.com.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Medication.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MedicationDao medicationDao();
}

