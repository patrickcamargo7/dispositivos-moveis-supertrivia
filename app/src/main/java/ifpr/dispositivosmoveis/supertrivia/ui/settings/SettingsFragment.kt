package ifpr.dispositivosmoveis.supertrivia.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.adapters.CategoryAdapter
import ifpr.dispositivosmoveis.supertrivia.adapters.DifficultyAdapter
import ifpr.dispositivosmoveis.supertrivia.dao.CategoryDAO
import ifpr.dispositivosmoveis.supertrivia.models.Game
import ifpr.dispositivosmoveis.supertrivia.util.GameSession
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import java.util.*

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

        dao.findAll({ categoriesAPI ->
            categoryAdapter = CategoryAdapter(categoriesAPI.data)
            view.listCategories.adapter = categoryAdapter
            view.listCategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            progressBarCategories.visibility = View.GONE
        }, {
            progressBarCategories.visibility = View.GONE
        })

        val difficulties = listOf("easy", "medium", "hard")

        difficultyAdapter = DifficultyAdapter(difficulties)
        view.listDificulties.adapter = difficultyAdapter
        view.listDificulties.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSave -> {
                saveSettings()
            }
            else -> {
            }
        }
    }

    private fun saveSettings()
    {
        val game = Game(
            difficulty = difficultyAdapter.getSelectedItem(),
            category = categoryAdapter.getSelectedItem()
        )

        context?.let { GameSession.setGameSettings(it, game) }
    }
}