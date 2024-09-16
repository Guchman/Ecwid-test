package org.gnu.deepclone.testclasses

class ClassWithEnumField(private val enum: EnumExample?){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClassWithEnumField

        return enum == other.enum
    }

    override fun hashCode(): Int {
        return enum.hashCode()
    }
}
