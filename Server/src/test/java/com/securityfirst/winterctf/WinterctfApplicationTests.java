package com.securityfirst.winterctf;

import com.securityfirst.winterctf.service.ChallengeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"dev"})
class WinterctfApplicationTests {

	@Autowired
	ChallengeService challengeService;
	@Test
	void contextLoads() {
		for (int i = 0; i < 300; i++) {
			System.out.println(challengeService.getCommentString(new Long(114)));
		}


	}

}
