package ifpr.dispositivosmoveis.supertrivia.util

import android.app.Activity
import android.widget.Toast
import ifpr.dispositivosmoveis.supertrivia.R
import java.io.IOException

class Helpers {
    companion object {
        fun showErrorConnection(error: Throwable, activity: Activity) {
            if (error is IOException) {
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.connection_error),
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}