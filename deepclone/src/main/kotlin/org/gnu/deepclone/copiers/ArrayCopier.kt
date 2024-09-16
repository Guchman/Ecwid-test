package org.gnu.deepclone.copiers

import org.gnu.deepclone.Context
import org.gnu.deepclone.Copier
import java.lang.reflect.Array.newInstance

class ArrayCopier : Copier {
    override fun check(original: Any): Boolean = original is Array<*>

    override fun create(original: Any): Any {
        if (original !is Array<*>) throw IllegalArgumentException()
        return newInstance(original.javaClass.componentType, original.size) as Array<Any>
    }

    override fun initialize(original: Any, copy: Any?, context: Context) {
        if (original !is Array<*>) throw IllegalArgumentException()
        original.forEachIndexed { index, obj -> (copy as Array<Any?>)[index] = context.copy(obj) as Any }
    }
}