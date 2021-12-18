package com.stellagosa.demo.security.rememberme.controller;

import com.stellagosa.demo.security.rememberme.utils.ImageCode;
import com.stellagosa.demo.security.rememberme.utils.ValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ValidateCodeController {

    @GetMapping("/code/image")
    public void validateImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ImageCode image = ValidateCode.createImage();
        request.getSession().setAttribute("imageCode", image);
        ImageIO.write(image.getImage(), "jpeg", response.getOutputStream());
    }
}
