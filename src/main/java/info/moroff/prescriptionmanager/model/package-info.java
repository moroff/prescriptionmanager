@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class),
    @XmlJavaTypeAdapter(type = Date.class, value=DateAdapter.class),
})
/**
 * The classes in this package represent utilities used by the domain.
 */
package info.moroff.prescriptionmanager.model;

import java.time.LocalDate;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import info.moroff.prescriptionmanager.util.DateAdapter;
import info.moroff.prescriptionmanager.util.LocalDateAdapter;