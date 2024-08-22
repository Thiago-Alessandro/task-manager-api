package net.weg.taskmanager.observability.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.observability.enums.MDCKeys;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class RequestHeaderFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String logLevel = request.getHeader(MDCKeys.LOG_LEVEL.headerName());
//        String logUsecase = request.getHeader(MDCKeys.USECASE.headerName());

        MDC.put(MDCKeys.LOG_LEVEL.mdcKey(), logLevel);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
