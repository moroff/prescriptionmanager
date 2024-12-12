package info.moroff.prescriptionmanager.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {
	private final PatientRepository repository;
	
	@Autowired
	public PatientService(PatientRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public Patient findById(int patientId) {
		return repository.findById(patientId);
	}

	@Transactional
	public Patient save(Patient patient) {
		repository.save(patient);
		return patient;
	}
}
