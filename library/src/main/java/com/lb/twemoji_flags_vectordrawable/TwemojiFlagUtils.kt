package com.lb.twemoji_flags_vectordrawable

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import java.util.Locale
import kotlin.math.roundToInt

object TwemojiFlagUtils {

    /**
     * Processes the text to replace country code regional indicators with Twemoji drawables.
     *
     * @param size The size in pixels (Float) to match the text size.
     */
    fun process(context: Context, text: CharSequence?, size: Float): Spannable? {
        if (text == null) return null
        val builder = SpannableStringBuilder(text)
        val iconSize = size.roundToInt()
        var i = 0
        while (i < builder.length - 1) {
            val c1 = builder[i]
            val c2 = builder[i + 1]
            if (isRegionalIndicator(c1, c2)) {
                if (i + 3 < builder.length) {
                    val c3 = builder[i + 2]
                    val c4 = builder[i + 3]
                    if (isRegionalIndicator(c3, c4)) {
                        val char1 = getCharFromRI(c1, c2)
                        val char2 = getCharFromRI(c3, c4)
                        val countryCode = "$char1$char2"
                        val drawable=getFlagDrawable(context,countryCode)
                        if (drawable != null) {
                            drawable.setBounds(0, 0, iconSize, iconSize)
                            val span = ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM)
                            builder.setSpan(span, i, i + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                        i += 4
                        continue
                    }
                }
            }
            i++
        }
        return builder
    }

    private fun isRegionalIndicator(c1: Char, c2: Char): Boolean {
        return c1 == '\uD83C' && c2 in '\uDDE6'..'\uDDFF'
    }

    private fun getCharFromRI(c1: Char, c2: Char): Char {
        val codePoint = Character.toCodePoint(c1, c2)
        return (codePoint - 0x1F1E6 + 'A'.code).toChar()
    }

    fun getFlagResourceName(countryCode: String): String {
        val code = countryCode.uppercase(Locale.US)
        if (code.length != 2) return ""
        val hex1 = Integer.toHexString(code[0].code - 'A'.code + 0x1F1E6)
        val hex2 = Integer.toHexString(code[1].code - 'A'.code + 0x1F1E6)
        return "twemoji_flag_${hex1}_${hex2}"
    }

    fun countryCodeToEmoji(countryCode: String): String {
        if (countryCode.length != 2) return ""
        val offset = 0x1F1E6 - 0x41
        val firstChar = Character.codePointAt(countryCode, 0) + offset
        val secondChar = Character.codePointAt(countryCode, 1) + offset
        return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
    }

    /**
     * Gets the resource ID for a specific country code (e.g., "US", "GB").
     * Returns 0 if not found.
     */
    @DrawableRes
    fun getFlagResId(context: Context, countryCode: String): Int {
        val resName = getFlagResourceName(countryCode)
        if (resName.isEmpty())
            return 0
        return context.resources.getIdentifier(resName, "drawable", context.packageName)
    }

    /**
     * Gets the VectorDrawable for a specific country code.
     */
    fun getFlagDrawable(context: Context, countryCode: String): android.graphics.drawable.Drawable? {
        val resId = getFlagResId(context, countryCode)
        if (resId == 0)
            return null
        return try {
            ContextCompat.getDrawable(context, resId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Extracts the country code from a regional indicator emoji string (e.g., "🇺🇸")
     * and returns the resource ID (or 0 if not found)
     */
    @DrawableRes
    fun getFlagResIdFromEmoji(context: Context, emoji: String): Int {
        if (emoji.length < 4) return 0 // Regional indicators are surrogate pairs (2 chars each)
        val c1 = emoji[0]
        val c2 = emoji[1]
        val c3 = emoji[2]
        val c4 = emoji[3]
        return if (isRegionalIndicator(c1, c2) && isRegionalIndicator(c3, c4)) {
            val code = "${getCharFromRI(c1, c2)}${getCharFromRI(c3, c4)}"
            getFlagResId(context, code)
        } else 0
    }
}
