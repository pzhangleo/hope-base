package hope.base.ui.adpter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class ListAdapter<ITEM, VH: AbstractViewHolder<ITEM>>
    : RecyclerView.Adapter<VH>() {

    private val data: MutableList<ITEM> = ArrayList()

    var next = 0

    fun noMore(): Boolean {
        return next == 0
    }

    fun submitList(list: List<ITEM>) {
        next = 0
        this.data.clear()
        this.data.addAll(list)
        notifyDataSetChanged()
    }

    fun addList(list: List<ITEM>) {
        val position = this.data.size
        this.data.addAll(list)
        notifyItemInserted(position)
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(data[position], position)
    }

}

abstract class AbstractViewHolder<ITEM>(v: View)
    : RecyclerView.ViewHolder(v) {

    abstract fun bindData(item: ITEM, position: Int)

}