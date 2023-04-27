package com.project.fri;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

/**
 * packageName    : com.project.fri fileName       : config date           : 2023-04-27 description
 * :
 */
class JasyptConfigTest {

  @Test
  void jasypt() {
    String url = "db url";
    String username = "db id";
    String password = "db pw";

    String encryptUrl = jasyptEncrypt(url);
    String encryptUsername = jasyptEncrypt(username);
    String encryptPassword = jasyptEncrypt(password);

    System.out.println("encryptUrl: " + encryptUrl);
    System.out.println("encryptUsername: " + encryptUsername);
    System.out.println("encryptPassword: " + encryptPassword);

    Assertions.assertThat(url).isEqualTo(jasyptDecryt(encryptUrl));
  }

  private String jasyptEncrypt(String input) {
    String key = "";
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setAlgorithm("PBEWithMD5AndDES");
    encryptor.setPassword(key);
    return encryptor.encrypt(input);
  }

  private String jasyptDecryt(String input) {
    String key = "";
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setAlgorithm("PBEWithMD5AndDES");
    encryptor.setPassword(key);
    return encryptor.decrypt(input);
  }
}
