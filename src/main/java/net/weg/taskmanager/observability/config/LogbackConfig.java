package net.weg.taskmanager.observability.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.util.StatusPrinter;
import net.weg.taskmanager.observability.filter.MDCLogLevelFilter;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LogbackConfig implements LoggerContextListener {

    @EventListener(ApplicationReadyEvent.class)
    public void configureLogback() {
        ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
        if (iLoggerFactory instanceof LoggerContext) {
            LoggerContext context = (LoggerContext) iLoggerFactory;
            configureDefaultContext(context);
            context.addListener(this);
        }
    }

    private void configureDefaultContext(LoggerContext context) {
        context.addTurboFilter(new MDCLogLevelFilter());
        StatusPrinter.print(context);
    }

    @Override
    public boolean isResetResistant() {
        return true;
    }

    @Override
    public void onStart(LoggerContext context) {
    }

    @Override
    public void onReset(LoggerContext context) {
        configureDefaultContext(context);
    }

    @Override
    public void onStop(LoggerContext context) {
    }

    @Override
    public void onLevelChange(Logger logger, Level level) {
    }
}
