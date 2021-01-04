class DataManager {
    constructor(url) {
        this.url = url
    }

    /**
     * 增
     * @param obj
     * @returns {Promise}
     */
    insert(obj) {
        return axios({
            method: 'post',
            url: this.url,
            contentType: "application/json",
            data: obj
        })
    }

    /**
     * 删
     * @param id
     * @returns {Promise}
     */
    remove(id) {
        return axios({
            method: 'delete',
            url: this.url + "/" + id
        })
    }

    /**
     * 改
     * @param id
     * @param obj
     * @returns {Promise}
     */
    update(id, obj) {
        return axios({
            method: 'post',
            url: this.url + "/" +id,
            contentType: "application/json",
            data: obj
        })
    }

    /**
     * 查
     * @param id
     * @returns {Promise}
     */
    find(id) {
        return axios({
            method: 'get',
            url: this.url + "/" +id,
            contentType: "application/json"
        })
    }

    /**
     * 查询列表
     * @param page
     * @param offset
     * @returns {*}
     */
    list(page, max) {
        return axios({
            method: 'get',
            url: this.url,
            responseType: 'json',
            responseEncoding: "utf8",
            contentType: "application/json",
            params: {
                max: max,
                offset: page * max
            }
        })
    }
}