package ifpr.dispositivosmoveis.supertrivia.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.dao.UserDAO
import ifpr.dispositivosmoveis.supertrivia.ui.main.MainActivity
import ifpr.dispositivosmoveis.supertrivia.util.UserSession
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val dao: UserDAO = UserDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        login.setOnClickListener(this);
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.login -> {
               authenticate()
            }
            else -> {
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun authenticate() {
        if (!formIsValid()) {
            showLoginFailed(R.string.invalid_email_or_password)
            return
        }

        loginProgressBar.visibility = View.VISIBLE

        dao.authenticate(username.text.toString(), password.text.toString(), { userAPI ->
            loginProgressBar.visibility = View.GONE
            if (userAPI.isSuccessfully()) {
                UserSession.setAuthenticatedUser(this, userAPI.data)
                startMainActivity()
            } else {
                showLoginFailed(R.string.invalid_email_or_password)
            }
        }, {
            loginProgressBar.visibility = View.GONE
            showLoginFailed(R.string.invalid_email_or_password)
        })

        return;
    }

    private fun formIsValid() : Boolean {
        if (username.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()) {
            return true;
        }
        return false;
    }
}
