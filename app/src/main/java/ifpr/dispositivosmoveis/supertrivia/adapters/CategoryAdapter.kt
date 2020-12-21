package ifpr.dispositivosmoveis.supertrivia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.models.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(categoriesList: List<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var categories = mutableListOf<Category>()
    private var selected : Category? = null

    init {
        categories = categoriesList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = categories.size

    override fun getItemViewType(position: Int): Int {
        if (categories[position] == selected) {
            return R.layout.item_category_selected
        }
        return R.layout.item_category;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.fillView(category, position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(category: Category, position: Int) {
            itemView.tvCategoryName.text = category.name

            itemView.setOnClickListener {
                selected = category
                notifyDataSetChanged()
            }
        }
    }

    fun getSelectedItem(): Category? {
        return selected
    }
}