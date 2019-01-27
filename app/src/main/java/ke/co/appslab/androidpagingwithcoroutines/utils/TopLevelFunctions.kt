package ke.co.appslab.androidpagingwithcoroutines.utils

import java.io.IOException

suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> = try {
    call.invoke()
} catch (e: Exception) {
    Result.Error(IOException(errorMessage, e))
}
val <T> T.exhaustive: T get() = this