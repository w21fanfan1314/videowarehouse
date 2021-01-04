package videowarehouse

import org.lhb.vw.Video
import org.lhb.vw.Warehouse

class BootStrap {

    def init = { servletContext ->
        for (int i = 0 ; i < 100; i ++ ){
            def videos = []
            for (int j = 0; j < 20 ; j ++) {
                videos << new Video(path: "/var${i}/video-${j}.mp4", createTime: new Date())
            }
            new Warehouse(name: "测试仓库 - " + i, path: "/var${i}/", videos: videos).save()
        }
    }
    def destroy = {
    }
}
