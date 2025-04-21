package com.portfolio.authorization.client.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClient is a Querydsl query type for Client
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClient extends EntityPathBase<Client> {

    private static final long serialVersionUID = 1483646603L;

    public static final QClient client = new QClient("client");

    public final StringPath clientId = createString("clientId");

    public final StringPath clientSecret = createString("clientSecret");

    public final SetPath<String, StringPath> redirectUris = this.<String, StringPath>createSet("redirectUris", String.class, StringPath.class, PathInits.DIRECT2);

    public QClient(String variable) {
        super(Client.class, forVariable(variable));
    }

    public QClient(Path<? extends Client> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClient(PathMetadata metadata) {
        super(Client.class, metadata);
    }

}

