package org.gnu.deepclone.testclasses

class WithConstructor(var id: Double) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WithConstructor

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.toInt()
    }

    override fun toString(): String {
        return "WithConstructor(id=$id)"
    }

}
