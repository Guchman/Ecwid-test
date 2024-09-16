package org.gnu.deepclone.testclasses

import kotlin.random.Random

class Recursive {
    var next: Recursive? = null
    val id = Random.nextInt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recursive

        return id == other.id
    }

    override fun hashCode(): Int {
        var result = next?.hashCode() ?: 0
        result = 31 * result + id
        return result
    }

    override fun toString(): String {
        return "Recursive(id=$id)"
    }

}