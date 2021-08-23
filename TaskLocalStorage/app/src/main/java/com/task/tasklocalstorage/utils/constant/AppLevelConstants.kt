package com.task.tasklocalstorage.utils.constant

object AppLevelConstants {
    /***INTENT */
    const val PARCEL_DATA = "parcel_data"
    const val PARCEL_EXTRA_DATA = "parcel_extra_data"
    const val EXTRA_DATA = "extra_data"
    const val ACCESS_TYPE = "access_type"

    /*******Ted permission */
    const val PERMISSION_ACCEPTED = 201
    const val PERMISSION_DENIED = 202

    /*******Password pattern */
    const val PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}$"

    /**********Api status**********/
    const val SUCCESS=200
    const val FAILED=400
    const val PENDING=201
    const val API_NOT_VERIFIED=202

    /***************OTP SCREEN*********************/
    const val FORGOT_PASSWORD=19001
}