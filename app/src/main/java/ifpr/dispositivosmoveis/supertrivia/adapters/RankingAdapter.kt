package ifpr.dispositivosmoveis.supertrivia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.models.Ranking
import kotlinx.android.synthetic.main.item_ranking.view.*

class RankingAdapter(rankingList: List<Ranking>) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {
    private var ranking = mutableListOf<Ranking>()

    init {
        ranking = rankingList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = ranking.size

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_ranking;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rankingItem = ranking[position]
        holder.fillView(rankingItem, position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(ranking: Ranking, position: Int) {
            itemView.tvRankingPosition.text = (position + 1).toString() + "º"
            itemView.tvRankingUser.text = ranking.user
            itemView.tvRankingScore.text = ranking.score.toString() + "Pts"
        }
    }
}