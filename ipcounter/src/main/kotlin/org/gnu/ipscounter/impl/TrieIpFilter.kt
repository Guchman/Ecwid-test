package org.gnu.ipscounter.impl

@Deprecated("Not optimal implementation")
class TrieIpFilter : Filter {
    private val head = TrieNode()
    override fun append(ip: String): Boolean {
        val segments = ip.split(".").map { it.toUByte() }
        var pointer = head
        var added = false
        segments
            .forEachIndexed { i, value ->
                if (pointer.children[value] == null) {
                    pointer.children[value] = TrieNode()
                    added = i + 1 == segments.size
                }
                pointer = pointer.children[value]!!
            }
        return added
    }

    class TrieNode {
        val children: MutableMap<UByte, TrieNode> = mutableMapOf()
    }
}


