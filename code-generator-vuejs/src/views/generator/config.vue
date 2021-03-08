<template>
  <div class="app-container">
    <el-row :gutter="15">
      <el-col style="margin-bottom: 10px">
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <span class="role-span">{{ tableName }} 类名配置：</span>
            <el-input v-model="className" placeholder="输入类名" size="mini" style="width:200px;max-width:100%;" />

            <span class="role-span"> 模块配置：</span>
            <el-input v-model="model" placeholder="输入模块" size="mini" style="width:200px;max-width:100%;" />
            <el-button
              :loading="genLoading"
              icon="el-icon-s-promotion"
              size="mini"
              style="float: right; padding: 6px 9px;"
              type="success"
              @click="toGen"
            >保存&生成</el-button>
            <el-button
              :loading="columnLoading"
              icon="el-icon-check"
              size="mini"
              style="float: right; padding: 6px 9px;margin-right: 9px"
              type="primary"
              @click="saveColumnConfig"
            >保存</el-button>
          </div>
          <el-form size="small" label-width="90px">
            <el-table v-loading="loading" :data="data" :max-height="tableHeight" border fit highlight-current-row stripe size="small" style="width: 100%;margin-bottom: 15px">
              <el-table-column prop="name" label="字段名称" />
              <el-table-column prop="jdbcType" label="字段JDBC类型" />
              <el-table-column prop="comments" label="字段描述">
                <!-- <template slot-scope="scope">
                  <el-input v-model="data[scope.$index].comments" size="mini" class="edit-input" />
                </template> -->
              </el-table-column>
              <el-table-column prop="javaType" label="字段Java类型" />
              <el-table-column prop="javaField" label="实体类属性" />
              <el-table-column align="center" label="主键" width="70px">
                <template slot-scope="scope">
                  <el-checkbox v-model="data[scope.$index].pk" />
                </template>
              </el-table-column>
              <el-table-column align="center" label="必填" width="70px">
                <template slot-scope="scope">
                  <el-checkbox v-model="data[scope.$index].notNull" disabled />
                </template>
              </el-table-column>
              <!--<el-table-column align="center" label="列表" width="70px">-->
              <!--<template slot-scope="scope">-->
              <!--<el-checkbox v-model="data[scope.$index].list" />-->
              <!--</template>-->
              <!--</el-table-column>-->
              <!--<el-table-column align="center" label="插入" width="70px">-->
              <!--<template slot-scope="scope">-->
              <!--<el-checkbox v-model="data[scope.$index].insert" />-->
              <!--</template>-->
              <!--</el-table-column>-->
              <el-table-column align="center" label="更新" width="70px">
                <template slot-scope="scope">
                  <el-checkbox v-model="data[scope.$index].edit" />
                </template>
              </el-table-column>
              <el-table-column align="center" label="查询" width="70px">
                <template slot-scope="scope">
                  <el-checkbox v-model="data[scope.$index].query" @change="() => data[scope.$index].queryType = !data[scope.$index].queryType ? '=' : data[scope.$index].queryType" />
                </template>
              </el-table-column>
              <el-table-column label="查询方式">
                <template slot-scope="scope">
                  <el-select
                    v-model="data[scope.$index].queryType"
                    filterable
                    class="edit-input"
                    clearable
                    size="mini"
                    placeholder="请选择"
                    @change="() => data[scope.$index].query = !!data[scope.$index].queryType"
                  >
                    <el-option
                      label="="
                      value="="
                    />
                    <el-option
                      label="!="
                      value="!="
                    />
                    <el-option
                      label=">="
                      value=">="
                    />
                    <el-option
                      label="<="
                      value="<="
                    />
                    <el-option
                      label="Left Like"
                      value="left_like"
                    />
                    <el-option
                      label="Like"
                      value="like"
                    />
                    <!-- <el-option
                      label="NotNull"
                      value="notNull"
                    />
                    <el-option
                      label="BetWeen"
                      value="between"
                    /> -->
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="表单类型">
                <template slot-scope="scope">
                  <el-select v-model="data[scope.$index].showType" filterable class="edit-input" clearable size="mini" placeholder="请选择">
                    <el-option
                      label="文本框"
                      value="input-text"
                    />
                    <el-option
                      label="文本域"
                      value="input-textarea"
                    />
                    <el-option
                      label="下拉框"
                      value="select"
                    />
                    <el-option
                      label="级联选择"
                      value="cascader"
                    />
                    <el-option
                      label="多选框"
                      value="input-checkbox"
                    />
                    <el-option
                      label="单选框"
                      value="input-radio"
                    />
                    <el-option
                      label="开关"
                      value="input-switch"
                    />
                    <el-option
                      label="数字"
                      value="input-number"
                    />
                    <el-option
                      label="密码"
                      value="input-password"
                    />
                    <el-option
                      label="范围"
                      value="input-range"
                    />
                    <el-option
                      label="年月框"
                      value="input-month"
                    />
                    <el-option
                      label="日期框"
                      value="input-date"
                    />
                    <el-option
                      label="日期时间框"
                      value="input-datetime"
                    />
                    <el-option
                      label="时间框"
                      value="input-time"
                    />
                    <el-option
                      label="颜色"
                      value="input-color"
                    />
                    <el-option
                      label="评分"
                      value="rate"
                    />
                    <el-option
                      label="邮箱"
                      value="input-email"
                    />
                    <el-option
                      label="电话"
                      value="input-tel"
                    />
                    <el-option
                      label="文件"
                      value="input-file"
                    />
                    <el-option
                      label="URL"
                      value="input-url"
                    />
                  </el-select>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { fetchTableColumns, generateTables, configTable } from '@/api/generator'
import { toCapitalizeCamelCase } from '@/utils'

export default {
  name: 'GeneratorConfig',
  components: {},
  data() {
    return {
      activeName: 'first',
      tableName: '',
      className: '',
      model: '',
      tableHeight: 550,
      columnLoading: false,
      genLoading: false,
      loading: false,
      data: []
    }
  },
  created() {
    this.tableName = this.$route.params.name
    this.className = toCapitalizeCamelCase(this.tableName)
    this.$nextTick(() => {
      this.loading = true
      fetchTableColumns(this.tableName).then(res => {
        this.className = res.data.className
        this.model = res.data.model
        this.data = res.data.columns
        this.loading = false
      }).catch(e => {
        this.loading = false
        console.log(e)
      })
    })
  },
  methods: {
    saveColumnConfig() {
      this.columnLoading = true
      configTable(this.tableName, this.model, this.className, this.data).then(res => {
        this.$notify({ title: '保存成功', type: 'success', duration: 2500 })
        this.columnLoading = false
      }).catch(err => {
        this.columnLoading = false
        console.log(err)
      })
    },
    toGen() {
      this.genLoading = true
      configTable(this.tableName, this.model, this.className, this.data).then(res => {
        this.$notify({ title: '保存成功', type: 'success', duration: 2500 })
        // 生成代码
        generateTables(this.tableName).then(data => {
          this.genLoading = false
          this.$notify({ title: '生成成功', type: 'success', duration: 2500 })
        }).catch(err => {
          this.genLoading = false
          console.log(err)
        })
      }).catch(err => {
        this.genLoading = false
        console.log(err)
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  .edit-input {
    .el-input__inner {
      border: 1px solid #e5e6e7;
    }
  }
</style>

<style scoped>
 ::v-deep .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
</style>
