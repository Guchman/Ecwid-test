package org.gnu.ipscounter.impl

import java.util.*
import kotlin.math.abs

@Deprecated("Not optimal implementation")
class BloomFilter(n: Int) : Filter {
    private val seeds = arrayOf(
        7, 11, 13, 17, 19
    )
    private val bits = BitSet(n)

    private val hashFunction: (String, Int) -> Long =
        { str, seed ->
            val m = (1e9 + 9).toInt()
            var hashValue: Long = 0
            var pPow: Long = 1
            for (c in str) {
                hashValue = (hashValue + (c - 'a' + 1) * pPow) % m
                pPow = (pPow * seed) % m
            }
            hashValue
        }


    override fun append(ip: String): Boolean {
        if (contains(ip)) return false
        seeds.forEach {
            val bit = getBit(ip, it)
            bits.set(bit)
        }
        return true
    }

    private fun getBit(str: String, seed: Int): Int {
        val hash = hashFunction.invoke(str, seed)
        return abs(hash % (bits.size() - 1)).toInt()
    }

    fun contains(str: String): Boolean {
        return seeds.any {
            bits.get(getBit(str, it))
        }
    }
}