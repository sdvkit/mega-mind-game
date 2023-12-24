package com.sulitsa.dev.megamindgames.util

object Formatter {

    fun formatSeconds(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    fun formatCoins(coins: Int): String {
        return when {
            coins >= 1_000_000 -> {
                val millions = coins / 1_000_000
                val remainingThousands = coins % 1_000_000 / 1_000
                val remainingHundreds = coins % 1_000
                val formattedString = StringBuilder()

                if (millions > 0) {
                    formattedString.append("$millions" + "kk ")
                }

                if (remainingThousands > 0) {
                    formattedString.append("$remainingThousands" + "k ")
                }

                if (remainingHundreds > 0) {
                    formattedString.append("$remainingHundreds")
                }

                formattedString.toString().trim()
            }
            coins >= 1_000 -> {
                val thousands = coins / 1_000
                val remainingHundreds = coins % 1_000

                when (remainingHundreds == 0) {
                    true -> "$thousands" + "k"
                    else -> "$thousands" + "k " + "$remainingHundreds"
                }
            }
            else -> coins.toString()
        }
    }
}