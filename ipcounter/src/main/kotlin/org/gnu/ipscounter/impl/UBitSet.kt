package org.gnu.ipscounter.impl

import java.util.*
import kotlin.math.abs

class UBitSet(n: Int) {
    private val negative = BitSet(n)
    private val positive = BitSet(n)

    fun isSet(number: Int) =
        bitSet(number).get(abs(number))

    fun set(number: Int) = bitSet(number).set(abs(number))

    private fun bitSet(number: Int) = if (number >= 0) positive else negative
}