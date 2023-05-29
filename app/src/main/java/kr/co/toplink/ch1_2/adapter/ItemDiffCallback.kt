package kr.co.toplink.ch1_2.adapter

import androidx.recyclerview.widget.DiffUtil
import kr.co.toplink.ch1_2.viewholder.ItemBinder
import kr.co.toplink.ch1_2.viewholder.ItemClazz
import kr.co.toplink.ch1_2.viewholder.MappableItemBinder


class ItemDiffCallback (
 private val viewBinders: Map<ItemClazz, MappableItemBinder>
) : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem : Any, newItem : Any) : Boolean {
        if(oldItem::class != newItem::class) {
            return false
        }
        return viewBinders[oldItem::class.java]?.areItemsTheSame(oldItem, newItem) ?: false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return viewBinders[oldItem::class.java]?.areContentsTheSame(oldItem, newItem) ?: false
    }
}

internal class SingleTypeDiffCallback(
    private val viewBinder: ItemBinder
) : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if(oldItem::class != newItem::class) {
            return false
        }
        return viewBinder?.areItemsTheSame(oldItem, newItem) ?: false
    }

    override fun areContentsTheSame(oldItem: Any, newItem:Any): Boolean {
        return viewBinder?.areContentsTheSame(oldItem, newItem) ?: false
    }

}
