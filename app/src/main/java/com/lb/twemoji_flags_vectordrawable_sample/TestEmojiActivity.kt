package com.lb.twemoji_flags_vectordrawable_sample

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.lb.common_utils.BoundActivity
import com.lb.twemoji_flags_vectordrawable_sample.databinding.ActivityMainBinding
import com.lb.twemoji_flags_vectordrawable_sample.databinding.ActivityMainBinding.inflate
import com.lb.twemoji_flags_vectordrawable_sample.databinding.ActivityTestEmojiBinding

class TestEmojiActivity :
    BoundActivity<ActivityTestEmojiBinding>(ActivityTestEmojiBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(0))
        ViewCompat.setOnApplyWindowInsetsListener(binding.appBarLayout) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            binding.appBarLayout.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnRandomize.setOnClickListener {
            randomizeText()
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.twemojiTextView.text = s
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.seekBarFontSize.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val size = progress.coerceAtLeast(1).toFloat()
                binding.twemojiTextView.textSize = size
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        if (savedInstanceState == null) {
            randomizeText()
            binding.seekBarFontSize.progress = 40
            binding.twemojiTextView.textSize = 40f
        }
    }

    private fun randomizeText() {
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val nonFlagEmojis = listOf("😀", "😎", "🚀", "🐱", "🍎", "⚽", "🎸", "💡", "🌈", "🔥")
        val allCountries = CountryRepository.getAllCountries().filter { it.code != "IR" }

        val c0 = chars.random()
        val c1 = nonFlagEmojis.random()
        val c2 = allCountries.random().flagEmoji
        val c3 = "🇮🇷"
        val c4 = allCountries.random().flagEmoji
        val c5 = nonFlagEmojis.random()
        val c6 = chars.random()

        val randomText = "$c0$c1$c2$c3$c4$c5$c6"
        binding.editText.setText(randomText)
    }
}
