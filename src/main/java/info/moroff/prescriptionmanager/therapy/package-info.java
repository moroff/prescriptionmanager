@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class),
    @XmlJavaTypeAdapter(type = Periodicity.class, value=PeriodicityAdapter.class),
    @XmlJavaTypeAdapter(type = Date.class, value=DateAdapter.class),
})
package info.moroff.prescriptionmanager.therapy;

import java.time.LocalDate;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import info.moroff.prescriptionmanager.model.Periodicity;
import info.moroff.prescriptionmanager.model.PeriodicityAdapter;
import info.moroff.prescriptionmanager.util.DateAdapter;
import info.moroff.prescriptionmanager.util.LocalDateAdapter;