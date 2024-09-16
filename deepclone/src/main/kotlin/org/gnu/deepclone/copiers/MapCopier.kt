package org.gnu.deepclone.copiers

import org.gnu.deepclone.Context
import org.gnu.deepclone.Copier

class MapCopier : Copier {
    override fun check(original: Any): Boolean = original is Map<*, *>

    override fun create(original: Any): Any? {
        val javaClass = original!!.javaClass
        val declaredConstructor = javaClass.getDeclaredConstructor()
        return declaredConstructor.newInstance()
    }

    override fun initialize(original: Any, copy: Any?, context: Context) {
        if (original !is Map<*, *>) throw IllegalArgumentException()
        if (copy !is MutableMap<*, *>) throw IllegalArgumentException()

        original.forEach {
            (copy as MutableMap<Any?, Any?>)[context.copy(it.key)] = context.copy(it.value)
        }
    }

}