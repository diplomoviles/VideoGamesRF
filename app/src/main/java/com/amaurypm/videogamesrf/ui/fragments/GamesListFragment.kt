package com.amaurypm.videogamesrf.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaurypm.videogamesrf.R
import com.amaurypm.videogamesrf.application.VideoGamesRFApp
import com.amaurypm.videogamesrf.data.GameRepository
import com.amaurypm.videogamesrf.data.remote.model.GameDto
import com.amaurypm.videogamesrf.databinding.FragmentGamesListBinding
import com.amaurypm.videogamesrf.ui.adapters.GameAdapter
import com.amaurypm.videogamesrf.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GamesListFragment : Fragment() {

    private var _binding: FragmentGamesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGamesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //El usuario ya ve el fragment en pantalla
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = (requireActivity().application as VideoGamesRFApp).repository

        lifecycleScope.launch {

            val call: Call<List<GameDto>> = repository.getGames("cm/games/games_list.php")

            call.enqueue(object : Callback<List<GameDto>> {
                override fun onResponse(p0: Call<List<GameDto>>, response: Response<List<GameDto>>) {
                    //Respuesta del server

                    binding.pbLoading.visibility = View.GONE

                    Log.d(Constants.LOGTAG, "Respuesta recibida: ${response.body()}")

                    response.body()?.let { games ->
                        binding.rvGames.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = GameAdapter(games){ game ->
                                //Aquí va la operación para el click de cada elemento
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, GameDetailFragment.newInstance(game.id.toString()))
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }
                    }
                }

                override fun onFailure(p0: Call<List<GameDto>>, error: Throwable) {
                    //Manejo del error
                    binding.pbLoading.visibility = View.GONE

                    Toast.makeText(
                        requireContext(),
                        "Error en la conexión: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })

        }

    }

}