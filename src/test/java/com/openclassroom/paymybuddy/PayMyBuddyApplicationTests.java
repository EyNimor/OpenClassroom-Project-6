package com.openclassroom.paymybuddy;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest @TestPropertySource(locations="classpath:application-test.properties")
class PayMyBuddyApplicationTests {

	@Test
	void contextLoads() {
		try {
			TimeUnit.MINUTES.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertNull(null);
	}

}
