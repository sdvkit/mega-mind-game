package com.sulitsa.dev.megamindgames.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import com.sulitsa.dev.megamindgames.R
import com.sulitsa.dev.megamindgames.presentation.common.GameCellItem

object AnimUtil {

    fun playTimerRotationAnim(view: View, context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.timer_rotation_anim)
        view.startAnimation(animation)
    }

    fun playGameCellItemRotationAnim(gameCellItem: GameCellItem) {
        val rotationAnimator = ObjectAnimator.ofFloat(gameCellItem, "rotationY", 0f, 180f)
        rotationAnimator.duration = 400
        rotationAnimator.interpolator = AccelerateDecelerateInterpolator()

        val animatorSet = AnimatorSet()
        animatorSet.play(rotationAnimator)
        animatorSet.start()
    }
}