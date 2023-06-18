package com.muhammed.animatedloginform.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import app.rive.runtime.kotlin.core.Direction
import app.rive.runtime.kotlin.core.Loop
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.muhammed.animatedloginform.R
import com.muhammed.animatedloginform.databinding.FragmentLoginBinding
import es.dmoral.toasty.Toasty

/**
 * Created by Muhammed Elşami on 18.06.23.
 */

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private var isPasswordVisible = false

    private lateinit var mailLayout: TextInputLayout
    private lateinit var passLayout: TextInputLayout

    private lateinit var mailEditText: TextInputEditText
    private lateinit var passEditText: TextInputEditText

    private lateinit var loginButton: AppCompatButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {
        passEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.animate.play("hands_down", Loop.ONESHOT, Direction.BACKWARDS)
            } else {
                binding.animate.play("Hands_up", Loop.ONESHOT, Direction.BACKWARDS)
            }
        }

        passLayout.setEndIconOnClickListener {
            togglePassVisibility()
        }

        mailEditText.addTextChangedListener {
            binding.animate.play("Login Machine", Loop.AUTO, Direction.AUTO, true)
            binding.animate.setBooleanState("Login Machine", "isChecking", true)
            (it?.length?.times(1.5))?.toFloat()
                ?.let { it1 -> binding.animate.setNumberState("Login Machine", "numLook", it1) }
        }

        binding.animate.setOnTouchListener { view, _ ->
            hideKeyboard(view)
            clearFocus()
            true
        }

        passEditText.setOnEditorActionListener { tView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER) {
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(tView.windowToken, 0)
                passEditText.clearFocus()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun togglePassVisibility() {
        if (isPasswordVisible) {
            val pass: String = passEditText.text.toString()
            passEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            passEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            passEditText.setText(pass)
            passEditText.setSelection(pass.length)
            binding.animate.play("hands_down", Loop.ONESHOT, Direction.BACKWARDS)

        } else {
            val pass: String = passEditText.text.toString()
            passEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passEditText.inputType = InputType.TYPE_CLASS_TEXT
            passEditText.setText(pass)
            passEditText.setSelection(pass.length)
            binding.animate.play("Hands_up", Loop.ONESHOT, Direction.BACKWARDS)
        }
        isPasswordVisible = !isPasswordVisible
    }

    private fun initView() {
        mailLayout = binding.emailLayout
        passLayout = binding.passwordLayout
        mailEditText = binding.emailInputId
        passEditText = binding.passwordInputId
        loginButton = binding.loginBtn

        loginButton.setOnClickListener(this)
        binding.container.setOnClickListener(this)
        binding.registerBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.loginBtn -> {
                login()
            }

            R.id.container -> {
                onClickOutside()
            }

            R.id.registerBtn -> {
                register()
            }
        }
    }

    private fun register() {
        findNavController().navigate(R.id.homeFragment)
    }

    private fun login() {
        binding.animate.pause()
        if (passEditText.isFocused) {
            binding.animate.play("Hands_up", Loop.ONESHOT, Direction.BACKWARDS)
            passEditText.clearFocus()
        }

        if (mailEditText.text.toString() == "muhammed" && passEditText.text.toString() == "1234"
        ) {
            Toasty.success(requireContext(), "Correct UserName and Password").show()
            binding.animate.play("success", Loop.AUTO, Direction.AUTO)

            // go to home fragment after 2 seconds
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().popBackStack(R.id.loginFragment, true)
                findNavController().navigate(R.id.homeFragment)
            }, 2000)

        } else {
            Toasty.error(requireContext(), "Wrong UserName or Password").show()
            binding.animate.play("fail", Loop.ONESHOT, Direction.BACKWARDS)
        }
    }

    private fun onClickOutside() {
        hideKeyboard(binding.container)
        clearFocus()
    }

    private fun clearFocus() {
        mailEditText.clearFocus()
        passEditText.clearFocus()
        binding.animate.setBooleanState("Login Machine", "isChecking", false)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
