package com.sulitsa.dev.megamindgames.presentation.endgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sulitsa.dev.megamindgames.databinding.EndGamePopupBinding
import com.sulitsa.dev.megamindgames.util.Constants

class EndGamePopupScreen : Fragment() {

    private lateinit var binding: EndGamePopupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EndGamePopupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.timerValue.text = requireArguments().getInt(Constants.TIMER_VALUE_ARG_KEY).toString()
    }
}