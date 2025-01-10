package com.aegis.laporan.penjualan.controller;

import com.aegis.laporan.penjualan.common.CommonRs;
import com.aegis.laporan.penjualan.dto.response.LoginResponse;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;

public class BaseController {

    public ResponseEntity<Object> success(Object data) {
        return new ResponseEntity(new CommonRs(HttpStatus.OK.value(), "success", data), HttpStatus.OK);
    }

    public ResponseEntity<Object> success() {
        return new ResponseEntity(new CommonRs(HttpStatus.OK.value(), "success"), HttpStatus.OK);
    }
    public ResponseEntity<Object> error(HttpStatus httpStatus, Object message) {
        return new ResponseEntity<>(new CommonRs(httpStatus.value(), "error", message), httpStatus);
    }
    public ResponseEntity<Resource> okDownload(String filename, String mediaType, byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        return ResponseEntity.ok().headers(headers).contentLength(data.length).contentType(MediaType.parseMediaType(mediaType)).body(new ByteArrayResource(data));
    }

    public ResponseEntity<Object> okLogin(ResponseCookie jwtCookie, LoginResponse data) {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(data);
    }

    public Pageable pageFromRequest(int page, int size, String sort, Boolean asc) {
        return ObjectUtils.isEmpty(sort) ? PageRequest.of(page, size) : PageRequest.of(page, size, Sort.by(new Sort.Order[]{this.getSortBy(sort, asc, true)}));
    }

    public Sort.Order getSortBy(String sort, Boolean asc, Boolean ignoreCase) {
        if (Boolean.FALSE.equals(ignoreCase)) {
            return Boolean.TRUE.equals(asc) ? Sort.Order.asc(sort) : Sort.Order.desc(sort);
        } else {
            return Boolean.TRUE.equals(asc) ? Sort.Order.asc(sort).ignoreCase() : Sort.Order.desc(sort).ignoreCase();
        }
    }
}
