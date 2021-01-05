package org.lhb.vw

import grails.gorm.services.Service

@Service(Video)
interface VideoService {

    Video get(Serializable id)

    List<Video> list(Map args)

    Long count()

    void delete(Serializable id)

    Video save(Video video)

}