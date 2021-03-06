package ifpr.dispositivosmoveis.supertrivia.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.util.GameSession
import kotlinx.android.synthetic.main.fragment_finish_game.*

class FinishGameFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_finish_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadGameData()
    }

    private fun loadGameData() {
        context?.let {
            val game = GameSession.getGame(it)
            tvScore.text = game.score.toString()
            tvErrors.text = game.errors.toString() + " " + getString(R.string.errors)
            tvAsserts.text = game.asserts.toString() + " " + getString(R.string.asserts)
            GameSession.reset(it)
        }
    }

}