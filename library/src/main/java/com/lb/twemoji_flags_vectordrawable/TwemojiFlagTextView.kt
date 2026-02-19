package com.lb.twemoji_flags_vectordrawable

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView

open class TwemojiFlagTextView : MaterialTextView {
    @Suppress("unused")
    constructor(context: Context) : super(context)

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


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
        val size = paint.textSize.toInt()
        isProcessing = true
        val processed = TwemojiUtils.process(context, text, size)
        super.setText(processed, BufferType.SPANNABLE)
        isProcessing = false
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        updateText()
    }
}
