package bankzworld.com.data;

import android.content.Context;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name;
    public String gender;
    public String age;
    public String location;
    public String bloodGroup;
    public String weight;
    public String tempereture;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String gender, String age, String location, String bloodGroup, String weight, String tempereture) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.location = location;
        this.bloodGroup = bloodGroup;
        this.weight = weight;
        this.tempereture = tempereture;
    }

    public static String getUserToken(Context context) {
        return SharedPreferenceHandler.getUsersName(context);
    }

    public static void saveUserToken(Context context, String userName) {
        SharedPreferenceHandler.saveUserName(context, userName);
    }

}
