<template>
  <div class="app-container">
    <el-tabs v-model="activeName">
      <el-tab-pane label="基本信息" name="basic">
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <span class="role-span">生成配置</span>
            <el-button
              :loading="configLoading"
              icon="el-icon-check"
              size="mini"
              style="float: right; padding: 6px 9px"
              type="primary"
              @click="submitConfig"
            >保存</el-button>
          </div>
          <el-form ref="form" :model="form" :rules="rules" size="mini" label-width="108px">
            <el-form-item label="作者名称" prop="author">
              <el-input v-model="form.author" clearable style="width: 40%" />
              <span style="color: #C0C0C0;margin-left: 10px;">类上面的@author</span>
            </el-form-item>
            <el-form-item label="Since" prop="since">
              <el-input v-model="form.since" clearable style="width: 40%" />
              <span style="color: #C0C0C0;margin-left: 10px;">类上面的@since</span>
            </el-form-item>
            <el-form-item label="Version" prop="version">
              <el-input v-model="form.version" clearable style="width: 40%" />
              <span style="color: #C0C0C0;margin-left: 10px;">类上面的@version</span>
            </el-form-item>
            <el-form-item label="模板路径" prop="templateDir">
              <el-input v-model="form.templateDir" clearable style="width: 40%" />
              <span style="color: #C0C0C0;margin-left: 10px;">模板路径</span>
            </el-form-item>
            <el-form-item label="包路径" prop="basePackage">
              <el-input v-model="form.basePackage" clearable style="width: 40%" />
              <span style="color: #C0C0C0;margin-left: 10px;">项目包的名称，生成的代码放到哪个包里面</span>
            </el-form-item>
            <el-form-item label="目标路径" prop="baseTarget">
              <el-input v-model="form.baseTarget" clearable style="width: 40%" />
              <span style="color: #C0C0C0;margin-left: 10px;">目标目录</span>
            </el-form-item>
            <el-form-item label="覆盖目标文件" prop="override">
              <el-switch v-model="form.override" />
              <span style="color: #C0C0C0;margin-left: 10px;">是否覆盖目标文件</span>
            </el-form-item>
            <el-form-item label="jdbc驱动" prop="jdbcDriver">
              <el-input v-model="form.jdbcDriver" clearable style="width: 40%" />
            </el-form-item>
            <el-form-item label="数据库链接" prop="jdbcUrl">
              <el-input v-model="form.jdbcUrl" clearable style="width: 40%" />
            </el-form-item>
            <el-form-item label="数据库用户名" prop="jdbcUsername">
              <el-input v-model="form.jdbcUsername" clearable style="width: 40%" />
            </el-form-item>
            <el-form-item label="数据库密码" prop="jdbcPassword">
              <el-input v-model="form.jdbcPassword" clearable style="width: 40%" />
            </el-form-item>
            <el-form-item label="数据库类型" prop="database">
              <el-select v-model="form.database" filterable style="width: 40%" clearable placeholder="请选择">
                <el-option
                  label="oracle"
                  value="oracle"
                />
                <el-option
                  label="mssql"
                  value="mssql"
                />
                <el-option
                  label="mysql"
                  value="mysql"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="去表前缀" prop="prefix">
              <el-input v-model="form.prefix" clearable placeholder="默认不去除表前缀" style="width: 40%" />
              <span style="color: #C0C0C0;margin-left: 10px;">默认不去除表前缀，可自定义，多个可以用`|`分隔</span>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="表信息" name="table">
        <sticky class-name="sub-navbar">
          <div class="filter-container">
            <el-input v-model="listQuery.name" clearable placeholder="输入表名" size="mini" class="filter-item" style="width:400px;max-width:100%;" />
            <el-button class="filter-item" type="primary" icon="el-icon-search" size="mini" @click="handleFilter">搜索</el-button>
            <el-button
              :loading="genLoading"
              class="filter-item"
              icon="el-icon-check"
              size="mini"
              type="success"
              @click="toGenAll"
            >生成全部</el-button>
            <el-button
              :loading="genLoading"
              class="filter-item"
              icon="el-icon-s-promotion"
              size="mini"
              type="success"
              @click="toGenSelected"
            >生成选中</el-button>
          </div>
        </sticky>

        <table-pagination
          ref="table"
          :fetch="fetch"
          :loading="loading"
          :columns="columns"
          :params="listQuery"
          :page-size="200"
          show-pagination
          @selection-change="handleSelectionChange"
        />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script type="text/jsx">
import TablePagination from '@/components/TablePagination'
import Sticky from '@/components/Sticky' // 粘性header组件
import { fetchTables, fetchConfig, updateConfig, generateTables } from '@/api/generator'

export default {
  components: { TablePagination, Sticky },
  data() {
    return {
      activeName: 'table',
      configLoading: false,
      form: {
        author: '',
        version: '',
        since: '',
        templateDir: '',
        basePackage: '',
        baseTarget: '',
        jdbcDriver: '',
        jdbcUrl: '',
        jdbcUsername: '',
        jdbcPassword: '',
        database: ''
      },
      rules: {
        author: [
          { required: true, message: '作者不能为空', trigger: 'blur' }
        ],
        version: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        since: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        templateDir: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        basePackage: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        jdbcDriver: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        jdbcUrl: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        jdbcUsername: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        // jdbcPassword: [
        //   { required: true, message: '不能为空', trigger: 'blur' }
        // ],
        database: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ]
      },
      listQuery: {
        name: ''
      },
      loading: false,
      fetch: fetchTables,
      selectedRows: [],
      genLoading: false,
      columns: [
        {
          name: '#',
          label: '#',
          type: 'selection',
          width: 60
        },
        {
          name: 'name',
          label: '表名',
          align: 'center',
          width: 260
        },
        {
          name: 'comments',
          label: '注释',
          align: 'center',
          width: 360
        },
        {
          actions: true,
          fixed: false,
          items: [
            {
              type: 'default',
              show: true,
              icon: 'el-icon-edit',
              tooltip: '编辑',
              to: 'GeneratorConfig',
              toParams: ['name']
            }
          ]
        }
      ]
    }
  },
  created() {
    this.fetchConfig()
  },
  methods: {
    fetchConfig() {
      this.configLoading = true
      fetchConfig().then(res => {
        this.form = res.data
        this.configLoading = false
      }).catch(err => {
        this.configLoading = false
        console.log(err.response.data.message)
      })
    },
    submitConfig() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.configLoading = true
          updateConfig(this.form).then(res => {
            this.$notify({ title: '保存成功', type: 'success', duration: 2500 })
            this.configLoading = false
          }).catch(err => {
            this.configLoading = false
            console.log(err.response.data.message)
          })
        }
      })
    },
    handlePageChange(pagination) {
      console.log(pagination)
    },
    handleSelectionChange(val) {
      this.selectedRows = val
      console.log('val:', val)
    },
    handleFilter() {
      this.$refs.table.reload(this.listQuery)
    },
    toGenSelected() {
      if (!this.selectedRows.length) {
        return
      }
      this.toGen(this.selectedRows.map(v => v.name).join(','))
    },
    toGenAll() {
      this.toGen('*')
    },
    toGen(tableNames = '') {
      this.genLoading = true
      // 生成代码
      generateTables(tableNames).then(data => {
        this.genLoading = false
        this.$notify({ title: '生成成功', type: 'success', duration: 2500 })
      }).catch(err => {
        this.genLoading = false
        console.log(err)
      })
    }
  }
}
</script>
