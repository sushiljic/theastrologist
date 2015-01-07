package com.theastrologist.rest.controller;

import com.theastrologist.rest.domain.AstroTheme;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SAM on 16/11/2014.
 */
public class ThemeController {

    @Controller
    @RequestMapping("/albums")
    public class AlbumController {
        @RequestMapping(method = RequestMethod.GET)
        public
        @ResponseBody
        AstroTheme getTheme() {
            AstroTheme newTheme = AstroTheme.getNewTheme("Samy", new DateTime("1985-01-04T11:20").toDate());

            return newTheme;
        }
    }
}
