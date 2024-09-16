package org.gnu.ipscounter.impl

class IpCounter(private val filter: Filter) {
    var unique: Long = 0
        private set
    var total: Long = 0
        private set

    fun add(ip: String) {
        total++
        if (filter.append(ip)) {
            unique++
        }
    }
}