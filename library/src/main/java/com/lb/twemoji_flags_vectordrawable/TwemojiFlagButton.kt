package com.lb.twemoji_flags_vectordrawable

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

open class TwemojiFlagButton : MaterialButton  {
    @Suppress("unused")
    constructor(context: Context) : super(context)

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var originalText: CharSequence? = null
    private var isProcessing = false

    override fun setText(text: CharSequence?, type: BufferType?) {
        if (isProcessing) {
            super.setText(text, type)
            return
        }
        originalText = text
        updateText()
    }

    private fun updateText() {
        val text = originalText ?: return
        isProcessing = true
        val processed = TwemojiUtils.process(context, text, paint.textSize)
        super.setText(processed, BufferType.SPANNABLE)
        isProcessing = false
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        updateText()
    }
}
