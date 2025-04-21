package com.portfolio.authorization.auth.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthCode is a Querydsl query type for AuthCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthCode extends EntityPathBase<AuthCode> {

    private static final long serialVersionUID = -346272520L;

    public static final QAuthCode authCode = new QAuthCode("authCode");

    public final StringPath clientId = createString("clientId");

    public final StringPath code = createString("code");

    public final DateTimePath<java.time.Instant> expiresAt = createDateTime("expiresAt", java.time.Instant.class);

    public final StringPath redirectUri = createString("redirectUri");

    public QAuthCode(String variable) {
        super(AuthCode.class, forVariable(variable));
    }

    public QAuthCode(Path<? extends AuthCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthCode(PathMetadata metadata) {
        super(AuthCode.class, metadata);
    }

}

