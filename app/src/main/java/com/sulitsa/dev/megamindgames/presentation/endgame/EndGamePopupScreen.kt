package com.sulitsa.dev.megamindgames.presentation.endgame

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sulitsa.dev.megamindgames.R
import com.sulitsa.dev.megamindgames.databinding.EndGamePopupBinding
import com.sulitsa.dev.megamindgames.presentation.injectDependencies
import com.sulitsa.dev.megamindgames.presentation.navigateTo
import com.sulitsa.dev.megamindgames.util.Constants
import com.sulitsa.dev.megamindgames.util.Formatter
import kotlinx.coroutines.launch
import javax.inject.Inject

class EndGamePopupScreen : Fragment() {

    @Inject lateinit var endGamePopupViewModel: EndGamePopupViewModel
    private lateinit var binding: EndGamePopupBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

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
        configureCoinsResult()
        configureState()
        configureButtons()
    }

    private fun configureButtons() {
        with (binding) {
            homeButton.setOnClickListener {
                val event = EndGamePopupEvent.UpgradeCoinsValue
                endGamePopupViewModel.onEvent(event)

                navigateTo(
                    resId = R.id.action_endGamePopupScreen_to_menuViewScreen,
                    args = Bundle().apply {
                        putBoolean(Constants.SHOULD_UPDATE_COINS_ARG_KEY, true)
                    }
                )
            }

            doubleRewardButton.setOnClickListener {
                val event = EndGamePopupEvent.UpgradeCoinsValue
                endGamePopupViewModel.onEvent(event)
                endGamePopupViewModel.onEvent(event)

                Toast.makeText(requireContext(), "Reward doubled!", Toast.LENGTH_LONG).show()

                navigateTo(
                    resId = R.id.action_endGamePopupScreen_to_menuViewScreen,
                    args = Bundle().apply {
                        putBoolean(Constants.SHOULD_UPDATE_COINS_ARG_KEY, true)
                    }
                )
            }
        }
    }

    private fun configureState() {
        lifecycleScope.launch {
            endGamePopupViewModel.state.collect { state ->
                configureViewsByState(state = state)
            }
        }
    }

    private fun configureViewsByState(state: EndGamePopupState) {
        binding.coinCountTextView.text = Formatter.formatCoins(state.coinsCount)
    }

    private fun configureCoinsResult() {
        val seconds = requireArguments().getInt(Constants.TIMER_VALUE_ARG_KEY)
        val event = EndGamePopupEvent.CalculateCoins(seconds = seconds)
        endGamePopupViewModel.onEvent(event)
    }
}