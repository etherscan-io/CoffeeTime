module CoffeeTime {
    requires kotlin.stdlib;
    requires kotlin.stdlib.jdk8;
    requires kotlin.reflect;
    requires java.desktop;
    requires java.logging;
    requires java.prefs;

    exports gargoyle.ct.log.jul to kotlin.reflect;
}
