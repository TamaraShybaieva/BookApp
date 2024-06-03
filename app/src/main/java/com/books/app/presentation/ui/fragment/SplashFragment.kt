package com.books.app.presentation.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.books.app.R

class SplashFragment : Fragment(R.layout.activity_splash) {
    private val READ_PHONE_STATE_PERMISSION_REQUEST_CODE = 100
    private lateinit var progressBar: ProgressBar
    private lateinit var subtitle: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progressBar)
        subtitle = view.findViewById(R.id.subtitle)
        if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
        ) {
            requestReadPhoneStatePermission()
        }
        startProgressBar()
    }

    private fun startProgressBar() {
        val timerDuration = 2000L
        val interval = 20L
        progressBar.max = (timerDuration / interval).toInt()

        val countDownTimer = object : CountDownTimer(timerDuration, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = ((timerDuration - millisUntilFinished) / interval).toInt()
                progressBar.progress = progress
            }

            override fun onFinish() {
                progressBar.progress = progressBar.max
                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
            }
        }
        countDownTimer.start()
    }

    private fun requestReadPhoneStatePermission() {
        ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                READ_PHONE_STATE_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_PHONE_STATE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
    }
}