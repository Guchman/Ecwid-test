package org.gnu.ipscounter

import org.gnu.ipscounter.impl.BitSetFilter
import org.gnu.ipscounter.impl.IpCounter
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.time.measureTime

fun main(vararg args: String) {
    if (args.isEmpty()) {
        println(
            """
            |Provide a path to the file with ips.
        """.trimMargin()
        )
        return
    }

    val path = args[0]
    println("Starting to calc unique IPs in the file $path")
    val counter = IpCounter(BitSetFilter())
    val took = measureTime {
        Files.lines(Paths.get(path)).use { lines ->
            lines.forEach { counter.add(it) }
        }
    }
    println("Unique IPs = ${counter.unique}, Total IPs = ${counter.total}, took = $took")
}