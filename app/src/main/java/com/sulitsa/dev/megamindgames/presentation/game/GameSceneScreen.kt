package com.sulitsa.dev.megamindgames.presentation.game

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sulitsa.dev.megamindgames.R
import com.sulitsa.dev.megamindgames.databinding.GameScreenBinding
import com.sulitsa.dev.megamindgames.domain.model.Gem
import com.sulitsa.dev.megamindgames.presentation.common.GameCellItem
import com.sulitsa.dev.megamindgames.presentation.currentNavDestination
import com.sulitsa.dev.megamindgames.presentation.injectDependencies
import com.sulitsa.dev.megamindgames.presentation.navigateTo
import com.sulitsa.dev.megamindgames.util.AnimUtil
import com.sulitsa.dev.megamindgames.util.Constants
import com.sulitsa.dev.megamindgames.util.Formatter
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameSceneScreen : Fragment() {

    @Inject lateinit var gameSceneViewModel: GameSceneViewModel
    private lateinit var binding: GameScreenBinding
    private var gems: List<Gem> = emptyList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GameScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureGameProgress()
        configureState()
        configureTimerAnimation()
    }

    private fun configureGameProgress() {
        val args = arguments ?: return


        val shouldStartNewGame = args.getBoolean(Constants.SHOULD_NEW_GAME_ARG_KEY, false)

        Log.i("____", shouldStartNewGame.toString())

        if (shouldStartNewGame) {
            gameSceneViewModel.onEvent(GameSceneEvent.StartNewGame)
            args.clear()
        }
    }

    private fun configureState() {
        lifecycleScope.launch {
            gameSceneViewModel.state.collect { state ->
                configureViewsByState(state = state)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun configureViewsByState(state: GameSceneState) {
        binding.coinCountTextView.text = Formatter.formatCoins(coins = state.coinsCount)
        binding.timerTextView.text = Formatter.formatSeconds(seconds = state.timerValue)

        checkIfGameIsFinished(state = state)

        if (gems != state.gems) {
            gems = state.gems
            fillGameCellsGrid(gems = gems, savedGameCellItems = state.gameCellItems)
        }

        for (index in 0 until binding.gameCellsGridLayout.childCount) {
            val gameCell = binding.gameCellsGridLayout.getChildAt(index) as GameCellItem
            gameCell.isClickAvailable = state.isUiAvailable
        }
    }

    private fun checkIfGameIsFinished(state: GameSceneState) {
        if (state.isGameFinished && currentNavDestination() == this.javaClass.simpleName) {
            gameSceneViewModel.onEvent(GameSceneEvent.StopTimer)

            navigateTo(
                resId = R.id.action_gameSceneScreen_to_endGamePopupScreen,
                args = Bundle().apply {
                    putInt(Constants.TIMER_VALUE_ARG_KEY, state.timerValue)
                }
            )
        }
    }

    private fun fillGameCellsGrid(gems: List<Gem>, savedGameCellItems: List<GameCellItem>) {
        when (savedGameCellItems.isEmpty()) {
            true -> saveGameCellItems(gems = gems)
            else -> restoreSavedGameCellItems(savedGameCellItems = savedGameCellItems)
        }
    }

    private fun restoreSavedGameCellItems(savedGameCellItems: List<GameCellItem>) {
        savedGameCellItems.forEach { gameCellItem ->
            (gameCellItem.parent as GridLayout).removeView(gameCellItem)
            setGameCellItemLayoutParams(gameCellItem = gameCellItem)
            binding.gameCellsGridLayout.addView(gameCellItem)
        }
    }

    private fun saveGameCellItems(gems: List<Gem>) {
        val gameCellItems: MutableList<GameCellItem> = mutableListOf()

        gems.forEach { gem ->
            val gameCellItem = GameCellItem(context = context)
            setGameCellItemLayoutParams(gameCellItem = gameCellItem)

            gameCellItem.setGem(gem = gem)
            gameCellItem.setOnItemClickListener {
                AnimUtil.playGameCellItemRotationAnim(gameCellItem = gameCellItem)

                val event = GameSceneEvent.TriggerGameCell(
                    gameCell = gameCellItem,
                    ifGemsAreDifferentAction = { gameCellItem1, gameCellItem2 ->
                        AnimUtil.playGameCellItemRotationAnim(gameCellItem = gameCellItem1)
                        AnimUtil.playGameCellItemRotationAnim(gameCellItem = gameCellItem2)
                    }
                )

                gameSceneViewModel.onEvent(event)
            }

            gameCellItems.add(gameCellItem)
            binding.gameCellsGridLayout.addView(gameCellItem)
        }

        val event = GameSceneEvent.SaveGameCellItems(gameCellItems = gameCellItems)
        gameSceneViewModel.onEvent(event)
    }

    private fun setGameCellItemLayoutParams(gameCellItem: GameCellItem) {
        gameCellItem.layoutParams = GridLayout.LayoutParams(
            GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL,1f),
            GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL,1f)
        )
    }

    private fun configureTimerAnimation() {
        AnimUtil.playTimerRotationAnim(
            view = binding.timerImageView,
            context = requireContext()
        )
    }
}