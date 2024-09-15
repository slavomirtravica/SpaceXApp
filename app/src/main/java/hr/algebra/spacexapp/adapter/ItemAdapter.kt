package hr.algebra.spacexapp.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.spacexapp.ItemPagerActivity
import hr.algebra.spacexapp.POSITION
import hr.algebra.spacexapp.R
import hr.algebra.spacexapp.model.Item
import hr.algebra.spacexapp.SPACEX_PROVIDER_CONTENT_URI
import hr.algebra.spacexapp.framework.startActivity
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File
import kotlin.io.path.Path

class ItemAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvItem = itemView.findViewById<TextView>(R.id.tvItem)

        fun bind(item: Item) {
            tvItem.text = item.name

            val imagePath = item.image
            if (!imagePath.isNullOrEmpty()) {
                Picasso.get()
                    .load(imagePath)
                    .error(R.drawable.spacex)
                    .transform(RoundedCornersTransformation(50, 5))
                    .into(ivItem)
            } else {
                // Handle the case where imagePath is null or empty
                ivItem.setImageResource(R.drawable.spacex)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnLongClickListener {

            val item = items[position]
            context.contentResolver.delete(
                ContentUris.withAppendedId(SPACEX_PROVIDER_CONTENT_URI, item._id!!),
                null,
                null
            )

            File(item.image).delete()
            notifyDataSetChanged()

            true
        }

        holder.itemView.setOnClickListener {
            context.startActivity<ItemPagerActivity>(POSITION, position)
        }

        holder.bind(items[position])
    }
}