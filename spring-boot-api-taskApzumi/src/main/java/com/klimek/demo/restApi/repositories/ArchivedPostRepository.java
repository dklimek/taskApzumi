package com.klimek.demo.restApi.repositories;

import com.klimek.demo.restApi.entities.ArchivedPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "archivedpost", path = "archivedpost")
public interface ArchivedPostRepository extends CrudRepository<ArchivedPost, UUID> {
    @Query(value = "SELECT DISTINCT post_id FROM ArchivedPost", nativeQuery = true)
    List<BigInteger> findAllUniqueUserId();

}
