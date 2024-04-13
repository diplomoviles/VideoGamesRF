package com.amaurypm.videogamesrf.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.amaurypm.videogamesrf.R
import com.amaurypm.videogamesrf.data.remote.PokemonApi
import com.amaurypm.videogamesrf.data.remote.model.PokemonDetail2Dto
import com.amaurypm.videogamesrf.data.remote.model.PokemonDetailDto
import com.amaurypm.videogamesrf.databinding.ActivityTestPokemonBinding
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestPokemon : AppCompatActivity() {

    private lateinit var binding: ActivityTestPokemonBinding

    companion object{
        const val BASE_URL = "https://pokeapi.co/"
        const val LOGTAG = "LOGSPOKEMON"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Generamos la instancia a retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Instanciamos el pokemonApi
        val pokemonApi = retrofit.create(PokemonApi::class.java)

        val call: Call<PokemonDetail2Dto> = pokemonApi.getPokemonDetail("6")

        call.enqueue(object: Callback<PokemonDetail2Dto>{
            override fun onResponse(p0: Call<PokemonDetail2Dto>, response: Response<PokemonDetail2Dto>) {
                Log.d(LOGTAG, "Pokémon recibido: ${response.body()?.name}, Url de la imagen: ${response.body()?.sprites?.other?.officialArtwork?.front_default}")

                binding.apply {

                    tvPokemon.text = response.body()?.name.toString().replaceFirstChar { firstChar ->
                        firstChar.uppercase()
                    }

                    Glide.with(this@TestPokemon)
                        .load(response.body()?.sprites?.other?.officialArtwork?.front_shiny)
                        .into(ivPokemon)
                }

            }

            override fun onFailure(p0: Call<PokemonDetail2Dto>, p1: Throwable) {

            }

        })



    }
}