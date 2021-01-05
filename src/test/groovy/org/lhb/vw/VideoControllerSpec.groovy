package org.lhb.vw

import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import grails.validation.ValidationException
import spock.lang.*

class VideoControllerSpec extends Specification implements ControllerUnitTest<VideoController>, DomainUnitTest<Video> {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {
        given:
        controller.videoService = Mock(VideoService) {
            1 * list(_) >> []
            1 * count() >> 0
        }

        when:"The index action is executed"
        controller.index()

        then:"The model is correct"
        !model.videoList
        model.videoCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
        controller.create()

        then:"The model is correctly created"
        model.video!= null
    }

    void "Test the save action with a null instance"() {
        when:"Save is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        controller.save(null)

        then:"A 404 error is returned"
        response.redirectedUrl == '/video/index'
        flash.message != null
    }

    void "Test the save action correctly persists"() {
        given:
        controller.videoService = Mock(VideoService) {
            1 * save(_ as Video)
        }

        when:"The save action is executed with a valid instance"
        response.reset()
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        populateValidParams(params)
        def video = new Video(params)
        video.id = 1

        controller.save(video)

        then:"A redirect is issued to the show action"
        response.redirectedUrl == '/video/show/1'
        controller.flash.message != null
    }

    void "Test the save action with an invalid instance"() {
        given:
        controller.videoService = Mock(VideoService) {
            1 * save(_ as Video) >> { Video video ->
                throw new ValidationException("Invalid instance", video.errors)
            }
        }

        when:"The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def video = new Video()
        controller.save(video)

        then:"The create view is rendered again with the correct model"
        model.video != null
        view == 'create'
    }

    void "Test the show action with a null id"() {
        given:
        controller.videoService = Mock(VideoService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.show(null)

        then:"A 404 error is returned"
        response.status == 404
    }

    void "Test the show action with a valid id"() {
        given:
        controller.videoService = Mock(VideoService) {
            1 * get(2) >> new Video()
        }

        when:"A domain instance is passed to the show action"
        controller.show(2)

        then:"A model is populated containing the domain instance"
        model.video instanceof Video
    }

    void "Test the edit action with a null id"() {
        given:
        controller.videoService = Mock(VideoService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.edit(null)

        then:"A 404 error is returned"
        response.status == 404
    }

    void "Test the edit action with a valid id"() {
        given:
        controller.videoService = Mock(VideoService) {
            1 * get(2) >> new Video()
        }

        when:"A domain instance is passed to the show action"
        controller.edit(2)

        then:"A model is populated containing the domain instance"
        model.video instanceof Video
    }


    void "Test the update action with a null instance"() {
        when:"Save is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(null)

        then:"A 404 error is returned"
        response.redirectedUrl == '/video/index'
        flash.message != null
    }

    void "Test the update action correctly persists"() {
        given:
        controller.videoService = Mock(VideoService) {
            1 * save(_ as Video)
        }

        when:"The save action is executed with a valid instance"
        response.reset()
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        populateValidParams(params)
        def video = new Video(params)
        video.id = 1

        controller.update(video)

        then:"A redirect is issued to the show action"
        response.redirectedUrl == '/video/show/1'
        controller.flash.message != null
    }

    void "Test the update action with an invalid instance"() {
        given:
        controller.videoService = Mock(VideoService) {
            1 * save(_ as Video) >> { Video video ->
                throw new ValidationException("Invalid instance", video.errors)
            }
        }

        when:"The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(new Video())

        then:"The edit view is rendered again with the correct model"
        model.video != null
        view == 'edit'
    }

    void "Test the delete action with a null instance"() {
        when:"The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then:"A 404 is returned"
        response.redirectedUrl == '/video/index'
        flash.message != null
    }

    void "Test the delete action with an instance"() {
        given:
        controller.videoService = Mock(VideoService) {
            1 * delete(2)
        }

        when:"The domain instance is passed to the delete action"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(2)

        then:"The user is redirected to index"
        response.redirectedUrl == '/video/index'
        flash.message != null
    }
}






