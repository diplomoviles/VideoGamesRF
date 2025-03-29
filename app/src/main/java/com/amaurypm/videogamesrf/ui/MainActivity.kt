package com.amaurypm.videogamesrf.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.amaurypm.videogamesrf.R
import com.amaurypm.videogamesrf.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /*private lateinit var repository: GameRepository
    private lateinit var retrofit: Retrofit*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*retrofit = RetrofitHelper().getRetrofit()

        repository = GameRepository(retrofit)

        lifecycleScope.launch {
            try{
                /*val games = repository.getGames()
                Log.d(Constants.LOGTAG, "Respuesta: $games")*/

                val game = repository.getGameDetail("21357")
                Log.d(Constants.LOGTAG, "Respuesta: $game")
            }
            catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(
                    this@MainActivity,
                    "Error en la conexión: ${e.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.d(Constants.LOGTAG, "Error en la conexión: ${e.message}")
            }
        }*/



    }
}