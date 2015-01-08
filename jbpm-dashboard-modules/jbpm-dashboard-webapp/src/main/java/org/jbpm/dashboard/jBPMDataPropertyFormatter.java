/**
 * Copyright (C) 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.dashboard;

import java.util.*;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.jboss.dashboard.LocaleManager;
import org.jboss.dashboard.annotation.Install;
import org.jboss.dashboard.provider.DataPropertyFormatterImpl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Install
public class jBPMDataPropertyFormatter extends DataPropertyFormatterImpl {

    /** The no value text. */
    private final static String NO_VALUE = "---";

    /** The number 0.. */
    private final static Double DOUBLE_ZERO = new Double(0);

    @Inject
    protected LocaleManager localeManager;

    public String[] getSupportedPropertyIds() {
        return new String[] {"min(duration)", "avg(duration)", "max(duration)",
                "min(ts.duration)", "avg(ts.duration)", "max(ts.duration)",
                "status", "userid"};
    }
    
    public String formatValue(String propertyId, Object value, Locale l) {
        if (value instanceof Collection) {
            return super.formatCollection(propertyId, (Collection) value, ", ", null, null, l);
        }
        if (propertyId.indexOf("duration") != -1) {
            return formatValue_duration(value, l);
        }
        if (propertyId.equals("status")) {
            return formatValue_status(value, l);
        }
        if (propertyId.equals("userid")) {
            return formatValue_userid(value, l);
        }
        return super.formatValue(propertyId, value, l);
    }

    /**
     * Format the property <code>duration</code>..
     * If duration value is <code>null</code> or equals to <code>0</code>, means
     * the task is not completed yet and the duration is undefined.
     *
     * @param value The property value.
     * @param l The locale
     * @return The duration formatted as String.
     * @throws Exception An error ocurred.
     */
    public String formatValue_duration(Object value, Locale l) {
        if (value == null || !(value instanceof Number)) return NO_VALUE;

        // If task is not finished, duration is 0.
        if (value != null && DOUBLE_ZERO.equals(value)) return NO_VALUE;

        Number lengthInSeconds = (Number) value;
        long millis = lengthInSeconds.longValue();
        if (millis < 0) millis = 0;
        return formatElapsedTime(millis, l);
    }

    public String formatValue_status(Object value, Locale l) {
        try {
            if (value == null) return "---";
            ResourceBundle i18n = getBundle(l);
            return i18n.getString("status." + value.toString());
        } catch (Exception e) {
            return value.toString();
        }
    }

    /**
     * Format the property <code>userid</code> used in task assigments.
     * If no user assigned use a custom literal.
     *
     * @param value The property value.
     * @param l The locale
     * @return The user property formatted as String.
     * @throws Exception An error ocurred.
     */
    public String formatValue_userid(Object value, Locale l) {
        try {
            // Check if a user is assigned.
            if (value != null) {
                String valueStr = (String) value;
                if (valueStr.trim().length() > 0) return value.toString();
            }

            // Not assigned, so use a custom literal.
            ResourceBundle i18n = getBundle(l);
            return i18n.getString("user.notAssinged");

        } catch (Exception e) {
            return value.toString();
        }
    }



    public String formatElapsedTime(long millis, Locale l) {
        long milliseconds = millis;
        long seconds = milliseconds / 1000; milliseconds %= 1000;
        long minutes = seconds / 60; seconds %= 60;
        long hours = minutes / 60; minutes %= 60;
        long days = hours / 24; hours %= 24;
        long weeks = days / 7; days %= 7;

        ResourceBundle i18n = getBundle(l);
        String pattern = "ellapsedtime.hours";
        if (days > 0) pattern = "ellapsedtime.days";
        if (weeks > 0) pattern = "ellapsedtime.weeks";
        return MessageFormat.format(i18n.getString(pattern), new Long[] {new Long(seconds), new Long(minutes), new Long(hours), new Long(days), new Long(weeks)});
    }

    /**
     * Get the associated resource bundle.
     *
     * @param l The locale.
     * @return The bundle for the locale.
     */
    protected ResourceBundle getBundle(Locale l) {
        return localeManager.getBundle("org.jbpm.dashboard.messages", l);
    }
}

