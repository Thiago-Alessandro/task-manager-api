package net.weg.taskmanager.observability.filter;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.observability.enums.MDCKeys;

@Component
@AllArgsConstructor
public class RequestHeaderFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(RequestHeaderFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        logger.error("ERROR message");
        logger.warn("WARN message");
        logger.info("INFO message");
        logger.debug("DEBUG message");
        logger.trace("TRACE message");
        
//         String logLevel = request.getHeader(MDCKeys.LOG_LEVEL.headerName());
// //        String logUsecase = request.getHeader(MDCKeys.USECASE.headerName());

//         MDC.put(MDCKeys.LOG_LEVEL.mdcKey(), logLevel);

        try {
            filterChain.doFilter(request, response);
        } finally {
            // MDC.clear();
        }
    }
}
