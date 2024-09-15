package hr.algebra.spacexapp.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.spacexapp.R
import hr.algebra.spacexapp.model.Item
import hr.algebra.spacexapp.SPACEX_PROVIDER_CONTENT_URI
import hr.algebra.spacexapp.framework.startActivity


class ItemPagerAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)


        private val tvHomePort = itemView.findViewById<TextView>(R.id.tvHomePort)
        private val tvShipName = itemView.findViewById<TextView>(R.id.tvShipName)
        private val tvYearBuilt = itemView.findViewById<TextView>(R.id.tvYearBuilt)

        fun bind(item: Item) {
            tvHomePort.text = item.homePort ?: "Rijeka"
            tvShipName.text = item.name
            tvYearBuilt.text = item.yearBuilt.toString()

            ivRead.setImageResource(
                if (item.read)
                    R.drawable.green_flag
                else
                    R.drawable.red_flag
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_pager, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivRead.setOnLongClickListener {

            val item = items[position]
            item.read = !item.read
            context.contentResolver.update(
                ContentUris.withAppendedId(SPACEX_PROVIDER_CONTENT_URI, item._id!!),
                ContentValues().apply {
                    put(Item::read.name, item.read)
                },
                null,
                null
            )

            notifyItemChanged(position)
            true
        }

        holder.bind(items[position])
    }
}