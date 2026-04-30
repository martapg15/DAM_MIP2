package dam.a51564.mip2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dam.a51564.mip2.adapter.DogImageAdapter
import dam.a51564.mip2.core.state.Resource
import dam.a51564.mip2.databinding.ActivityMainBinding
import dam.a51564.mip2.viewmodel.DogViewModel
import kotlinx.coroutines.launch

/**
 * Main Activity displaying a grid of random dog images and a breed filter.
 * Refactored to use ViewBinding.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: DogViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DogImageAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ── Edge-to-edge insets ──
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[DogViewModel::class.java]

        setupRecyclerView()
        setupSpinner()
        observeState()

        binding.buttonRefresh.setOnClickListener {
            viewModel.fetchImages()
        }
    }

    private fun setupRecyclerView() {
        adapter = DogImageAdapter()
        binding.recyclerViewDogs.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewDogs.adapter = adapter
    }

    private fun setupSpinner() {
        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerBreeds.adapter = spinnerAdapter

        binding.spinnerBreeds.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = spinnerAdapter.getItem(position)
                if (selected != null) {
                    viewModel.setBreed(selected)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun observeState() {
        // Observe Dog Images State
        lifecycleScope.launch {
            viewModel.dogImages.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        adapter.updateImages(resource.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, resource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Observe Breeds State
        lifecycleScope.launch {
            viewModel.breeds.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        spinnerAdapter.clear()
                        spinnerAdapter.addAll(resource.data)
                        // Preserve selection
                        val current = viewModel.selectedBreed.value
                        val pos = resource.data.indexOf(current)
                        if (pos >= 0) binding.spinnerBreeds.setSelection(pos)
                    }
                    is Resource.Error -> {
                        Toast.makeText(this@MainActivity, "Failed to load breeds", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}