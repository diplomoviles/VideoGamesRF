package com.amaurypm.videogamesrf.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.amaurypm.videogamesrf.R
import com.amaurypm.videogamesrf.data.remote.PokemonApi
import com.amaurypm.videogamesrf.databinding.ActivityPokemonBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class PokemonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonBinding

    companion object{
        const val BASE_URL = "https://pokeapi.co/"
        const val LOGTAG = "LOGSPOKEMON"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Instanciamos retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Instanciamos el api
        val pokemonApi = retrofit.create(PokemonApi::class.java)


        lifecycleScope.launch {

            try{

                val pokemonDetail = pokemonApi.getPokemonDetail("25")

                binding.tvPokemon.text = pokemonDetail.name

                Glide.with(this@PokemonActivity)
                    .load(pokemonDetail.sprites.other.officialArtwork.frontDefault)
                    .into(binding.ivPokemon)



            }catch (e: IOException){
                Log.d(LOGTAG, "Error de conexión: ${e.message}")
                Toast.makeText(
                    this@PokemonActivity,
                    "No hay conexión",
                    Toast.LENGTH_SHORT
                )
            }catch (e: Exception){
                e.printStackTrace()
                Log.d(LOGTAG, "Error inesperado: ${e.message}")
                Toast.makeText(
                    this@PokemonActivity,
                    "Error inesperado",
                    Toast.LENGTH_SHORT
                )
            }

        }



    }
}