package com.project.fri;

import java.util.Locale;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
public class FriApplication {
	@PostConstruct
	public void started(){
		//시간 한국으로 변경
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		//위치도 변경
		Locale.setDefault(Locale.KOREA);
	}
	public static void main(String[] args) {
		SpringApplication.run(FriApplication.class, args);
	}

}
