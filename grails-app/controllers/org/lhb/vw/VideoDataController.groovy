package org.lhb.vw

class VideoDataController {
    VideoService videoService

    def showVideoImage(Long id) {
        byte[] image = videoService.get(id)?.image
        println image
        response.setHeader("Content-type", "image/jpeg")
        response.outputStream << image
        response.outputStream.flush()
    }

    def list(int page, int count) {
        respond videoService.list(max: count, offset: page * count), formats: ['json']
    }

    def search(String keyword, int page, int count) {
        def searchLikeKeyword = "%${keyword.split("").join("%")}%"
        println("搜索关键字: ${searchLikeKeyword}")
        def results = Video.createCriteria().list(max: count, offset: page * count) {
            like ("name", searchLikeKeyword)
        }
        respond results , formats: ['json']
    }
 }
