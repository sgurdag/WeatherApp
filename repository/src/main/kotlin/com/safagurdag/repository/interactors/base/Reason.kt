package com.safagurdag.repository.interactors.base

import androidx.annotation.StringRes
import com.safagurdag.repository.R


/**
 * [Failure] reasons
 */
sealed class Reason(@StringRes val messageRes: Int)

//region Subclasses

class NetworkError : Reason(R.string.reason_network)
class EmptyResultError : Reason(R.string.reason_empty_body)
class GenericError : Reason(R.string.reason_generic)
class ResponseError : Reason(R.string.reason_response)
class TimeoutError : Reason(R.string.reason_timeout)
class PersistenceEmpty : Reason(R.string.reason_persistance_empty)
class PersistenceDataExpired : Reason(R.string.reason_persistance_data_is_expired)
class NoNetworkPersistenceEmpty : Reason(R.string.reason_no_network_persistance_empty)
//endregion
