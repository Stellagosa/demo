package com.stellagosa.demo.security.rememberme.utils;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ImageCode implements Serializable {

    private static final long serialVersionUID = -9103570302541194254L;

    private BufferedImage image;
    private String code;
    private LocalDateTime expireInt;

    public ImageCode(BufferedImage image, String code, int expireInt) {
        this.image = image;
        this.code = code;
        this.expireInt = LocalDateTime.now().plusSeconds(expireInt);
    }
    public boolean isExpired() {
        return this.expireInt.isBefore(LocalDateTime.now());
    }
}
