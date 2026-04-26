package dam.a51564.mip2

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import dam.a51564.mip2.viewmodel.DogViewModel

/**
 * Single-screen Activity that displays a random dog image
 * and lets the user refresh it.
 *
 * See docs/03_screens.md and docs/06_architecture.md – View Layer.
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
        val toolbar      = findViewById<MaterialToolbar>(R.id.toolbar)
        val imageViewDog = findViewById<ImageView>(R.id.imageViewDog)
        val progressBar  = findViewById<ProgressBar>(R.id.progressBar)
        val buttonRefresh = findViewById<MaterialButton>(R.id.buttonRefresh)

        // ── Toolbar ──
        setSupportActionBar(toolbar)

        // ── ViewModel ──
        viewModel = ViewModelProvider(this)[DogViewModel::class.java]

        // ── Observe image URL ──
        viewModel.dogImageUrl.observe(this) { url ->
            Glide.with(this)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageViewDog)
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
            viewModel.fetchRandomDogImage()
        }
    }
}