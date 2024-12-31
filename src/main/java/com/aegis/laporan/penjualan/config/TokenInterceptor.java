package com.aegis.laporan.penjualan.config;

import com.aegis.laporan.penjualan.constant.ApplicationEnum;
import com.aegis.laporan.penjualan.constant.Secured;
import com.aegis.laporan.penjualan.exception.AegisException;
import com.aegis.laporan.penjualan.exception.ResourceForbiddenException;
import com.aegis.laporan.penjualan.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    Environment env;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        try {
            if (request.getMethod().equalsIgnoreCase("options")) {
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
            String invalid = "Invalid token";
            String expired = "Token expired";
            String jwtToken = request.getHeader("Authorization");

            String[] splitString = jwtToken.split("\\.");
            String base64EncodedBody = splitString[1];
            Base64 base64Url = new Base64(true);
            String body = new String(base64Url.decode(base64EncodedBody));
            Map<String, String> bodyToken = new Gson().fromJson(body, Map.class);

            String role = bodyToken.get("Role");
            String nik = bodyToken.get("User");

            DecimalFormat format = new DecimalFormat("###");
            format.setMinimumIntegerDigits(0);
            format.setMaximumFractionDigits(2000);
            String expString = format.format(bodyToken.get("exp"));
            Date exp = Date.from(Instant.ofEpochSecond(Long.parseLong(expString)));
            if (exp.getTime() < Calendar.getInstance().getTimeInMillis()) {
                throw new AegisException(404, expired);
            }

            HandlerMethod method = (HandlerMethod) handler;
            Secured anno = method.getMethodAnnotation(Secured.class);

            boolean allowed;
            try {
                ApplicationEnum.Group group = ApplicationEnum.Group.valueOf(role);
                allowed = anno == null || anno.value().length == 0 || Arrays.asList(anno.value()).contains(group);
            } catch (Exception e) {
                throw UnauthorizedException.create("Invalid Group ID");
            }

            if (!allowed) {
                throw ResourceForbiddenException.create("Forbidden Access");
            }

        } catch (NullPointerException e) {
            throw new UnauthorizedException("Unauthorized access");
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}