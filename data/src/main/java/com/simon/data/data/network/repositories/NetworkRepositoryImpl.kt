package com.simon.data.data.network.repositories

import com.simon.data.models.characters.CharactersResponseItem
import com.simon.data.data.network.ktor.HttpRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

internal class NetworkRepositoryImpl(private val client:HttpClient) : NetworkRepository {
    override fun getCharacters(): Flow<NetworkResult<List<CharactersResponseItem>>> =flow{
        val request = client.get(HttpRoutes.ALL_CHARACTERS)

        when(request.status.value){
            in 200..299 -> {
                val body:List<CharactersResponseItem> = request.body()
                emit(NetworkResult.Success(body))
            }
            else ->{
                request.throwKtorException()
            }
        }

    }.defaultHandler()
}

/**
 * THis methods throws an exception from the error code
 */
fun HttpResponse.throwKtorException() {
    when (status.value) {
        in 300..399 -> throw RedirectResponseException(this, "error")
        in 400..499 -> throw ClientRequestException(this, "error")
        in 500..599 -> throw ServerResponseException(this, "error")
    }

}

/**
 * THis method handles the error thrown and return the json formatted response
 */
fun<T> Flow<NetworkResult<T>>.defaultHandler(): Flow<NetworkResult<T>> {
    return catch {
        it.printStackTrace()
        when (it) {
            is ResponseException -> {

                emit(NetworkResult.Failure(it.message.toString()))

                }

            else -> {
                emit(NetworkResult.Failure(it.message.toString()))
            }
        }
    }.onStart {
        emit(NetworkResult.Loading)

    }
}
