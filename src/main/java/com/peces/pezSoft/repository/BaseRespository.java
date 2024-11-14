package com.peces.pezSoft.repository;

import java.io.Serializable;

import com.peces.pezSoft.model.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRespository<E extends Base, ID extends Serializable> extends JpaRepository<E, ID> {
}