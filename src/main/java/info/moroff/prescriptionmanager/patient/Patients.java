/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.moroff.prescriptionmanager.patient;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
