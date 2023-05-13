package com.ahr.mandirinews.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Convert from string (2023-04-28T19:20:15Z) to LocalDate
 */
private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, dateFormatter)
}

/**
 * Convert from LocalDate to String (10 Jan, 2023)
 */
private val newsDateFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")

fun LocalDate.toNewsFormat(): String {
    return format(newsDateFormatter)
}
