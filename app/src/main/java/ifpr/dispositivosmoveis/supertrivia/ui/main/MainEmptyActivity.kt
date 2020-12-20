package ifpr.dispositivosmoveis.supertrivia.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ifpr.dispositivosmoveis.supertrivia.ui.login.LoginActivity
import ifpr.dispositivosmoveis.supertrivia.util.UserSession


class MainEmptyActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityIntent: Intent = if (UserSession.isAuthenticated(this)) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        startActivity(activityIntent)
        finish()
    }
}