package info.moroff.prescriptionmanager.demo.patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import info.moroff.prescriptionmanager.patient.Patient;
import info.moroff.prescriptionmanager.patient.PatientRepository;

@RunWith(SpringRunner.class)
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
		Assume.assumeTrue(patientRepo != null);
		
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
