package com.lb.twemoji_flags_vectordrawable_sample

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.lb.common_utils.BoundActivity
import com.lb.twemoji_flags_vectordrawable_sample.databinding.ActivityMainBinding

class MainActivity  : BoundActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var adapter: CountryAdapter
    private var allCountries: List<Country> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(0))
        ViewCompat.setOnApplyWindowInsetsListener(binding.appBarLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            binding.appBarLayout.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        setSupportActionBar(binding.toolbar)
        setupRecyclerView()
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                val searchView = SearchView(this@MainActivity)
                menu.add("Search").setActionView(searchView)
                    .setIcon(android.R.drawable.ic_menu_search)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_ALWAYS)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false
                    override fun onQueryTextChange(newText: String?): Boolean {
                        filter(newText)
                        return true
                    }
                })
                menu.add("Test emoji").setOnMenuItemClickListener {
                    startActivity(Intent(this@MainActivity, TestEmojiActivity::class.java))
                    true
                }.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                menu.addSubMenu("More info").let { subMenu ->
                    subMenu.setIcon(android.R.drawable.ic_menu_info_details)
                    subMenu.item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                    subMenu.add("Repository website").setOnMenuItemClickListener(
                        createUrlMenuItemClickListener("https://github.com/AndroidDeveloperLB/TwemojiFlagsVectorDrawable")
                    )
                    subMenu.add("All my repositories").setOnMenuItemClickListener(
                        createUrlMenuItemClickListener("https://github.com/AndroidDeveloperLB")
                    )
                    subMenu.add("All my apps").setOnMenuItemClickListener(
                        createUrlMenuItemClickListener("https://play.google.com/store/apps/developer?id=AndroidDeveloperLB")
                    )
                }
            }

            private fun createUrlMenuItemClickListener(url: String): MenuItem.OnMenuItemClickListener {
                return MenuItem.OnMenuItemClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    @Suppress("DEPRECATION")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                    startActivity(intent)
                    true
                }
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        allCountries = CountryRepository.getAllCountries()
        adapter = CountryAdapter(allCountries) { country ->
            copyToClipboard(country.flagEmoji)
            Toast.makeText(this, "Copied: ${country.flagEmoji}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.adapter = adapter
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("emoji", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun filter(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            allCountries
        } else {
            val lowerCaseQuery = query.lowercase()
            allCountries.filter {
                it.name.lowercase().contains(lowerCaseQuery) ||
                        it.code.lowercase().contains(lowerCaseQuery) ||
                        it.englishName.lowercase().contains(lowerCaseQuery)
            }
        }
        adapter.updateList(filteredList)
        binding.recyclerView.post { binding.recyclerView.scrollToPosition(0) }
    }

}
