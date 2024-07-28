package com.example.kaizenchallange.presentation.util

fun Int.formatTime(): String {
    val days = this / (3600 * 24)
    val hours = (this % (3600 * 24)) / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60
    return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, remainingSeconds)
}