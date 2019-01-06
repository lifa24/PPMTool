package io.lifafa.ppmtool.repositories;

import io.lifafa.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository <User,Long>{
}
