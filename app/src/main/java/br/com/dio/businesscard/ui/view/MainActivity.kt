package br.com.dio.businesscard.ui.view

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.SearchEvent
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.com.dio.businesscard.App
import br.com.dio.businesscard.R
import br.com.dio.businesscard.data.repository.BusinessCardRepository
import br.com.dio.businesscard.databinding.ActivityMainBinding
import br.com.dio.businesscard.ui.BusinessCardAdapter
import br.com.dio.businesscard.ui.MainViewModel
import br.com.dio.businesscard.ui.MainViewModelFactory
import br.com.dio.businesscard.util.Image
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.observeOn

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels { MainViewModelFactory((application as App).repository) }

    private val adapter by lazy { BusinessCardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpPermissions()

        binding.rvCards.adapter = adapter

        getAllBusinessCard()
        insertListeners()
    }

    private fun setUpPermissions() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddBusinessCardActivity::class.java)
            startActivity(intent)
        }

        adapter.listenerDelete = {
            val alertDialog = MaterialAlertDialogBuilder(this)
                .setTitle("Atenção")
                .setMessage("Tem certeza de que deseja deletar esse item?")

            alertDialog.setNegativeButton("Cancelar") { _, _ -> }

            alertDialog.setPositiveButton("Confirmar") { _, _ ->
                mainViewModel.delete(it)
                Toast.makeText(this, R.string.label_show_delete, Toast.LENGTH_SHORT).show()
                getAllBusinessCard()
            }

            alertDialog.show()
        }

        adapter.listenerShare = { card ->
            Image.share(this@MainActivity, card)
        }
    }

    private fun getAllBusinessCard() {
        mainViewModel.getAll().observe(this, { businessCards ->
            adapter.submitList(businessCards)

            if (businessCards.isNotEmpty()) {
                binding.rvCards.visibility = View.VISIBLE
                binding.includeEmpty.emptyState.visibility = View.GONE
            }

            else if (businessCards.isEmpty()) {
                binding.includeEmpty.emptyState.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }

        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        mainViewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                adapter.submitList(it)
            }
        })
    }
}