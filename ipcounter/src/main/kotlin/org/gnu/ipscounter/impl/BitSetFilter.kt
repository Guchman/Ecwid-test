package org.gnu.ipscounter.impl

class BitSetFilter(n: Int = Int.MAX_VALUE) : Filter {
    private val bitset = UBitSet(n)
    private val converter = IpConverter()

    override fun append(ip: String): Boolean {
        val numeric = converter.convert(ip)
        if (!bitset.isSet(numeric)) {
            bitset.set(numeric)
            return true
        }
        return false
    }

}