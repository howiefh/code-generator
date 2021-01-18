const Mock = require('mockjs')

const table = Mock.mock({
  'items|30': [{
    name: '@word()',
    comments: '@sentence(10, 20)'
  }]
})

const column = Mock.mock({
  'items|30': [{
    table: '@word()',
    name: '@word()',
    comments: '@sentence(10, 20)',
    jdbcType: '@word()',
    javaType: '@word()',
    javaField: '@word()',
    pk: '@boolean',
    notNull: '@boolean',
    insert: '@boolean',
    edit: '@boolean',
    list: '@boolean',
    query: '@boolean',
    queryType: '@word',
    showType: '@word'
  }]
})

module.exports = [
  {
    url: '/generator/tables$',
    type: 'get',
    response: config => {
      const items = table.items

      const { name, page = 1, rows = 20 } = config.query

      const mockList = items.filter(item => {
        if (name && item.name !== name) return false
        return true
      })

      const pageList = mockList.filter((item, index) => index < rows * page && index >= rows * (page - 1))

      return {
        code: 200,
        data: {
          total: mockList.length,
          items: pageList
        }
      }
    }
  },
  {
    url: `/generator/tables/[^/]*/columns`,
    type: 'get',
    response: config => {
      const data = {
        className: '',
        model: '',
        columns: column.items
      }

      return {
        code: 200,
        data: data
      }
    }
  },
  {
    url: `/generator/tables/[^/]*/config`,
    type: 'post',
    response: config => {
      return {
        code: 200,
        data: 'success'
      }
    }
  },
  {
    url: `/generator/tables/[^/]*/generate`,
    type: 'post',
    response: config => {
      return {
        code: 200,
        data: 'success'
      }
    }
  },

  {
    url: `/generator/config`,
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: {
          author: 'howiefh',
          version: '1.0',
          since: '1.0',
          templateDir: 'templates',
          basePackage: 'io.github.howiefh',
          baseTarget: '',
          override: false,
          jdbcDriver: 'com.mysql.jdbc.Driver',
          jdbcUrl: 'jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8',
          jdbcUsername: 'root',
          jdbcPassword: '',
          database: 'mysql'
        }
      }
    }
  },

  {
    url: `/generator/config`,
    type: 'post',
    response: config => {
      return {
        code: 200,
        data: 'success'
      }
    }
  }
]
