package ifpr.dispositivosmoveis.supertrivia.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.dao.UserDAO
import ifpr.dispositivosmoveis.supertrivia.models.User
import ifpr.dispositivosmoveis.supertrivia.ui.main.MainActivity
import ifpr.dispositivosmoveis.supertrivia.util.Helpers
import ifpr.dispositivosmoveis.supertrivia.util.UserSession
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private val dao: UserDAO = UserDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegisterPage.setOnClickListener(this)
        tvHaveAccount.setOnClickListener(this)
    }

    private fun showRegisterFailed(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnRegisterPage -> {
                register()
            }
            R.id.tvHaveAccount -> {
                startLoginActivity()
            }
            else -> {
            }
        }
    }

    private fun register() {
        if (!formIsValid()) {
            showRegisterFailed(getString(R.string.invalid_information))
            return
        }

        val user = User(
            tvUsernameRegister.text.toString(),
            passwordRegister.text.toString(),
            tvEmailRegister.text.toString(),
            ""
        )

        registerProgressBar.visibility = View.VISIBLE

        dao.register(user, { userAPI ->
            registerProgressBar.visibility = View.GONE
            if (userAPI.isSuccessfully()) {
                UserSession.setAuthenticatedUser(this, userAPI.data)
                startMainActivity()
            } else {
                showRegisterFailed(getString(R.string.invalid_information))
            }
            registerProgressBar.visibility = View.GONE
        }, { error ->
            Helpers.showErrorConnection(error, this)

            registerProgressBar.visibility = View.GONE
            showRegisterFailed(getString(R.string.invalid_information))
        })
    }

    private fun formIsValid(): Boolean {
        if (tvUsernameRegister.text.toString().isNotEmpty() && tvEmailRegister.text.toString()
                .isNotEmpty() && passwordRegister.text.toString()
                .isNotEmpty() && passwordRegister.text.toString() == passwordRegisterConfimation.text.toString()
        ) {
            return true;
        }
        return false;
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)

        finish()
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)

        finish()
    }

}