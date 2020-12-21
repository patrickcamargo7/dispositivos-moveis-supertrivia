package ifpr.dispositivosmoveis.supertrivia.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.adapters.RankingAdapter
import ifpr.dispositivosmoveis.supertrivia.dao.RankingDAO
import ifpr.dispositivosmoveis.supertrivia.util.Helpers
import kotlinx.android.synthetic.main.fragment_ranking.*
import kotlinx.android.synthetic.main.fragment_ranking.view.*

class RankingFragment : Fragment() {
    private val dao: RankingDAO = RankingDAO()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_ranking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarRanking.visibility = View.VISIBLE

        dao.findAll({ rankingAPI ->
            view.rankingList.adapter = RankingAdapter(rankingAPI.data)
            view.rankingList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            progressBarRanking.visibility = View.GONE
        }, { error ->
            progressBarRanking.visibility = View.GONE
            activity?.let { it1 -> Helpers.showErrorConnection(error, it1) }
        })
    }
}