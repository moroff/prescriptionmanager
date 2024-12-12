package info.moroff.prescriptionmanager.patient;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Simple domain object representing a list of patients. Mostly here to be used for the 'patients' {@link
 * org.springframework.web.servlet.view.xml.MarshallingView}.
 */
@XmlRootElement
public class Patients {

    private List<PatientInfo> patients;

    @XmlElement
    public List<PatientInfo> getPatientList() {
        if (patients == null) {
            patients = new ArrayList<>();
        }
        return patients;
    }

	public void addAll(Collection<Patient> patients) {
		getPatientList().addAll(
			patients.stream().map(p -> {
				PatientInfo pi = new PatientInfo();
				pi.id = p.getId();
				pi.firstName = p.getFirstName();
				pi.lastName = p.getLastName();
				pi.remainingDays = p.getRemainingDays();
				pi.exhaustingDate = p.getExhaustingDate() != null ? Date.valueOf(p.getExhaustingDate()) : null;
				pi.nextAppointmentDate = p.getNextAppointmentDate() != null ? Date.valueOf(p.getNextAppointmentDate()) : null;
				return pi;
			}).collect(Collectors.toList())
		);
		
	}

}
