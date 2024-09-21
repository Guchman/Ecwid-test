package org.gnu.ipscounter.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

private const val BITS_TO_TEST = "bitsToTest"

class UBitSetTest {

    private val bitSet = UBitSet(1024)

    @ParameterizedTest
    @MethodSource(BITS_TO_TEST)
    fun `should set bits`(bit: Int) {
        //when:
        bitSet.set(bit)

        //then:
        assertThat(bitSet.isSet(bit)).isTrue()
    }

    @ParameterizedTest
    @MethodSource(BITS_TO_TEST)
    fun `bits shouldn't be set by default`(bit: Int) {
        //expect:
        assertThat(bitSet.isSet(bit)).isFalse()
    }

    companion object {
        @JvmStatic
        fun bitsToTest() =
            Stream.of(
                0,
                1,
                -1,
                1024,
                -1024,
            ).map { Arguments.of(it) }

    }
}