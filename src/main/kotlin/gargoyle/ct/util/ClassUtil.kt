package gargoyle.ct.util

import java.io.InputStream
import java.net.URL
import kotlin.reflect.KClass

val KClass<*>.className: String
    get() = this.qualifiedName!!
val KClass<*>.simpleClassName: String
    get() = this.simpleName!!

fun KClass<*>.getResource(name: String, onError: () -> Nothing = { error("NOT FOUND $name") }): URL =
    java.classLoader.getResource(getResourceName(name)) ?: onError()

fun KClass<*>.getResourceAsStream(name: String, onError: () -> Nothing = { error("NOT FOUND $name") }): InputStream =
    java.classLoader.getResource(getResourceName(name))?.openStream() ?: onError()

fun KClass<*>.getResourceName(name: String): String {
    return if (name.startsWith('/')) name.trimStart('/') else "${
        qualifiedName!!.replace('.', '/').substringBeforeLast('/')
    }/$name"
}


val KClass<*>.classLoader: ClassLoader
    get() = this.java.classLoader
