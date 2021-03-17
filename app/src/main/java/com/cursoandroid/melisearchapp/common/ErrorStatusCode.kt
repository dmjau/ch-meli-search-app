package com.cursoandroid.melisearchapp.common

import com.cursoandroid.melisearchapp.R
/*
 * Estados de error posibles cuando se efectua la llamada a la Api.
 */
class ErrorStatusCode {
    companion object{
        fun evaluateResponseCode(code: Int): Int {
            return when (code) {
                404 -> {
                    R.string.resource_not_found
                }
                400 -> {
                    R.string.bad_request
                }
                in 500..599 -> {
                    R.string.server_error
                }
                else -> {
                    R.string.unknown_error
                }
            }
        }
    }
}