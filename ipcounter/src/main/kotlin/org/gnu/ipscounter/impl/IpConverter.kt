package org.gnu.ipscounter.impl

class IpConverter {
    fun convert(ip: String): Int {
        var number = 0
        var segment = 0
        for (ch in ip) {
            if (ch == '.') {
                number = setSegment(number, segment)
                segment = 0
            } else {
                segment = segment * 10 + (ch.digitToInt())
            }
        }
        return setSegment(number, segment)
    }

    private fun setSegment(number: Int, segment: Int) = (number shl 8) or segment
}