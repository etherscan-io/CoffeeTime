package gargoyle.ct.config.convert.impl

import gargoyle.ct.config.convert.CTUnitConverter
import gargoyle.ct.util.CTTimeUtil
import java.text.MessageFormat
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class CTConfigDataConverter : CTUnitConverter<LongArray> {
    override fun format(unit: TimeUnit, vararg data: Long): String {
        val unitChar: String = when (unit) {
            TimeUnit.HOURS -> UNIT_HOURS
            TimeUnit.MINUTES -> UNIT_MINUTES
            TimeUnit.SECONDS -> UNIT_SECONDS
            TimeUnit.DAYS, TimeUnit.MILLISECONDS, TimeUnit.MICROSECONDS, TimeUnit.NANOSECONDS ->
                throw UnsupportedOperationException("Cannot parse line, invalid time unit ${unit.name}")

            else ->
                throw UnsupportedOperationException("Cannot parse line, invalid time unit ${unit.name}")
        }
        return MessageFormat.format(
            PATTERN_FORMAT,
            CTTimeUtil.fromMillis(unit, data[0]),
            unitChar,
            CTTimeUtil.fromMillis(unit, data[1]),
            unitChar,
            CTTimeUtil.fromMillis(unit, data[2]),
            unitChar
        )
    }

    override fun parse(data: String): LongArray {
        require(data.isNotBlank()) { MSG_EMPTY_LINE }
        val trimmedLine = data.trim()
        require(!COMMENTS.contains(trimmedLine[0])) { ("Commented line: $data") }
        val longs = LongArray(3)
        val m = PATTERN_PARSE.matcher(trimmedLine)
        if (m.find()) {
            val groupCount = m.groupCount()
            require(groupCount == 6) { "Cannot parse line: $data" }
            var g = 1
            while (g <= groupCount) {
                val q = m.group(g)
                val unit: TimeUnit = when (val u = m.group(g + 1)) {
                    UNIT_HOURS -> TimeUnit.HOURS
                    UNIT_MINUTES -> TimeUnit.MINUTES
                    UNIT_SECONDS -> TimeUnit.SECONDS
                    else -> throw IllegalArgumentException("Cannot parse line: ${data}, invalid time unit $u")
                }
                try {
                    longs[g / 2] = CTTimeUtil.toMillis(unit, q.toLong())
                } catch (ex: NumberFormatException) {
                    throw IllegalArgumentException("Cannot parse line: $data", ex)
                }
                g += 2
            }
        } else {
            throw IllegalArgumentException(data)
        }
        return longs
    }

    companion object {
        private const val COMMENTS = "#;'"
        private const val MSG_EMPTY_LINE = "Empty line"
        private const val PATTERN_FORMAT = "{0}{1}/{2}{3}/{4}{5}"
        private val PATTERN_PARSE = Pattern.compile(
            "(?:([0-9]+)([a-zA-Z]+))/(?:([0-9]+)([a-zA-Z]+))[^a-zA-Z0-9]+(?:([0-9]+)([a-zA-Z]+))",
            Pattern.CASE_INSENSITIVE
        )
        private const val UNIT_HOURS = "H"
        private const val UNIT_MINUTES = "M"
        private const val UNIT_SECONDS = "S"
    }
}