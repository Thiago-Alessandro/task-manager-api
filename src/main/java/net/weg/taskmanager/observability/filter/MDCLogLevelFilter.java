// package net.weg.taskmanager.observability.filter;

// import ch.qos.logback.classic.Level;
// import ch.qos.logback.classic.Logger;
// import ch.qos.logback.classic.turbo.TurboFilter;
// import ch.qos.logback.core.spi.FilterReply;
// import net.weg.taskmanager.observability.enums.MDCKeys;
// import org.slf4j.MDC;
// import org.slf4j.Marker;
// import org.springframework.stereotype.Component;

// @Component
// public class MDCLogLevelFilter extends TurboFilter {

//     @Override
//     public FilterReply decide(Marker marker, Logger logger, Level level, String message, Object[] params, Throwable t) {

//         String requestLogLevel = MDC.get(MDCKeys.LOG_LEVEL.mdcKey());

//         if (requestLogLevel == null) {
//             return FilterReply.NEUTRAL;
//         }

//         Level requestLevel;
//         Level defaultLevel = Level.valueOf(System.getProperty("DEFAULT_LOG_LEVEL"));
//         try {
//             requestLevel = Level.toLevel(requestLogLevel, defaultLevel);
//         } catch (IllegalArgumentException e) {
//             requestLevel = defaultLevel;
//         }

//         if (level.isGreaterOrEqual(requestLevel)) {
//             return FilterReply.ACCEPT;
//         } else {
//             return FilterReply.DENY;
//         }
//     }
// }
