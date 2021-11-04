package com.example.postdemojetpackcompose.network

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.io.InterruptedIOException

object NetworkRoute {


    /**
     * Main and only one public fun in this class to invoke api calls safely granting error and exception handling.
     * All the mapping stuff to return only success or error model from done here.
     */
    suspend fun <T : Any> makeApiCall(
        call: suspend () -> Response<T>
    ): ApiResponseResult<T> {

        val response: Response<T>?
        try {
            response = call.invoke()
            val responseModel = response.body()
            val responseErrorBody = response.errorBody()
            if (isValidResponse(response)) return ApiResponseResult.Success(
                responseModel
            )
            if (responseErrorBody != null) return ApiResponseResult.Error("Error occurs in API")
            return ApiResponseResult.Error("Error occurs in API")
        } catch (throwable: Throwable) {
            return when (throwable) {
                is IOException, is InterruptedIOException, is HttpException -> ApiResponseResult.Error("Error Occurs")
                else -> ApiResponseResult.Error("Error Occurs")
            }
        }
    }

    /**
     * Check if call is successful.
     */
    private fun <T> isValidResponse(response: Response<T>?): Boolean {
        return response != null && response.isSuccessful && response.body() != null
    }

}

sealed class ApiResponseResult<out T : Any> {
    data class Success<out T : Any>(val data: Any?) : ApiResponseResult<T>()
    data class Error(val errorMsg: String) : ApiResponseResult<Nothing>()
}
sealed class DataSourceResult<out T : Any> {
    data class Success<out T : Any>(val data: Any?) : DataSourceResult<T>()
    data class Error(val errorMsg: String) : DataSourceResult<Nothing>()
}
