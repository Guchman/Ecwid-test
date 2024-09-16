package org.gnu.ipscounter.impl

interface Filter {
    fun append(ip: String): Boolean
}
