package org.aleks4ay.hotel.run;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by aser on 01.08.2021.
 */
public class RunBundle {
    public static void main(String[] args) {
        Locale locale2 = Locale.forLanguageTag("ru");

        ResourceBundle rb = ResourceBundle.getBundle("/hotel", locale2);
        System.out.println("locale from RB" + rb.getLocale());
    }
}
