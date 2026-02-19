package com.lb.twemoji_flags_vectordrawable_sample

import java.util.Locale

object CountryRepository {

    fun getAllCountries(): List<Country> {
        val isoCountries = Locale.getISOCountries()
        val countries = mutableListOf<Country>()
        val englishLocale = Locale.ENGLISH

        for (countryCode in isoCountries) {
            val locale = Locale.Builder().setRegion(countryCode).build()
            val name = locale.displayCountry
            // If the name is empty or just the country code, it's likely not a valid/translated name
            if (name.isEmpty() || name.equals(countryCode, ignoreCase = true)) continue

            val englishName = locale.getDisplayCountry(englishLocale)
            val flagEmoji = countryCodeToEmoji(countryCode)
            countries.add(Country(name, flagEmoji, countryCode, englishName))
        }

        return countries.sortedBy { it.name }
    }

    private fun countryCodeToEmoji(countryCode: String): String {
        if (countryCode.length != 2) return ""
        val offset = 0x1F1E6 - 0x41
        val firstChar = Character.codePointAt(countryCode, 0) + offset
        val secondChar = Character.codePointAt(countryCode, 1) + offset
        return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
    }
}
