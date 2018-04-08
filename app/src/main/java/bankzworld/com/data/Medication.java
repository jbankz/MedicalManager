package bankzworld.com.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.widget.EditText;

@Entity
public class Medication {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "numOfDoze")
    private String numOfDoze;

    @ColumnInfo(name = "numOfTimes")
    private String numOfTimes;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "endDate")
    private String endDate;

    @ColumnInfo(name = "month")
    private String month;

    public Medication(String name, String description, String numOfDoze, String numOfTimes, String date, String endDate, String month) {
        this.name = name;
        this.description = description;
        this.numOfDoze = numOfDoze;
        this.numOfTimes = numOfTimes;
        this.date = date;
        this.endDate = endDate;
        this.month = month;
    }

    public Medication() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumOfDoze() {
        return numOfDoze;
    }

    public void setNumOfDoze(String numOfDoze) {
        this.numOfDoze = numOfDoze;
    }

    public String getNumOfTimes() {
        return numOfTimes;
    }

    public void setNumOfTimes(String numOfTimes) {
        this.numOfTimes = numOfTimes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
