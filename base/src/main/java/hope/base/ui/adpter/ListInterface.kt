package hope.base.ui.adpter

import androidx.annotation.Keep

@Keep
interface ListInterface<ITEM> {

    fun getRespList(): List<ITEM>

    fun getNextIndex() : Int
}

fun <ITEM> ListInterface<ITEM>.noMoreData(): Boolean {
    return this.getNextIndex() == 0
}