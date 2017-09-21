package gargoyle.ct.log

import gargoyle.ct.log.jul.JULLoggerFactory
import gargoyle.ct.util.getResourceAsStream
import java.io.FileNotFoundException
import java.io.InputStream
import kotlin.reflect.KClass

object LoggerFactoryConfig {
    const val LOGGING_PROPERTIES: String = "logging.properties"
    val loggerFactoryClass: KClass<out ILoggerFactory>
        get() = JULLoggerFactory::class

    val configuration: InputStream
        get() =
            LoggerFactory::class.getResourceAsStream(LOGGING_PROPERTIES)
            { throw FileNotFoundException(LOGGING_PROPERTIES) }
}
