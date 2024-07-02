package com.example.ttspractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tts: TextToSpeech? = null

    private lateinit var ttsButton: Button
    private lateinit var inputText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ttsButton = findViewById(R.id.tts_button)
        inputText = findViewById(R.id.text_editText)

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.d("TTS", "Language not supported")
                } else {
                    ttsButton.isEnabled = true
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        }

        ttsButton.setOnClickListener { startTTS() }
    }

    private fun startTTS() {
        val text: CharSequence = inputText.text
        tts?.setPitch(0.7f) // 음성 톤 높이 지정
        tts?.setSpeechRate(0.8f) // 음성 속도 지정
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "id1")

    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        super.onDestroy()
    }
}