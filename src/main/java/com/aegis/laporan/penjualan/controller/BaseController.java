package com.aegis.laporan.penjualan.controller;

import com.aegis.laporan.penjualan.common.CommonRs;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

public class BaseController {

    public ResponseEntity<Object> success(Object data) {
        return new ResponseEntity(new CommonRs(HttpStatus.OK.value(), "success", data), HttpStatus.OK);
    }

    public ResponseEntity<Object> success() {
        return new ResponseEntity(new CommonRs(HttpStatus.OK.value(), "success"), HttpStatus.OK);
    }
    public ResponseEntity<Resource> okDownload(String filename, String mediaType, byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        return ResponseEntity.ok().headers(headers).contentLength(data.length).contentType(MediaType.parseMediaType(mediaType)).body(new ByteArrayResource(data));
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