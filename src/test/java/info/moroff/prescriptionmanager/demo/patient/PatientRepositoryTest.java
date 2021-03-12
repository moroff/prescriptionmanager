package info.moroff.prescriptionmanager.demo.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import info.moroff.prescriptionmanager.patient.Patient;
import info.moroff.prescriptionmanager.patient.PatientRepository;

@SpringBootTest
public class PatientRepositoryTest {

	@Autowired
	private PatientRepository patientRepo;
	
	@Test
	public void test() {
		assertNotNull(patientRepo);
	}

	@Test
	public  void testFindAll() {
		assumeTrue(patientRepo != null);

		assertFalse(patientRepo.findAll().isEmpty());
	}
	
	@Test
	public void testReadFirst() {
		Patient patient = patientRepo.findAll().iterator().next();
		
		assertNotNull(patient);
		
		patient = patientRepo.findById(patient.getId());
		assertEquals(0, patient.getDrugBoxItems().size());
	}
}
