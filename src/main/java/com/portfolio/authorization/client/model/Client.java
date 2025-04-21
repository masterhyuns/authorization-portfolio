package com.portfolio.authorization.client.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    private String clientId;

    private String clientSecret;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_redirect_uris", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "redirect_uri")
    private Set<String> redirectUris;
}
