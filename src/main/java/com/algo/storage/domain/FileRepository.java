package com.algo.storage.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * @author : iyeong-gyo
 * @package : com.algo.storage.domain
 * @since : 28.04.24
 */
public interface FileRepository extends CrudRepository<FileDetail, Long> {

}
