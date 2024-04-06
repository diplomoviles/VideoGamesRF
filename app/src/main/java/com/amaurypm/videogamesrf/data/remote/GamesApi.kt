package com.amaurypm.videogamesrf.data.remote

import com.amaurypm.videogamesrf.data.remote.model.GameDetailDto
import com.amaurypm.videogamesrf.data.remote.model.GameDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

//Sería el equivalente al DAO en Room
interface GamesApi {

    //Aquí ponemos las funciones para los endpoints

    //https://www.serverbpw.com/cm/games/games_list.php

    @GET
    fun getGames(
        @Url url: String?
    ): Call<List<GameDto>>    //Así se llamaría: getGames("cm/games/games_list.php")"

    //https://www.serverbpw.com/cm/games/game_detail.php?id=21357&name=amaury

    @GET("cm/games/game_detail.php?")
    fun getGameDetail(
        @Query("id") id: String?/*,
        @Query("name") name: String?*/
    ): Call<GameDetailDto>

   //Se llamaría a la función: getGameDetail("21357")
   //Se genera la url: https://www.serverbpw.com/cm/games/game_detail.php?id=21357


    //---------------------------------------------------
    //Para generar endpoints del estilo
    //https://www.serverbpw.com/cm/games/21357/amaury

    @GET("cm/games/{id}/{name}")
    fun getGameTest(
        @Path("id") id: String?,
        @Path("name") name: String?
    ): Call<GameDetailDto>
    //Se llamaría a la función: getGameTest("21357", "amaury")
    //Se genera la url: https://www.serverbpw.com/cm/games/21357/amaury



}