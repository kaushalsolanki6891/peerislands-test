package com.peerislands.repository.impl;

import com.peerislands.model.GenerateQueryHack;
import com.peerislands.repository.ExtendedRepository;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.QueryTranslator;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Collections;

public class ExtendedRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ExtendedRepository<T, ID> {

    private EntityManager entityManager;

    public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public String getGeneratedQuery(GenerateQueryHack hack) {
        TypedQuery typeQuery = super.getQuery(hack.getSpec(), hack.getPageable());
        //Get Hibernate SQL
        String hqlQueryString = typeQuery.unwrap(org.hibernate.query.Query.class).getQueryString();
        //Translate HQL to SQL
        ASTQueryTranslatorFactory queryTranslatorFactory = new ASTQueryTranslatorFactory();
        SessionImplementor hibernateSession = entityManager.unwrap(SessionImplementor.class);
        QueryTranslator queryTranslator = queryTranslatorFactory.createQueryTranslator("", hqlQueryString, Collections.emptyMap(), hibernateSession.getFactory(), null);
        queryTranslator.compile(Collections.emptyMap(), false);
        String sqlQueryString = queryTranslator.getSQLString();
        return sqlQueryString;
    }

}