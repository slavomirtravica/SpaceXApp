package hr.algebra.spacexapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hr.algebra.spacexapp.adapter.ItemPagerAdapter
import hr.algebra.spacexapp.databinding.ActivityItemPagerBinding
import hr.algebra.spacexapp.framework.fetchItems
import hr.algebra.spacexapp.model.Item

const val POSITION = "hr.algebra.spacexapp.item_pos"

class ItemPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemPagerBinding
    private lateinit var items: MutableList<Item>
    private var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPager() {
        items = fetchItems()
        position = intent.getIntExtra(POSITION, position)
        binding.viewPager.adapter = ItemPagerAdapter(this, items)
        binding.viewPager.currentItem = position
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}