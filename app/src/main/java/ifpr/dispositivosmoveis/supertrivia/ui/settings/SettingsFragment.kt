package ifpr.dispositivosmoveis.supertrivia.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.adapters.CategoryAdapter
import ifpr.dispositivosmoveis.supertrivia.adapters.DifficultyAdapter
import ifpr.dispositivosmoveis.supertrivia.dao.CategoryDAO
import ifpr.dispositivosmoveis.supertrivia.models.Game
import ifpr.dispositivosmoveis.supertrivia.ui.login.LoginActivity
import ifpr.dispositivosmoveis.supertrivia.util.GameSession
import ifpr.dispositivosmoveis.supertrivia.util.Helpers
import ifpr.dispositivosmoveis.supertrivia.util.UserSession
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : Fragment(), View.OnClickListener {
    private val dao: CategoryDAO = CategoryDAO()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var difficultyAdapter: DifficultyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarCategories.visibility = View.VISIBLE

        btnLogout.setOnClickListener(this)

        dao.findAll({ categoriesAPI ->
            categoryAdapter = CategoryAdapter(categoriesAPI.data)
            view.listCategories.adapter = categoryAdapter
            view.listCategories.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            progressBarCategories.visibility = View.GONE
        }, {error ->
            progressBarCategories.visibility = View.GONE
            activity?.let { it1 -> Helpers.showErrorConnection(error, it1) }
        })

        val difficulties = listOf("easy", "medium", "hard")

        context?.let {
            btnSave.isEnabled = !GameSession.isConfigured(it)
        }

        difficultyAdapter = DifficultyAdapter(difficulties)
        view.listDificulties.adapter = difficultyAdapter
        view.listDificulties.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSave -> {
                saveSettings()
            }
            R.id.btnLogout -> {
                appLogout()
            }
            else -> {
            }
        }
    }

    private fun appLogout() {
        context?.let {
            GameSession.reset(it)
            UserSession.logout(it)

            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            startActivity(intent)

            this.activity?.finish()
        }
    }

    private fun saveSettings() {
        val game = Game(
            difficulty = difficultyAdapter.getSelectedItem(),
            category = categoryAdapter.getSelectedItem()
        )

        context?.let { GameSession.setGameSettings(it, game) }
    }
}