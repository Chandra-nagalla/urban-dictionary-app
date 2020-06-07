package com.urban.dictionary.service

import retrofit2.Response

object RetrofitCallHandler {
    /**
     * Function to invoke service [call], catch exceptions like IOExceptions to be logged
     * when the service call return, check the meta data for response code
     * any exceptions occur or the service call does not return successful code or response data,
     * would return default error message
     * else will return the response data
     *
     * @param call retrofit suspend function to be invoked with return type of [Response] with metadata
     *
     * @return generic message or data of Type of [T]
     */
    suspend fun <T> processCall(
        call: suspend () -> Response<T>
    ): ServiceResult<T> {
        return try {

            val serviceCallback = call()
            val body = serviceCallback.body()
            if (serviceCallback.isSuccessful && body != null) {
                ServiceResult.Success(body)
            } else {
                getGenericServiceError("${serviceCallback.code()}")
            }

        } catch (exception: Exception) {
            ServiceResult.Error(exception)
        }
    }

    private fun getGenericServiceError(
        errorCode: String?
    ): ServiceResult.Error {
        return ServiceResult.Error(Exception(errorCode))
    }

}
