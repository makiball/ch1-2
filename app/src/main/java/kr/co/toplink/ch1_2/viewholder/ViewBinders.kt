package kr.co.toplink.ch1_2.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

typealias ItemClazz = Class<out Any>
typealias MappableItemBinder = MappableItemViewBinder<Any, RecyclerView.ViewHolder>
typealias ItemBinder = BaseItemViewBinder<Any, RecyclerView.ViewHolder>

abstract class MappableItemViewBinder<M, in VH : RecyclerView.ViewHolder> (
    val modelClazz : Class<out M>
) : BaseItemViewBinder<M, VH>()

abstract class BaseItemViewBinder<M, in VH : RecyclerView.ViewHolder> : DiffUtil.ItemCallback<M>() {

    abstract fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun bindViewHolder(model: M, viewHolder: VH)
    abstract fun getItemLayoutResource() : Int

    open fun onViewRecycled(viewHolder: VH) = Unit
    open fun onViewDetachedFromWindow(viewHolder: VH) = Unit

}

inline fun < reified T : ViewDataBinding> ViewGroup.inflate(
    @LayoutRes layout : Int,
    attachToRoot: Boolean = false
): T = DataBindingUtil.inflate<T>(
    LayoutInflater.from(context),
    layout,
    this,
    attachToRoot
)