package com.kernel360.utils.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class EncryptionConfiguration {

    @Value("${jasypt.encryptor.password}")
    private String password;

    @Primary
    @Bean("washpediaEncryptorBean")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndTripleDES");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setPoolSize("1");
        encryptor.setConfig(config);
        return encryptor;
    }

}