package ifpr.dispositivosmoveis.supertrivia.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.models.Category
import kotlinx.android.synthetic.main.item_difficulty.view.*

class DifficultyAdapter(difficultList: List<String>) : RecyclerView.Adapter<DifficultyAdapter.ViewHolder>() {
    private var difficulties = mutableListOf<String>()
    private var selected: String? = null

    init {
        difficulties = difficultList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = difficulties.size

    override fun getItemViewType(position: Int): Int {
        if (difficulties[position] == selected) {
            return R.layout.item_difficulty_selected
        }
        return R.layout.item_difficulty
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fillView(difficulties[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("ResourceAsColor")
        fun fillView(difficulty: String, position: Int) {
            itemView.tvDifficultyName.text = difficulty
            itemView.setOnClickListener {
                selected = difficulty
                notifyDataSetChanged()
            }
        }
    }

    fun getSelectedItem(): String? {
        return selected
    }
}