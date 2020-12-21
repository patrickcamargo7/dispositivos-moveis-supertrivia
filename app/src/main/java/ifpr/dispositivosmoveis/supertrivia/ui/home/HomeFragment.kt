package ifpr.dispositivosmoveis.supertrivia.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.util.GameSession
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), View.OnClickListener {
    var navController: NavController? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            if (!GameSession.isConfigured(it)) {
                btnGameStart.isEnabled = false
            }
        }

        btnGameSetup.setOnClickListener(this)
        btnGameStart.setOnClickListener(this)

        navController = Navigation.findNavController(view)

        context?.let {
            if (GameSession.isConfigured(it) && GameSession.getGame(it).isStarted()) {
                btnGameStart.text = resources.getText(R.string.continue_game)
            } else {
                btnGameStart.text = resources.getText(R.string.start)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnGameSetup -> {
                navController!!.navigate(R.id.action_navigation_game_to_navigation_settings)
            }
            R.id.btnGameStart -> {
                navController!!.navigate(R.id.action_navigation_game_to_gameFragment)
            }
            else -> {
            }
        }
    }
}