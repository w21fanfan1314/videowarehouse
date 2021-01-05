package org.lhb.vw

import grails.rest.Resource

/**
 * 视频仓库
 */
class Warehouse {
    String name
    String path
    List videos

    static hasMany = [videos: Video]

    static constraints = {
        name unique: true, nullable: false, blank: false
        path blank: false, unique: true, nullable: false
        videos nullable: true
    }

    static mapping = {
        videos lazy: true
    }

}
