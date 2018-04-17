package bankzworld.com.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MedicationDao {

    // insert to database
    @Insert
    void insertAll(Medication... medications);

    // query from database
    @Query("SELECT * FROM Medication")
    List<Medication> getAll();

    // queries for items by month
    @Query("SELECT * FROM Medication WHERE month IN  (:month)")
    List<Medication> getSingleItem(String month);

    // query from database
    @Delete
    int deleteListItem(Medication medication);

    // delete all items in the database
    @Query("DELETE FROM Medication")
    void deleteAll();

}
