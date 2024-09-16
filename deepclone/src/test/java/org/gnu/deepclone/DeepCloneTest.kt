package org.gnu.deepclone

import org.assertj.core.api.Assertions.assertThat
import org.gnu.deepclone.testclasses.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DeepCloneTest {

    private val copier = DeepClone()

    @Test
    fun `should copy primitive array`() {
        val original = intArrayOf(2, 3, 4)

        val copy = copier.copy(original)

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should copy nullables`() {
        val original = null

        val copy = copier.copy(original)

        assertThat(copy).isNull()
    }

    @Test
    fun `should copy nullable collections`() {
        val original = mutableListOf(WithConstructor(0.0), null)

        val copy = copier.copy(original)

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should copy String array`() {
        val original = arrayOf("1", "2", "3")

        val copy = copier.copy(original)

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should copy collection`() {
        val original = mutableListOf("1", "2", "3")

        val copy = copier.copy(original)

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should copy objects collection`() {
        val original = mutableListOf(WithConstructor(2.0), WithConstructor(3.0))

        val copy = copier.copy(original) as Collection<Any>

        shouldBeProperCopy(copy, original)
        copy.forEachIndexed { index, obj ->
            shouldBeProperCopy(obj, original[index])
        }
    }

    @Test
    fun `should copy singleton list`() {
        val original = mutableListOf("1")

        val copy = copier.copy(original)

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should copy without default constructor`() {
        val original = WithConstructor(23.0)

        val copy = copier.copy(original)

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should allow own immutables`() {
        val original = arrayOf("1", "2", "3")

        val copy = copier.copy(original)

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should copy Object array`() {
        val original = arrayOf(Nested(), Nested(), Nested())

        val copy = copier.copy(original)

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should copy recursive`() {
        val original = Recursive()
        original.next = original

        val copy = copier.copy(original)

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should copy data class`() {
        val original = DataClass()

        val copy = copier.copy(original)

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should copy class with enums`() {
        val original = ClassWithEnumField(EnumExample.EN)

        val copy = copier.copy(original)

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should copy recursive collections`() {
        val original = mutableListOf<List<Any>>()
        original.add(original)

        val copy = copier.copy(original)

        assertThat(copy).isNotSameAs(original)
        assertThat((copy as List<*>)[0]).isSameAs(copy)
    }

    @Test
    fun `should copy maps`() {
        val commonObject = WithConstructor(1.0)
        val original = mutableMapOf(
            WithConstructor(0.0) to commonObject,
            commonObject to WithConstructor(3.0),
        )

        val copy = copier.copy(original) as Map<Any, Any>

        shouldBeProperCopy(copy, original)
        original.forEach {
            shouldBeProperCopy(it.value, copy[it.key])
        }
    }

    @Test
    fun `should nullable copy maps`() {
        val commonObject = WithConstructor(1.0)
        val original = mutableMapOf(
            WithConstructor(0.0) to commonObject,
            commonObject to WithConstructor(3.0),
            null to null
        )

        val copy = copier.copy(original) as Map<Any?, Any?>

        shouldBeProperCopy(copy, original)
    }

    @Test
    fun `should allow extra immutables`() {
        val original = BigDecimal(1)
        copier.addImmutables(listOf(BigDecimal::class.java))

        val copy = copier.copy(original)

        assertThat(copy).isEqualTo(original)
            .isSameAs(original)
    }


    @Test
    fun `should work with custom processors`() {
        val original = StringBuilder("2")
        copier.addProcessor(object : Copier {
            override fun check(original: Any) = original is StringBuilder

            override fun create(original: Any): Any = StringBuilder(original.toString())

            override fun initialize(original: Any, copy: Any?, context: Context) {
                (copy as StringBuilder).append("+").append(original).append("=5")
            }
        })

        val copy = copier.copy(original)

        assertThat(copy.toString()).isEqualTo("2+2=5")
            .isNotSameAs(original)
    }

    private fun shouldBeProperCopy(copy: Any?, original: Any?) {
        assertThat(copy).isEqualTo(original)
            .isNotSameAs(original)
    }
}