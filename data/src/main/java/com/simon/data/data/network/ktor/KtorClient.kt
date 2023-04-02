package com.simon.data.data.network.ktor

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import timber.log.Timber

private const val TIME_OUT: Long = 60_000

@OptIn(ExperimentalSerializationApi::class)
internal fun ktorHttpClient() = HttpClient(OkHttp) {

    expectSuccess = true
    engine {
        // this: OkHttpConfig
        config {
            // this: OkHttpClient.Builder
            followRedirects(true)
            // ...
        }

    }


    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Timber.tag("Logger Ktor =>").v(message)
            }
        }
        level = LogLevel.ALL
    }

    install(ContentNegotiation) {

        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            },
            contentType = ContentType.Any
        )
    }

    install(HttpCache)

    install(ContentNegotiation) {
        val converter = KotlinxSerializationConverter(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                explicitNulls = false
            }
        )
        register(ContentType.Application.Json, converter)
    }


    install(HttpTimeout) { // 4
        requestTimeoutMillis = TIME_OUT
        connectTimeoutMillis = TIME_OUT
        socketTimeoutMillis = TIME_OUT
    }


    install(ResponseObserver) {
        onResponse { response ->
            Timber.tag("HTTP status:").d(response.status.value.toString())
        }
    }


}