@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(type = LocalDate.class, 
                        value = LocalDateAdapter.class)
})
package info.moroff.prescriptionmanager.patient;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import info.moroff.prescriptionmanager.util.LocalDateAdapter;