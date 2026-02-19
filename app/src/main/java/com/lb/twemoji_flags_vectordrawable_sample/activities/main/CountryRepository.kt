package com.lb.twemoji_flags_vectordrawable_sample.activities.main

import com.lb.twemoji_flags_vectordrawable.TwemojiFlagUtils
import java.util.Locale

object CountryRepository {

    fun getAllCountries(): List<CountryAdapter.Country> {
        val isoCountries = Locale.getISOCountries()
        val countries = mutableListOf<CountryAdapter.Country>()
        val englishLocale = Locale.ENGLISH
        for (countryCode in isoCountries) {
            val locale = Locale.Builder().setRegion(countryCode).build()
            val name = locale.displayCountry
            // If the name is empty or just the country code, it's likely not a valid/translated name
            if (name.isEmpty() || name.equals(countryCode, ignoreCase = true)) continue
            val englishName = locale.getDisplayCountry(englishLocale)
            val flagEmoji = TwemojiFlagUtils.countryCodeToEmoji(countryCode)
            countries.add(CountryAdapter.Country(name, flagEmoji, countryCode, englishName))
        }
        return countries.sortedBy { it.name }
    }

}
