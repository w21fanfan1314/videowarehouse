package org.lhb.vw

import grails.rest.Resource

/**
 * 视频
 */
class Video {
    String path
    long size
    String name
    Date createTime
    byte[] image

    static belongsTo = [warehouse: Warehouse]
    static constraints = {
        path nullable: false, blank: false
        name nullable: true, blank: false
        size min: 0L
        createTime nullable: false
        image nullable: true, blank: true
    }
}
