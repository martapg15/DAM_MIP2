package dam.a51564.mip2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import dam.a51564.mip2.adapter.DogImageAdapter
import dam.a51564.mip2.viewmodel.DogViewModel

/**
 * Main Activity displaying a grid of random dog images and a breed filter.
 *
 * See docs/03_screens.md and docs/09_feature_extensions.md.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: DogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // ── Edge-to-edge insets ──
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ── View references ──
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val spinnerBreeds = findViewById<Spinner>(R.id.spinnerBreeds)
        val recyclerViewDogs = findViewById<RecyclerView>(R.id.recyclerViewDogs)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val buttonRefresh = findViewById<MaterialButton>(R.id.buttonRefresh)

        // ── Toolbar ──
        setSupportActionBar(toolbar)

        // ── ViewModel ──
        viewModel = ViewModelProvider(this)[DogViewModel::class.java]

        // ── Grid Setup ──
        val adapter = DogImageAdapter()
        recyclerViewDogs.layoutManager = GridLayoutManager(this, 2)
        recyclerViewDogs.adapter = adapter

        // ── Observe Dog Images ──
        viewModel.dogImages.observe(this) { images ->
            adapter.updateImages(images)
        }

        // ── Setup and Observe Spinner ──
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBreeds.adapter = spinnerAdapter

        viewModel.breeds.observe(this) { breedList ->
            spinnerAdapter.clear()
            spinnerAdapter.addAll(breedList)
            // Preserve selection
            viewModel.selectedBreed.value?.let { current ->
                val pos = breedList.indexOf(current)
                if (pos >= 0) spinnerBreeds.setSelection(pos)
            }
        }

        // ── Listen for Spinner Selection ──
        spinnerBreeds.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = spinnerAdapter.getItem(position)
                if (selected != null) {
                    viewModel.setBreed(selected)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // ── Observe error messages ──
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // ── Observe loading state ──
        viewModel.isLoading.observe(this) { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        // ── Refresh button ──
        buttonRefresh.setOnClickListener {
            viewModel.fetchImages()
        }
    }
}