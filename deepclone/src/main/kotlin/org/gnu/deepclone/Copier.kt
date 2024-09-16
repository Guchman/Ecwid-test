package org.gnu.deepclone

interface Copier {
    /**
     * Should return true if copier want to copy this object
     */
    fun check(original: Any): Boolean

    /**
     * Should return created object
     */
    fun create(original: Any): Any?

    /**
     * Should set properties to copied object if that haven't done it yet at creation phase.
     * Separation of creation and initialization required to prevent cyclic object creation
     * on self refs.
     */
    fun initialize(original: Any, copy: Any?, context: Context)
}