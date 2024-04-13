package com.amaurypm.videogamesrf.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.amaurypm.videogamesrf.R
import com.amaurypm.videogamesrf.application.VideoGamesRFApp
import com.amaurypm.videogamesrf.data.GameRepository
import com.amaurypm.videogamesrf.data.remote.model.GameDetailDto
import com.amaurypm.videogamesrf.databinding.FragmentGameDetailBinding
import com.amaurypm.videogamesrf.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val GAME_ID = "game_id"

class GameDetailFragment : Fragment() {
    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    private var game_id: String? = null

    private lateinit var repository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            game_id = args.getString(GAME_ID)

            Log.d(Constants.LOGTAG, "Id recibido: $game_id")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Programar la conexión
        repository = (requireActivity().application as VideoGamesRFApp).repository

        lifecycleScope.launch {
            game_id?.let { id ->

                //val call: Call<GameDetailDto> = repository.getGameDetail(id)

                //Con Apiary:
                val call: Call<GameDetailDto> = repository.getGameDetailApiary(id)

                call.enqueue(object: Callback<GameDetailDto>{
                    override fun onResponse(p0: Call<GameDetailDto>, response: Response<GameDetailDto>) {

                        binding.apply {
                            pbLoading.visibility = View.INVISIBLE

                            tvTitle.text = response.body()?.title

                            tvLongDesc.text = response.body()?.longDesc

                            Glide.with(requireActivity())
                                .load(response.body()?.image)
                                .into(ivImage)

                        }

                    }

                    override fun onFailure(p0: Call<GameDetailDto>, p1: Throwable) {
                        //Manejar el error sin conexión
                        binding.pbLoading.visibility = View.INVISIBLE
                    }

                })
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(gameId: String) =
            GameDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(GAME_ID, gameId)
                }
            }
    }
}