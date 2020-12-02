package com.xiaoneng.ss.account

/**
 * @author Burning
 * @description:
 * @date :2020/12/2 6:01 PM
 */
class MyTest {
    companion object {
        /** 我是main入口函数 **/
        @JvmStatic
        fun main(args: Array<String>) {
            var ss:Array<IntArray> = arrayOf(intArrayOf(5,4),intArrayOf(6,4),intArrayOf(6,7)
                ,intArrayOf(2,3)
                ,intArrayOf(1,1))
            maxEnvelopes(ss)
        }
        fun maxEnvelopes(envelopes: Array<IntArray>): Int {
            var arrs = ArrayList<ArrayList<IntArray>>()
            envelopes.forEach {flag->
                var flag = flag
                var arr = ArrayList<IntArray>()
                arr.add(flag)
                envelopes.forEach {
                    if (it[0]>flag[0]&&it[1]>flag[1]){
                        flag = it
                        arr.add(it)
                    }
                }
                arrs.add(arr)
            }
            var max = 0
            var result = ArrayList<IntArray>()
            arrs.forEach {
                if (it.size>max){
                    max = it.size
                    result = it
                }
            }
            result.forEach {

                println(""+it[0]+" "+it[1])
            }
            return max
        }
    }


}
