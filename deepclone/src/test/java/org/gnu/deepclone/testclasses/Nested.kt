package org.gnu.deepclone.testclasses

import kotlin.random.Random

class Nested {
    private val data: Int = Random.nextInt()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Nested

        return data == other.data
    }

    override fun hashCode(): Int {
        return data
    }

    override fun toString(): String {
        return "Nested(data=$data)"
    }

}
