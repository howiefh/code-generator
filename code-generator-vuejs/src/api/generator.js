import request from '@/utils/request'

export function fetchConfig() {
  return request({
    url: '/generator/config',
    method: 'get'
  })
}

export function updateConfig(data) {
  return request({
    url: '/generator/config',
    method: 'post',
    data
  })
}

export function fetchTables(params) {
  return request({
    url: '/generator/tables',
    method: 'get',
    params
  })
}

export function fetchTableColumns(tableName) {
  return request({
    url: `/generator/tables/${tableName}/columns`,
    method: 'get'
  })
}

export function configTable(tableName, model, className, data) {
  return request({
    url: `/generator/tables/${tableName}/config/${model}/${className}`,
    method: 'post',
    data
  })
}

export function generateTables(tableNames) {
  return request({
    url: `/generator/tables/${tableNames}/generate`,
    method: 'post'
  })
}
