package com.yang.common_lib.helper


/**
 * @ClassName MoveAndSwipedListener
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/30 13:55
 */
interface MoveAndSwipedListener {

    fun onDelete(index: Int)

    fun onMoved(startIndex: Int, endIndex: Int)
}