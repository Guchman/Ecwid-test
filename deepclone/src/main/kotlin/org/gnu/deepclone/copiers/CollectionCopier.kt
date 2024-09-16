package org.gnu.deepclone.copiers

import org.gnu.deepclone.Context
import org.gnu.deepclone.Copier

class CollectionCopier : Copier {
    override fun check(original: Any): Boolean = original is Collection<*>

    override fun create(original: Any): Any {
        val javaClass = original.javaClass
        val declaredConstructor = javaClass.getDeclaredConstructor()
        return declaredConstructor.newInstance() as MutableCollection<Any?>
    }

    override fun initialize(original: Any, copy: Any?, context: Context) {
        if (original !is Collection<*>) throw IllegalArgumentException()
        if (copy !is MutableCollection<*>) throw IllegalArgumentException()

        (original as Collection<Any?>).forEach {
            (copy as MutableCollection<Any?>).add(context.copy(it))
        }
    }
}