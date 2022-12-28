package com.genome.parpalak.controllers;

import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager=true)
@ApplicationScoped
public class LocaleChanger implements Serializable {

    private Locale currentLocale = new Locale("ru");
    
    private static final Logger LOGGER = Logger.getLogger(LocaleChanger.class.getName());

    public LocaleChanger() {
        LOGGER.log(Level.INFO, "Locale Changer has been initialized...");
    }

    public void changeLocale(String localeCode) {
        currentLocale = new Locale(localeCode);
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
}
