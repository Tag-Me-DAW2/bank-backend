package com.tagme.tagme_bank_back.persistence.dao.jpa;

import java.util.Optional;

public interface GenericJpaDao<T> {
    Long count();
    Optional<T> findById(Long id);
}
