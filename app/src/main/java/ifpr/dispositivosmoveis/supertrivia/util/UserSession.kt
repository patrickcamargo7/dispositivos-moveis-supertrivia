package ifpr.dispositivosmoveis.supertrivia.util

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import ifpr.dispositivosmoveis.supertrivia.models.User

class UserSession {
    companion object {
        private const val USER_KEY = "_auth_user";
        private const val PREFS_FILE_KEY = "_prefs_file_key";

        fun setAuthenticatedUser(context: Context, user: User) {
            val sharedPref = context.getSharedPreferences(PREFS_FILE_KEY, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                var userString = Gson().toJson(user)
                putString(USER_KEY, userString)
                commit()
            }
        }

        fun isAuthenticated(activity: Activity): Boolean {
            return try {
                getUser(activity)
                true
            } catch (e: Exception) {
                false
            }
        }

        fun getUser(context: Context): User {
            val sharedPref = context.getSharedPreferences(PREFS_FILE_KEY, Context.MODE_PRIVATE)
            val userAuth = sharedPref.getString(USER_KEY, "")

            if (userAuth!!.isEmpty())
                throw Exception();
            return Gson().fromJson(userAuth, User::class.java)
        }

        fun logout(context: Context)
        {
            val sharedPref = context?.getSharedPreferences(PREFS_FILE_KEY, Context.MODE_PRIVATE)
            sharedPref.edit().remove(USER_KEY).commit()
        }
    }
}