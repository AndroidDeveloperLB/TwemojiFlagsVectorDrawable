package com.lb.twemoji_flags_vectordrawable

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

open class TwemojiFlagEditText : AppCompatEditText {
    @Suppress("unused")
    constructor(context: Context) : super(context)

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var isProcessing = false

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isProcessing || s == null) return
                val processed = TwemojiUtils.process(context, s,  paint.textSize)
                if (processed != null) {
                    isProcessing = true
                    val selectionStart = selectionStart
                    val selectionEnd = selectionEnd
                    s.replace(0, s.length, processed)
                    setSelection(selectionStart.coerceAtMost(s.length), selectionEnd.coerceAtMost(s.length))
                    isProcessing = false
                }
            }
        })
    }
}
