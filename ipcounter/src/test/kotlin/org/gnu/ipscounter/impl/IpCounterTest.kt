package org.gnu.ipscounter.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Ignore


class IpCounterTest {

    private val counter = IpCounter(BitSetFilter(1024))

    @Test
    fun `should count unique addresses`() {
        //given:
        val ip1 = "123.255.44.23"
        val ip2 = "123.255.44.55"
        val ip3 = "123.252.44.55"

        //when:
        counter.add(ip1)
        counter.add(ip2)
        counter.add(ip3)
        counter.add(ip1)
        counter.add(ip2)

        //then:
        assertThat(counter.total).isEqualTo(5)
        assertThat(counter.unique).isEqualTo(3)
    }

    @Test
    @Ignore
    fun `check file`() {
        val counter = IpCounter(BitSetFilter())
        val path = ""
        Files.lines(Paths.get(path)).use { lines ->
            lines.forEach { counter.add(it) }
        }
        println("Lines = ${counter.unique}")
    }
}