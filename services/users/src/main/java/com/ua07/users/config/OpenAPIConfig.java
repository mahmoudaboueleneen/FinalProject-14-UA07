// OpenAPIConfig.java


package com.ua07.users.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

  @Bean
  public OpenAPI openAPI() {
    Server devServer = new Server();

    Contact contact = new Contact();
    contact.setEmail("mahmoudaboueleneen@gmail.com");
    contact.setName("Mahmoud Abou Eleneen");
    contact.setUrl("https://www.github.com/mahmoudaboueleneen");

    Info info = new Info()
      .title("Amazon Replica - Users Microservice API")
      .version("1.0")
      .contact(contact);

    return new OpenAPI().info(info).servers(List.of(devServer));
  }
}
