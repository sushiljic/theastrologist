package com.theastrologist.rest.domain;

import java.util.Date;

/**
 * Created by SAM on 16/11/2014.
 */
public class AstroTheme {

    private String name;
    private Date dateOfBirth;

    public static AstroTheme getNewTheme(String name, Date dateOfBirth) {
        AstroTheme newTheme = new AstroTheme(name, dateOfBirth);
        return newTheme;
    }

    private AstroTheme(String name, Date dateOfBirth) {

        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
