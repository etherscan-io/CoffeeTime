package gargoyle.ct.log.jul

import gargoyle.ct.log.ILoggerFactory
import gargoyle.ct.log.Logger
import java.io.InputStream
import java.util.logging.LogManager

class JULLoggerFactory : ILoggerFactory {
    override fun getLogger(name: String): Logger = JULLogger(name)

    override fun configure(stream: InputStream): Unit = LogManager.getLogManager().readConfiguration(stream)
}
