package com.cursoandroid.melisearchapp.common

import com.cursoandroid.melisearchapp.R

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