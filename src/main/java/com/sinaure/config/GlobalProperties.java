package com.sinaure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties // no prefix, root level.
public class GlobalProperties {

    //thread-pool , relax binding
    private String repository;
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	@Override
    public String toString() {
        return "GlobalProperties{" +
                "repository=" + repository +
                ", email='" + email + '\'' +
                '}';
    }
}
