package bankzworld.com.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

@Entity
public class Medication {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "numOfDoze")
    private String mNumOfDoze;

    @ColumnInfo(name = "numOfTimes")
    private String mNumOfTimes;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "endDate")
    private String mEndDate;

    @ColumnInfo(name = "month")
    private String mMonth;


    public Medication(String name, String description, String numOfDoze, String numOfTimes, String date, String endDate, String month) {
        this.mName = name;
        this.mDescription = description;
        this.mNumOfDoze = numOfDoze;
        this.mNumOfTimes = numOfTimes;
        this.mDate = date;
        this.mEndDate = endDate;
        this.mMonth = month;
    }

    @Ignore
    public Medication() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getNumOfDoze() {
        return mNumOfDoze;
    }

    public void setNumOfDoze(String mNumOfDoze) {
        this.mNumOfDoze = mNumOfDoze;
    }

    public String getNumOfTimes() {
        return mNumOfTimes;
    }

    public void setNumOfTimes(String mNumOfTimes) {
        this.mNumOfTimes = mNumOfTimes;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String mMonth) {
        this.mMonth = mMonth;
    }

    public static void saveUsersName(Context context, String name) {
        SharedPreferenceHandler.saveUserName(context, name);
    }

    public static String getUserName(Context context) {
        return SharedPreferenceHandler.getUsersName(context);
    }
}
