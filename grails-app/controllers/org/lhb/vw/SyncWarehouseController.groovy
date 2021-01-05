package org.lhb.vw

class SyncWarehouseController {
    WarehouseService warehouseService

    def syncVideos(Long id) {
        Warehouse warehouse = warehouseService.get(id)
        def result = 0
        if (warehouse) {
            def files = this.deepCatalogGetFiles(warehouse.path)
            result = this.saveVideoObjects(warehouse, files)
        }
        flash.message="同步完成: ${result}"
        redirect(controller: 'warehouse', action: 'show', id: id)
    }

    private List<File> deepCatalogGetFiles(String rootPath) {
        def rootFile = new File(rootPath)
        def files = []
        if (rootFile.exists() && rootFile.directory) {
            Stack<File> dirs = []
            dirs << rootFile
            while (!dirs.empty) {
                def dir = dirs.pop()
                def subFiles = dir.listFiles()
                subFiles?.each {
                    if (it.file)
                        files << it
                    else if (it.directory)
                        dirs << it
                }
            }
        }
        files
    }

    private int saveVideoObjects(Warehouse warehouse, List<File> files) {
        int successCount = 0
        files?.each {
            println "文件: $it.absolutePath"
            if (!Video.findByPath(it.path)){
                def video = new Video(name: it.name, path: it.absolutePath,warehouse: warehouse,
                        size: it.size(), createTime: new Date(), image: fetchVideoFrame(it, 20))
                video.save()
                warehouse.videos << video
                successCount ++
            }
        }
        warehouse.save()
        successCount
    }

    private byte[] fetchVideoFrame(File videoFile, def frameIndex) {
        if (videoFile.exists() && videoFile.file) {

        }
        null
    }
}
