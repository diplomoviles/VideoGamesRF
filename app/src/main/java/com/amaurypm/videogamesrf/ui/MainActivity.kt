package com.amaurypm.videogamesrf.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.amaurypm.videogamesrf.R
import com.amaurypm.videogamesrf.data.GameRepository
import com.amaurypm.videogamesrf.data.remote.RetrofitHelper
import com.amaurypm.videogamesrf.data.remote.model.GameDto
import com.amaurypm.videogamesrf.databinding.ActivityMainBinding
import com.amaurypm.videogamesrf.ui.fragments.GamesListFragment
import com.amaurypm.videogamesrf.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /*private lateinit var repository: GameRepository
    private lateinit var retrofit: Retrofit*/



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, GamesListFragment())
                .commit()
        }



        /*retrofit = RetrofitHelper().getRetrofit()
        repository = GameRepository(retrofit)

        lifecycleScope.launch {

            val call: Call<List<GameDto>> = repository.getGames("cm/games/games_list.php")

            call.enqueue(object : Callback<List<GameDto>> {
                override fun onResponse(p0: Call<List<GameDto>>, response: Response<List<GameDto>>) {
                    //Respuesta del server
                    Log.d(Constants.LOGTAG, "Respuesta recibida: ${response.body()}")
                }

                override fun onFailure(p0: Call<List<GameDto>>, error: Throwable) {
                    //Manejo del error
                    Toast.makeText(
                        this@MainActivity,
                        "Error en la conexión: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })

        }*/

    }
}