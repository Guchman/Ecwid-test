package org.gnu.deepclone

import org.gnu.deepclone.copiers.ArrayCopier
import org.gnu.deepclone.copiers.CollectionCopier
import org.gnu.deepclone.copiers.MapCopier
import java.lang.invoke.MethodType
import kotlin.reflect.KClass


class DeepClone {
    private companion object {
        val defaultCopiers: List<Copier> = listOf(
            ArrayCopier(),
            CollectionCopier(),
            MapCopier(),
        )
    }

    private val knownImmutables: MutableSet<Class<out Any>> = mutableSetOf<KClass<out Any>>(
        java.lang.Integer::class,
        java.lang.Double::class,
        java.lang.Float::class,
        java.lang.String::class,
        java.lang.Short::class,
        java.lang.Character::class,
        java.lang.Byte::class
    ).asSequence()
        .map { it.java }
        .toMutableSet()

    private val copyProcessors = defaultCopiers.toMutableList()

    fun addImmutables(immutables: Collection<Class<*>>) {
        knownImmutables.addAll(immutables)
    }

    fun copy(obj: Any?): Any? {
        return copy(obj, null)
    }

    fun addProcessor(vararg copiers: Copier) {
        copyProcessors.addAll(copiers)
    }

    internal fun copy(obj: Any?, context: Context?): Any? {
        if (obj == null) return null

        val clazz = obj.javaClass
        if (clazz.isPrimitive)
            return this
        if (obj is String) {
            return obj
        }

        if (clazz.isEnum) {
            return obj
        }

        // non primitives
        if (ofAllowedImmutables(clazz)) {
            return obj
        }

        if (clazz.isArray && clazz.componentType.isPrimitive) {
            return copyArray(obj, clazz)
        }

        val currentContext = context ?: Context(this)

        val copyMethod = clazz.declaredMethods.firstOrNull { it.name == "copy" && it.parameterCount == 0 }
        if (copyMethod != null) {
            return copyMethod.invoke(this)
        }

        val key = System.identityHashCode(obj).toString()
        val cachedObject = currentContext[key]
        if (cachedObject != null) {
            return cachedObject
        } else {
            val copyProcessor = copyProcessors.firstOrNull { it.check(obj) }
            val newObject = copyProcessor?.create(obj) ?: instantiate(obj)
            currentContext[key] = newObject
            copyProcessor?.initialize(obj, newObject, currentContext) ?: initialize(
                newObject,
                obj,
                currentContext
            )
            return newObject
        }
    }

    private fun ofAllowedImmutables(clazz: Class<Any>) = knownImmutables.contains(clazz)

    private fun initialize(
        newObject: Any,
        obj: Any,
        context: Context
    ): Any {
        newObject.javaClass.declaredFields.forEach { field ->
            field.trySetAccessible()
            val value = field.get(obj)
            field.set(newObject, copy(value, context))
        }
        return newObject
    }

    private fun instantiate(obj: Any): Any {
        val clazz = obj.javaClass
        val factory = clazz.constructors.first()
        if (factory.parameterTypes.isEmpty()) {
            return factory.newInstance()
        }
        val params = Array<Any?>(factory.parameterTypes.size) { null }
        factory.parameterTypes.forEachIndexed { index, param ->
            params[index] = if (param.isPrimitive) {
                val declaredConstructors = wrap(param)?.declaredConstructors!!
                declaredConstructors.first {
                    it.parameterCount == 1 && it.parameterTypes.any { param ->
                        param.isPrimitive
                    }
                }?.newInstance(0)
            } else
                null
        }
        return factory.newInstance(*params)
    }

    private fun wrap(clazz: Class<*>): Class<*>? {
        return MethodType.methodType(clazz).wrap().returnType()
    }


    private fun copyArray(obj: Any, clazz: Class<Any>) = when (clazz.componentType) {
        Byte::class.java -> (obj as ByteArray).copyOf(obj.size)
        Int::class.java -> (obj as IntArray).copyOf(obj.size)
        Long::class.java -> (obj as LongArray).copyOf(obj.size)
        Double::class.java -> (obj as DoubleArray).copyOf(obj.size)
        Float::class.java -> (obj as FloatArray).copyOf(obj.size)
        Char::class.java -> (obj as CharArray).copyOf(obj.size)
        Short::class.java -> (obj as ShortArray).copyOf(obj.size)
        else -> throwUnsupported(clazz)
    }

    private fun throwUnsupported(clazz: Class<Any>): Nothing {
        throw IllegalArgumentException("class $clazz doesn't supported yet")
    }

}

class Context(private val cloner: DeepClone) {
    private val objects: MutableMap<String, Any> = mutableMapOf()

    /**
     * Don't use that outside of DeepClone
     * Should be package private
     */
    internal operator fun get(objectIdentity: String) = objects[objectIdentity]

    /**
     * Don't use that outside of DeepClone
     * Should be package private
     */
    internal operator fun set(objectIdentity: String, obj: Any) {
        objects[objectIdentity] = obj
    }

    fun copy(obj: Any?) = cloner.copy(obj, this)
}

