package com.xiaoneng.ss.account

/**
 * @author Burning
 * @description:
 * @date :2020/12/2 6:01 PM
 */
class MyTest {
    companion object {
        var ii = 0

        /** 我是main入口函数 **/
        @JvmStatic
        fun main(args: Array<String>) {
            var ss: ArrayList<IntArray> = arrayListOf(
                intArrayOf(5, 4), intArrayOf(6, 4), intArrayOf(6, 7)
                , intArrayOf(2, 3)
                , intArrayOf(8, 8)
                , intArrayOf(8, 9)
                , intArrayOf(1, 1),
                intArrayOf(2, 3)
                , intArrayOf(8, 8)
                , intArrayOf(8, 9)
                , intArrayOf(1, 1)
            )
            maxEnvelopes(ss)
        }

        fun maxEnvelopes(envelopes: ArrayList<IntArray>): Int {
            var arrs = ArrayList<ArrayList<IntArray>>()
            envelopes.forEach { flag ->
                var r = ArrayList<IntArray>()
                findArrays(flag, flag, r, arrs, envelopes)
            }
            var max = 0
            var result = ArrayList<IntArray>()
            arrs.forEach {
                if (it.size > max) {
                    max = it.size
                    result = it
                }
            }
            result.forEach {

                println("" + it[0] + " " + it[1])
            }
            println(ii)
            return max
        }

        fun findArrays(
            first: IntArray,
            feed: IntArray,
            results: ArrayList<IntArray>,
            arrs: ArrayList<ArrayList<IntArray>>,
            envelopes: ArrayList<IntArray>
        ) {
            var rr = results
            if (results.size > 0) {
                var aa = results.last()
                if ((aa[0] == 8 && aa[1] == 8)
                    || (aa[0] == 8 && aa[1] == 9)
                ) {
                    arrs.add(results)
                    rr = ArrayList<IntArray>()
                    rr.add(first)
                    aa = first
                }
                if (feed[0] > aa[0] && feed[1] > aa[1]) {
                    rr.add(feed)
                }
            } else {
                rr.add(feed)
            }



            ii++
            envelopes.forEach {
                if (it[0] > feed[0] && it[1] > feed[1]) {
                    findArrays(first, it, rr, arrs, envelopes)
                }
            }
//            arrs.add(results)
        }
    }


}
