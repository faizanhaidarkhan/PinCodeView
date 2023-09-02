package com.faizan.pincodeview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import com.faizan.pincodeview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    private var passcode = "";
    private var passcodeLength = 0;
    private var passCodeImageView = arrayOfNulls<AppCompatImageView>(6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        handlePasscodeCalculator()
    }

    private fun handlePasscodeCalculator() {
        binding?.let { binding ->

            val digitButtons = listOf(
                binding.tv1, binding.tv2, binding.tv3, binding.tv4, binding.tv5,
                binding.tv6, binding.tv7, binding.tv8, binding.tv9, binding.tv0
            )

            digitButtons.forEachIndexed { index, button ->
                button.setOnClickListener {
                    if (checkPasswordLength()) return@setOnClickListener
                    passcode += index.toString()
                    handlePasscodeView()
                }
            }

            binding.tvC.setOnClickListener {
                passcode = ""
                handlePasscodeView()
            }

            binding.tvBackspace.setOnClickListener {
                if (passcode.isNotEmpty()) {
                    passcode = passcode.dropLast(1)
                    handlePasscodeView()
                }
            }
        }
    }

    private fun checkPasswordLength(): Boolean {
        if (passcode.length == 6)
            return true
        return false
    }

    private fun handlePasscodeView() {

        //Loop through all image views and set the place holder on all pin views
        for (i in 0..5) {
            val imageViewId = "iv$i"
            val resId: Int =
                resources.getIdentifier(imageViewId, "id", this.packageName)
            passCodeImageView[i] = this.findViewById(resId)
            passCodeImageView[i]?.background = AppCompatResources.getDrawable(this, R.drawable.ic_pin_placeholder)
        }

        passcodeLength = (passcode.length) - 1

        //Set Pin selected view for number of pin figures added
        if (passcode.isNotEmpty()) {
            for (i in 0..passcodeLength) {
                val editTextId = "iv$i"
                val resId: Int =
                    resources.getIdentifier(editTextId, "id", this.packageName)
                passCodeImageView[i] = this.findViewById(resId)
                passCodeImageView[i]?.background = AppCompatResources.getDrawable(this, R.drawable.ic_pin)
            }
        }

        //Id entered passcode's length is 6 then show a toast for entered Pin
        if (passcode.length == 6) {
            Toast.makeText(this, "Passcode  = $passcode", Toast.LENGTH_SHORT).show()
        }
    }
}