package com.kiyosuke.resulter

sealed class PermissionResult {
    object Granted : PermissionResult()
    object Denied : PermissionResult()
    object NeverShowAgain : PermissionResult()
}