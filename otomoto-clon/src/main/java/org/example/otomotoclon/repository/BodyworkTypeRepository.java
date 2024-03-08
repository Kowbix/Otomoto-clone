package org.example.otomotoclon.repository;

import org.example.otomotoclon.entity.BodyworkType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyworkTypeRepository extends CrudRepository<BodyworkType, Long> {

    BodyworkType findBodyworkTypeByName(String name);
}
