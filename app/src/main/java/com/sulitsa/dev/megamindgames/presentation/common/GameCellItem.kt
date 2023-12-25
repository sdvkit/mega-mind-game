package com.sulitsa.dev.megamindgames.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.sulitsa.dev.megamindgames.databinding.GameCellBinding
import com.sulitsa.dev.megamindgames.domain.model.Gem

class GameCellItem(
    context: Context?,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var binding: GameCellBinding
    private var gem: Gem? = null
    var isClickAvailable: Boolean = true
    var isCellEnabled: Boolean = true

    init {
        val inflater = LayoutInflater.from(context)
        binding = GameCellBinding.inflate(inflater, this, true)
    }

    fun setGem(gem: Gem) {
        this.gem = gem
        binding.cellItemImageView.setImageResource(this.gem!!.imageResId)
        hideGem()
    }

    fun setOnItemClickListener(action: () -> Unit) {
        setOnClickListener {
            val isGemEnabled = gem?.isEnabled ?: return@setOnClickListener

            if (isClickAvailable && isCellEnabled && isGemEnabled) {
                action()
            }
        }
    }

    fun showGem() {
        binding.cellItemImageView.visibility = View.VISIBLE
    }

    fun hideGem() {
        binding.cellItemImageView.visibility = View.INVISIBLE
    }

    fun getGemId(): Int = gem!!.id

    fun setGemEnabled(isEnabled: Boolean) {
        gem!!.isEnabled = isEnabled

        if (!gem!!.isEnabled) {
            visibility = View.INVISIBLE
        }
    }

    fun isGemEnabled(): Boolean = gem!!.isEnabled

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GameCellItem
        return gem?.id == other.gem?.id
    }

    override fun hashCode(): Int {
        var result = binding.hashCode()
        result = 31 * result + (gem?.hashCode() ?: 0)
        result = 31 * result + isClickAvailable.hashCode()
        result = 31 * result + isCellEnabled.hashCode()
        return result
    }
}