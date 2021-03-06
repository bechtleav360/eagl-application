package com.bechtle.eagl.webapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.saml2.core.Saml2X509Credential;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;

@Configuration
@Slf4j
@Profile("stage")
public class StageRelyingPartyConfiguration {


    @Value("${locations.metadata.okta_azure}")
    String okta_metadata;

    @Value("${locations.certificates.signing}")
    String cert_path_signing;

    @Value("${locations.certificates.decryption}")
    String cert_path_decryption;

    @Value("${locations.key}")
    String key_path;

    @Value("${key}")
    String key_asString;




    @Bean
    public RelyingPartyRegistrationRepository relyingPartyRegistrations() throws CertificateException, IOException {

        RelyingPartyRegistration okta = RelyingPartyRegistrations
                .fromMetadataLocation(okta_metadata)
                .registrationId("okta")
                .signingX509Credentials(consumer -> {
                    Saml2X509Credential cert = Saml2X509Credential.signing(this.loadPrivateKey(), this.loadCertificate(cert_path_signing));
                    consumer.add(cert);
                })
                .decryptionX509Credentials(consumer -> {
                    Saml2X509Credential cert = Saml2X509Credential.decryption(this.loadPrivateKey(), this.loadCertificate(cert_path_decryption));
                    consumer.add(cert);
                })
                .build();

        RelyingPartyRegistration daad = RelyingPartyRegistrations
                .fromMetadataLocation("https://saml-bird.daad.com/saml2/idp/metadata.php")
                .registrationId("daad")
                .signingX509Credentials(consumer -> {
                    Saml2X509Credential cert = Saml2X509Credential.signing(this.loadPrivateKey(), this.loadCertificate(cert_path_signing));
                    consumer.add(cert);
                })
                .decryptionX509Credentials(consumer -> {
                    Saml2X509Credential cert = Saml2X509Credential.decryption(this.loadPrivateKey(), this.loadCertificate(cert_path_decryption));
                    consumer.add(cert);
                })
                .build();
        return new InMemoryRelyingPartyRegistrationRepository(okta, daad);
    }


    private X509Certificate loadCertificate(String path)  {


        Resource resource = new ClassPathResource(path);
        try (InputStream is = resource.getInputStream()) {
            X509Certificate certificate = (X509Certificate)
                    CertificateFactory.getInstance("X.509").generateCertificate(is);
            return certificate;
        } catch (IOException e) {
            log.error("Failed to load certificate in path '{}'", path);
            throw new IllegalArgumentException("Invalid path to certificate", e);
        } catch (CertificateException e) {
            log.error("Failed to parse certificate in path '{}'", path);
            throw new IllegalArgumentException("Invalid certificate", e);
        }
    }

    private RSAPrivateKey loadPrivateKey() throws IllegalArgumentException {
        Resource resource = null;
        if(StringUtils.hasLength(key_asString)) {
            log.info("Loading private key from application properties");
            resource = new ByteArrayResource(key_asString.getBytes());
        } else {
            log.info("Loading private key from classpath");
            resource = new ClassPathResource(key_path);
        }

        try (InputStream is = resource.getInputStream()) {
            RSAPrivateKey rsa = RsaKeyConverters.pkcs8().convert(is);
            return rsa;
        } catch (IOException e) {
            log.error("Failed to load private key");
            throw new IllegalArgumentException("Invalid path to private key", e);
        }
    }


}