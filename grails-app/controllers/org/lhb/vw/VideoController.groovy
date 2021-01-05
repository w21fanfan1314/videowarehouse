package org.lhb.vw

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class VideoController {

    VideoService videoService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond videoService.list(params), model:[videoCount: videoService.count()]
    }

    def show(Long id) {
        respond videoService.get(id)
    }

    def create() {
        respond new Video(params)
    }

    def save(Video video) {
        if (video == null) {
            notFound()
            return
        }

        try {
            videoService.save(video)
        } catch (ValidationException e) {
            respond video.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'video.label', default: 'Video'), video.id])
                redirect video
            }
            '*' { respond video, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond videoService.get(id)
    }

    def update(Video video) {
        if (video == null) {
            notFound()
            return
        }

        try {
            videoService.save(video)
        } catch (ValidationException e) {
            respond video.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'video.label', default: 'Video'), video.id])
                redirect video
            }
            '*'{ respond video, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        videoService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'video.label', default: 'Video'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'video.label', default: 'Video'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
