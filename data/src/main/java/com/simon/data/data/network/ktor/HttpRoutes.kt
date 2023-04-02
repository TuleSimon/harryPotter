package com.simon.data.data.network.ktor

object HttpRoutes {
    const val BASE_URL = "https://hp-api.onrender.com/api/"
    const val ALL_CHARACTERS = "${BASE_URL}characters"

    const val UNSPLASH_BASEURL = "https://api.unsplash.com/"
    const val RANDOM_PHOTOS = "${UNSPLASH_BASEURL}photos/random?orientation=landscape"
    const val CLIENT_KEY_PARAMS= "client_id"

}