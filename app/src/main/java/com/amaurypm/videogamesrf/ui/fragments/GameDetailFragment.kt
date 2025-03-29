package com.amaurypm.videogamesrf.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.amaurypm.videogamesrf.R
import com.amaurypm.videogamesrf.application.VideogamesRFApp
import com.amaurypm.videogamesrf.data.GameRepository
import com.amaurypm.videogamesrf.databinding.FragmentGameDetailBinding
import com.amaurypm.videogamesrf.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

private const val ARG_GAMEID = "id"

class GameDetailFragment : Fragment() {

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    private var gameId: String? = null

    private lateinit var repository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            gameId = args.getString(ARG_GAMEID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Aquí inflamos la vista correspondiente
        _binding = FragmentGameDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Instanciamos el repositorio desde la clase VideogamesRFApp
        repository = (requireActivity().application as VideogamesRFApp).repository

        //Log.d(Constants.LOGTAG, "Id recibido en el fragment de detalles: $gameId")

        lifecycleScope.launch {

            try {
                val gameDetail = repository.getGameDetail(gameId)

                binding.apply {

                    tvTitle.text = gameDetail.title

                    Glide.with(requireActivity())
                        .load(gameDetail.image)
                        .into(ivImage)

                    tvLongDesc.text = gameDetail.longDesc

                }

            }catch (e: Exception){
                //Manejamos la excepción
            }finally {
                binding.pbLoading.visibility = View.GONE

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        //Instancia al fragment
        @JvmStatic
        fun newInstance(id: String) =
            GameDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_GAMEID, id)
                }
            }
    }
}