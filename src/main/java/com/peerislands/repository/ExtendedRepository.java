package com.peerislands.repository;

import com.peerislands.model.GenerateQueryHack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface ExtendedRepository<T, ID extends Serializable> extends JpaRepository<T, ID>  {
    String getGeneratedQuery(GenerateQueryHack hack);
}