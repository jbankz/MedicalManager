package bankzworld.com.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHandler {
    private final static String USER_PREFS = "MEDICAL_PREF";

    //get User token from SharedPreference
    public static String getUsersName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        String userDetailsAsString = preferences.getString("username", "");
        return userDetailsAsString;
    }

    //Save User token from SharedPreference
    public static void saveUserName(Context context, String userName) {
        SharedPreferences preferences = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        preferences.edit().putString("username", userName).commit();
    }

}
