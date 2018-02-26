package info.moroff.prescriptionmanager.demo;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import info.moroff.prescriptionmanager.patient.DrugBoxItem;
import info.moroff.prescriptionmanager.patient.DrugBoxItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Transactional
@TestPropertySource("/test.properties")
public class DrugBoxItemControllerTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private DrugBoxItemRepository drugBoxItemRepository;
	
	private MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testDrugBoxItemEdit() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		DrugBoxItem drugBoxItem = drugBoxItemRepository.findById(8);
		DrugBoxItem drugBoxItemSaved;
		
		mockMvc.perform(get("/patients/1/drugs/8/edit")).andExpect(status().isOk());
		
		// New values
		drugBoxItem.setDaylyIntake(1.0);
		drugBoxItem.setInventoryAmount(20.0);
		drugBoxItem.setInventoryDate(LocalDate.now());
		
		mockMvc.perform(post("/patients/1/drugs/8/edit"). //
				param("daylyIntake", drugBoxItem.getDaylyIntake().toString()). //
				param("inventoryAmount", drugBoxItem.getInventoryAmount().toString()). //
				param("InventoryDate", drugBoxItem.getInventoryDate().format(formatter))
				).andExpect(redirectedUrl("/patients/1"));
		
		drugBoxItemSaved = drugBoxItemRepository.findById(8);

		assertEquals(drugBoxItem.getDrug(), drugBoxItemSaved.getDrug());
		assertEquals(drugBoxItem.getPatient(), drugBoxItemSaved.getPatient());
	}

}
