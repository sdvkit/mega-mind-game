package com.sulitsa.dev.megamindgames.presentation.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sulitsa.dev.megamindgames.R
import com.sulitsa.dev.megamindgames.databinding.MenuViewScreenBinding
import com.sulitsa.dev.megamindgames.presentation.injectDependencies
import com.sulitsa.dev.megamindgames.presentation.navigateTo
import com.sulitsa.dev.megamindgames.util.Constants
import com.sulitsa.dev.megamindgames.util.Formatter
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuViewScreen : Fragment() {

    @Inject lateinit var menuViewModel: MenuViewModel
    private lateinit var binding: MenuViewScreenBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MenuViewScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureState()
        configureClickListeners()
        configureCoinsCount()
    }

    private fun configureCoinsCount() {
        val args = arguments ?: return
        val shouldUpdateCoinsCount = args.getBoolean(Constants.SHOULD_UPDATE_COINS_ARG_KEY, false)

        if (shouldUpdateCoinsCount) {
            menuViewModel.onEvent(MenuEvent.GetCoinsCount)
        }
    }

    private fun configureState() {
        lifecycleScope.launch {
            menuViewModel.state.collect { state ->
                configureViewsByState(state)
            }
        }
    }

    private fun configureViewsByState(state: MenuState) {
        binding.coinCountTextView.text = Formatter.formatCoins(coins = state.currentCoinsCount)
    }

    private fun configureClickListeners() {
        with (binding) {
            playButton.setOnClickListener {
                navigateTo(
                    resId = R.id.action_menuViewScreen_to_gameSceneScreen,
                    args = Bundle().apply {
                        putBoolean(Constants.SHOULD_NEW_GAME_ARG_KEY, true)
                    }
                )
            }

            privacyPolicyButton.setOnClickListener {
                Toast.makeText(requireContext(), "Privacy policy", Toast.LENGTH_LONG).show()
            }

            settingButton.setOnClickListener {
                Toast.makeText(requireContext(), "Settings", Toast.LENGTH_LONG).show()
            }
        }
    }
}