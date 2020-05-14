package hope.base.ui.adpter

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.scwang.smart.refresh.layout.api.RefreshLayout

@Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")
class ListAdapterMediator<ITEM, VH : AbstractViewHolder<ITEM>>(
        private val refreshLayout: RefreshLayout,
        private val emptyLayout: View?,
        private val lifecycleOwner: LifecycleOwner,
        private val adapter: ListAdapter<ITEM, VH>,
        private val pageIndexLiveData: MutableLiveData<Int>?,
        private val listResultLiveData: LiveData<out ListInterface<ITEM>?>?,
        private val finish: () -> (Unit),
        private val enableRefresh: Boolean = true,
        private val enableLoadMore: Boolean = true
) {
    init {
        emptyLayout?.visibility = View.GONE
        refreshLayout.setOnRefreshListener {
            refreshNoAnimation()
        }
        refreshLayout.setEnableRefresh(enableRefresh && pageIndexLiveData != null)
        refreshLayout.setEnableLoadMore(enableLoadMore && pageIndexLiveData != null)
        refreshLayout.setOnLoadMoreListener {
            pageIndexLiveData?.postValue(adapter.next)
        }
        listResultLiveData?.observe(lifecycleOwner, Observer {
            if (it != null) {
                if (isRefresh()) {
                    adapter.submitList(it.getRespList())
                    refreshLayout.finishRefresh(0, true, it.noMoreData())
                } else {
                    adapter.addList(it.getRespList())
                    refreshLayout.finishLoadMore(0, true, it.noMoreData())
                }
                adapter.next = it.getNextIndex()
            } else {
                if (isRefresh()) {
                    refreshLayout.finishRefresh(false)
                } else {
                    refreshLayout.finishLoadMore(false)
                }
            }
            doUiSetup()
            finish.invoke()
        })
    }

    fun refreshNoAnimation() {
        refreshLayout.resetNoMoreData()
        adapter.next = 0
        pageIndexLiveData?.postValue(0)
    }


    private fun doUiSetup() {
        refreshLayout.finishRefresh(0, true, adapter.noMore())
        emptyLayout?.visibility = if (adapter.itemCount > 0) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun isRefresh(): Boolean {
        return adapter.noMore()
    }

}