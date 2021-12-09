import request from '../plugins/request'

enum Api {
    Delete = '/user',
    Update = '/user',
    Add = '/user',
    Search = '/user/all'
}

export function deleteUser(id: number) {
    return request({
        url: Api.Delete + '/' + id,
        method: 'delete'
    })
}

export function updateUser(data: any) {
    return request({
        url: Api.Update,
        method: 'put',
        data: data
    })
}

export function addUser(data: any) {
    return request({
        url: Api.Add,
        method: 'post',
        data: data
    })
}

export function search(condition: string) {
    return request({
        url: Api.Search,
        method: 'get',
        params: {
            condition: condition
        }
    })
}
