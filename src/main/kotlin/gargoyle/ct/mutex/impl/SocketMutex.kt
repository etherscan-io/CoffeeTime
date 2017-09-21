package gargoyle.ct.mutex.impl

import gargoyle.ct.log.Log
import gargoyle.ct.mutex.CTMutex
import java.io.IOException
import java.net.ServerSocket

class SocketMutex(private val port: Int) : CTMutex {
    private lateinit var mutex: ServerSocket

    @Synchronized
    override fun acquire(): Boolean =
        try {
            mutex = ServerSocket(port)
            true
        } catch (ex: IOException) {
            false
        }

    @Synchronized
    override fun release() {
        try {
            //                mutex = null
            if (::mutex.isInitialized && !mutex.isClosed) mutex.close()
        } catch (ex: IOException) {
            Log.error(ex, "socket mutex error")
        }
    }

}
