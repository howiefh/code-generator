<template>
  <div>
    <el-row type="flex" class="row-bg">
      <el-col :span="6">
        <span v-if="title" style="line-height:40px;">{{ title }}</span>
      </el-col>
      <el-col :span="6" :push="12">
        <div v-if="showToolbox" class="toolbox-container">
          <el-dropdown v-if="showFilter" :hide-on-click="false" :show-timeout="100" trigger="click" size="small">
            <el-button plain size="small">
              显示列({{ filteredTableLeafColumns.length }})
              <i class="el-icon-caret-bottom el-icon--right" />
            </el-button>
            <el-dropdown-menu slot="dropdown" class="column-tree-wrapper">
              <el-tree
                ref="columnTree"
                :data="tableColumns"
                :indent="14"
                :default-checked-keys="tableColumnNames"
                :props="{ label: 'label' }"
                :expand-on-click-node="false"
                :render-after-expand="false"
                class="column-tree"
                check-on-click-node
                show-checkbox
                node-key="name"
                highlight-current
                @check="handleTreeCheck"
              />
            </el-dropdown-menu>
          </el-dropdown>
          <el-button v-if="toolbox.export" type="primary" size="small" @click="handleToolboxExport(toolbox.export)">导出</el-button>
        </div>
      </el-col>
    </el-row>

    <el-table
      ref="table"
      :key="tableKey"
      v-loading="listLoading"
      :cell-style="cellStyle"
      :data="tableData"
      :default-sort="defaultSort"
      :max-height="maxHeight"
      :height="height"
      :show-summary="showSummary"
      :row-key="rowKey"
      :tree-props="treeProps"
      :default-expand-all="defaultExpandAll"
      :expand-row-keys="expandRowKeys"
      :indent="indent"
      :lazy="lazy"
      :load="load"
      :row-class-name="rowClassName"
      element-loading-text="给我一点时间"
      border
      fit
      highlight-current-row
      style="width: 100%"
      stripe
      @select="(selection, row) => handleEvent('select', selection, row)"
      @select-all="selection => handleEvent('select-all', selection)"
      @selection-change="selection => handleEvent('selection-change', selection)"
      @cell-click="handleCellClick"
      @sort-change="handleSortChange"
    >
      <!--数据列-->
      <table-column-item ref="columnItems" :columns="filteredTableColumns" :column-width="columnWidth" :first-column-type="firstColumnType" @click="handleClick" />
    </el-table>

    <!--分页-->
    <div v-if="showPagination" class="pagination-container">
      <el-pagination
        :total="tableTotal"
        :current-page="pagination.page"
        :page-size="pagination.rows"
        :layout="pageLayout"
        :page-sizes="pageSizes"
        background
        @update:currentPage="handleCurrentUpdate"
        @update:pageSize="handleSizeUpdate"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </div>

    <el-dialog :visible.sync="dialogVisible" :width="dialogWidth" :append-to-body="dialogAppendToBody" title="查看记录">
      <el-table :data="currentRowData">
        <el-table-column property="field" label="字段" width="200" />
        <el-table-column property="value" label="值">
          <template slot-scope="scope">
            <template v-if="scope.row.html">
              <div v-html="scope.row.html" />
            </template>
            <template v-else>
              {{ scope.row.value }}
            </template>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button :disabled="disablePrev" @click="handleClickPrev">上一个</el-button>
        <el-button :disabled="disableNext" @click="handleClickNext">下一个</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="customVisible" :width="dialogWidth" :append-to-body="dialogAppendToBody" @opened="handleDialogOpened">
      <slot slot="title" name="dialog-title">
        信息查看
      </slot>
      <slot slot="default" name="dialog-body">
        <el-form label-position="left" inline class="dialog-default-form">
          <el-form-item v-for="(value, key) in currentRow" :key="key" :label="key">
            <span>{{ value }}</span>
          </el-form-item>
        </el-form>
      </slot>
      <div slot="footer" class="dialog-footer">
        <el-button :disabled="disablePrev" @click="handleClickPrev">上一个</el-button>
        <el-button :disabled="disableNext" @click="handleClickNext">下一个</el-button>
      </div>
    </el-dialog>

    <el-image-viewer v-if="showImageViewer" :initial-index="initialIndex" :on-close="closeImageViewer" :url-list="imageUrls" />
  </div>
</template>

<script>
import ElImageViewer from 'element-ui/packages/image/src/image-viewer'
import request from '@/utils/request'
import { formatDate, isFunction, buildUrlParams, parseFilter, deepMerge, renderTemplate } from '@/utils'
import TableColumnItem from './TableColumnItem'
export default {
  name: 'TablePagination',
  components: {
    ElImageViewer,
    TableColumnItem
  },
  props: {
    title: {
      type: String,
      default: null
    },
    autoLoad: {
      type: Boolean,
      default: true
    },
    loading: {
      type: Boolean,
      default: false
    },
    tableKey: {
      type: [String, Number],
      default: null
    },
    cellStyle: {
      type: [Function, Object],
      default: null
    },
    rowClassName: {
      type: Function,
      default: null
    },
    maxHeight: {
      type: [String, Number],
      default: null
    },
    height: {
      type: [String, Number],
      default: null
    },
    showSummary: {
      type: Boolean,
      default: null
    },
    rowKey: {
      type: [String, Function],
      default: undefined
    },
    treeProps: {
      type: Object,
      default: undefined
    },
    defaultExpandAll: {
      type: Boolean,
      default: false
    },
    expandRowKeys: {
      type: Array,
      default: undefined
    },
    indent: {
      type: Number,
      default: 16
    },
    lazy: {
      type: Boolean,
      default: undefined
    },
    load: {
      type: Function,
      default: undefined
    },
    firstColumnType: {
      type: String,
      default: null
    },
    url: {
      type: String,
      default: ''
    },
    method: {
      type: String,
      default: 'get'
    },
    params: {
      type: Object,
      default: () => ({})
    },
    extendParams: {
      type: Object,
      default: () => ({})
    },
    dynamicParams: {
      type: [Object, Function],
      default: () => ({})
    },
    fetch: {
      type: Function,
      default: null
    },
    pageIndexKey: {
      type: String,
      default: 'page'
    },
    pageSizeKey: {
      type: String,
      default: 'rows'
    },
    listField: {
      type: [String, Function],
      default: () => {
        return (resp) => {
          return resp.data.items
        }
      }
    },
    totalField: {
      type: [String, Function],
      default: () => {
        return (resp) => {
          return resp.data.total
        }
      }
    },
    data: { // 数据列表
      type: Array,
      default: () => []
    },
    columns: {
      type: Array,
      default: () => []
    },
    columnWidth: {
      type: Number,
      default: 130
    },
    showPagination: {
      type: Boolean,
      default: false
    },
    defaultSort: {
      type: Object,
      default: () => ({
        prop: '',
        order: ''
      })
    },
    total: {
      type: Number,
      default: 0
    },
    pageLayout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper'
    },
    pageSize: {
      type: Number,
      default: 20
    },
    pageSizes: {
      type: Array,
      default: () => [10, 20, 50, 100, 200]
    },
    exportIgnoreColumns: {
      type: Array,
      default: () => []
    },
    exportGroupHeader: {
      type: Boolean,
      default: false
    },
    exportHeaderStyle: {
      type: Object,
      default: null
    },
    hiddenColumns: {
      type: Array,
      default: () => []
    },
    dialogWidth: {
      type: String,
      default: '70%'
    },
    dialogAppendToBody: {
      type: Boolean,
      default: false
    },
    toolbox: {
      type: Object,
      default: () => ({})
    },
    formatterAlias: {
      type: Object,
      default: () => ({})
    },
    sortable: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      listLoading: this.loading || false,
      internalFormatterAlias: {},
      tableColumnsDepth: 0,
      tableColumns: [],
      tableColumnNames: [],
      checkedTableColumnNames: [],
      // 嵌套表格 存储叶子节点列
      tableLeafColumns: [],
      checkedTableLeafColumnNames: [],
      tableData: this.data || [],
      tableTotal: this.total || 0,
      queryParams: Object.assign({}, this.params),
      pagination: {
        page: 1,
        rows: this.pageSize
      },
      sort: {
        prop: null,
        order: null
      },
      exportFilter: [],
      currentRowData: [],
      currentRowIndex: -1,
      currentRow: {},
      dialogVisible: false,
      customVisible: false,
      customKeys: [],
      customParams: {},
      imageColumns: [],
      showImageViewer: false, // 显示查看器
      imageUrls: [],
      initialIndex: 0
    }
  },
  computed: {
    disablePrev() {
      return this.currentRowIndex <= 0
    },
    disableNext() {
      const length = this.tableData && this.tableData.length || 0
      return this.currentRowIndex >= length - 1
    },
    showToolbox() {
      return this.showFilter || this.toolbox.export
    },
    showFilter() {
      // TODO 嵌套表格视图更新有问题 这种情况下下面两个返回的应该一样
      return this.toolbox.filter && this.tableLeafColumns.length === this.tableColumns.length
    },
    filteredTableColumns() {
      return this.tableColumns.filter(c => this.checkedTableColumnNames.indexOf(c.name) > -1)
    },
    filteredTableLeafColumns() {
      return this.tableLeafColumns.filter(c => this.checkedTableLeafColumnNames.indexOf(c.name) > -1)
    }
  },
  watch: {
    loading(newVal) {
      this.listLoading = newVal
    },
    data: {
      handler(newVal) {
        if (newVal && newVal.length) {
          this.tableData = newVal.slice(0)
        } else {
          this.tableData = []
        }
      },
      deep: true
    },
    total(newVal) {
      this.tableTotal = newVal
    },
    params: {
      handler(newVal) {
        this.queryParams = Object.assign({}, newVal)
      },
      deep: true
    },
    columns: {
      handler(newVal) {
        this.tableColumns = []
        this.tableColumnNames = []
        this.checkedTableColumnNames = []
        // 嵌套表格 存储叶子节点列
        this.tableLeafColumns = []
        this.checkedTableLeafColumnNames = []
        this.exportFilter = []
        this.init(newVal)
      },
      deep: true
    }
  },
  created() {
    this.init()
  },
  mounted() {
    if (this.autoLoad) {
      this.fetchList()
    }
  },
  methods: {
    init() {
      this.initColumns(this.columns)
      this.tableColumnsDepth = this.treeDepth(this.columns)
      this.parseColumns(this.tableColumns, 0, 0)
      this.tableColumnNames = this.checkedTableColumnNames.slice()
    },
    initColumns(columns) {
      const aliasKeys = Object.keys(this.formatterAlias)
      aliasKeys.forEach(key => {
        const alias = this.formatterAlias[key]
        if (typeof alias === 'string') {
          this.internalFormatterAlias[key] = parseFilter(alias)
        } else {
          this.internalFormatterAlias[key] = alias
        }
      })
      for (let i = 0; i < columns.length; i++) {
        const col = deepMerge({}, columns[i])
        let remove = false
        if (col.actions && col.items) {
          const defaultDynamicDisabled = () => false
          col.items.forEach(v => { v.disabled = v.disabled || defaultDynamicDisabled })
          col.items = col.items.filter(v => v.show === true && this.$hasPermissions(v.permissions))
          if (col.items.length <= 0) {
            remove = true
          } else {
            col.label = col.label || '操作'
            col.name = col.name || 'actions'
          }
        }
        if (!remove) {
          this.tableColumns.push(col)
          if (col.image) {
            this.imageColumns.push(col.name)
          }
        }
      }
    },
    treeDepth(columns) {
      const depth = [0]
      for (let i = 0; i < columns.length; i++) {
        depth.push(this.treeNodeDepth(columns[i]))
      }
      return Math.max(...depth)
    },
    treeNodeDepth(column) {
      // 叶子节点
      if ((!column.children || !column.children.length)) {
        return 1
      }
      return this.treeDepth(column.children) + 1
    },
    /**
     * @param startRow 从 0 起始
     * @param startCol 从 0 起始
     */
    parseColumns(columns, startRow, startCol) {
      let colSpan = 0
      let prevColSpan = 0
      for (let i = 0; i < columns.length; i++) {
        const column = columns[i]
        // 没有设置 label 时 使用 name 属性
        column.label = column.label || column.name
        if (typeof column.formatter === 'string') {
          if (this.internalFormatterAlias[column.formatter]) {
            column.formatter = this.internalFormatterAlias[column.formatter]
          } else {
            column.formatter = parseFilter(column.formatter)
          }
        }
        column.sortable = column.sortable || column.sortable === false ? column.sortable : this.sortable
        const show = this.hiddenColumns.indexOf(column.name) < 0 || column.hidden === false
        if (show) {
          this.checkedTableColumnNames.push(column.name)
        }
        column.startRow = startRow
        startCol = startCol + prevColSpan
        column.startCol = startCol
        // 叶子节点
        if ((!column.children || !column.children.length)) {
          column.colSpan = 1
          column.rowSpan = this.tableColumnsDepth - column.startRow
          column.isLeaf = true
          this.tableLeafColumns.push(column)
          if (show) {
            this.checkedTableLeafColumnNames.push(column.name)
          }
          if (!column.actions && (this.exportIgnoreColumns.indexOf(column.name) < 0 || column.noExport === false)) {
            this.exportFilter.push(column.name)
          }
        } else {
          column.isLeaf = false
          column.colSpan = this.parseColumns(column.children, startRow + 1, startCol)
          column.rowSpan = 1
        }
        colSpan += column.colSpan
        prevColSpan = column.colSpan
      }
      return colSpan
    },
    reload(query) {
      this.fetchList(query)
    },
    fetchList(query, exportCallback) {
      let { queryParams } = this
      const { fetch, method, url, listField, pageIndexKey, pageSizeKey, totalField, showPagination, pagination, sort } = this
      if (url || fetch) {
        this.listLoading = true
        if (query) {
          queryParams = Object.assign(queryParams, query)
        }
        if (exportCallback) {
          queryParams = Object.assign(queryParams, {
            [pageIndexKey]: pagination.page || 1,
            [pageSizeKey]: 10000
          })
        } else if (showPagination) {
          queryParams = Object.assign(queryParams, {
            [pageIndexKey]: pagination.page,
            [pageSizeKey]: pagination.rows
          })
        }
        if (sort.prop && sort.order) {
          queryParams = Object.assign(queryParams, { sort: sort.prop + ',' + sort.order })
        }
        let fetchData = null
        if (fetch) {
          fetchData = fetch(queryParams)
        } else {
          if (method === 'get') {
            fetchData = request({ url: url, method: method, params: queryParams })
          } else {
            fetchData = request({ url: url, method: method, data: queryParams })
          }
        }
        fetchData.then(response => {
          let list = null
          if (listField && isFunction(listField)) {
            list = listField(response)
          } else {
            list = response[listField]
          }
          let totalValue = 0
          if (totalField && isFunction(totalField)) {
            totalValue = totalField(response)
          } else {
            totalValue = response[totalField]
          }
          if (exportCallback) {
            exportCallback(list, totalValue)
          } else {
            this.tableData = list
            this.tableTotal = totalValue
            this.listLoading = false
          }
        }).catch(error => {
          console.error('Get remote data failed. ', error)
          this.listLoading = false
        })
      }
    },
    handleEvent(event) {
      this.$emit(event, ...Array.from(arguments).slice(1))
    },
    currentPage(current) {
      this.pagination.page = current
    },
    handleSizeChange(size) {
      this.pagination.rows = size
      this.handleEvent('page-change', this.pagination)
      this.fetchList()
    },
    handleCurrentChange(current) {
      this.pagination.page = current
      this.handleEvent('page-change', this.pagination)
      this.fetchList()
    },
    handleSizeUpdate(size) {
      this.pagination.rows = size
      this.handleEvent('page-update', this.pagination)
    },
    handleCurrentUpdate(current) {
      this.pagination.page = current
      this.handleEvent('page-update', this.pagination)
    },
    handleSortChange({ column, prop, order }) {
      const { sort } = this
      sort.prop = prop
      if (order === 'ascending') {
        sort.order = 'asc'
      } else if (order === 'descending') {
        sort.order = 'desc'
      } else {
        sort.prop = null
        sort.order = null
      }
      this.handleEvent('sort-change', { column, prop, order, sort })
      this.fetchList()
    },
    handleClick({ item, row, index }) {
      if (item.to || item.path) {
        if (item.matrixParams) {
          const params = this.generateParams(item.matrixParams, row)
          let urlParams = buildUrlParams(params, '', ';')
          if (urlParams) {
            urlParams = ';' + urlParams
          }
          const url = buildUrlParams(this.queryParams, item.path + urlParams)
          this.$router.push({
            path: url
          })
        } else {
          const params = this.generateParams(item.toParams, row)
          let query = this.generateParams(item.toQuery, row)
          query = Object.assign({}, this.extendParams, query)
          if (item.to) {
            this.$router.push({
              name: item.to,
              params: params,
              query: query
            })
          } else {
            this.$router.push({
              path: item.path,
              params: params,
              query: query
            })
          }
        }
      } else if (item.url) {
        const method = item.method || 'post'
        let ajax
        let params
        if (item.params) {
          params = this.generateParams(item.params, row)
        } else {
          params = row
        }
        if (method === 'get') {
          ajax = request({ url: item.url, method: method, params: params })
        } else {
          ajax = request({ url: item.url, method: method, data: params })
        }
        ajax.then(response => {
          const msg = item.success || response.msg
          if (msg) {
            this.$notify({
              message: msg,
              type: 'success',
              duration: 2000
            })
          }
        }).catch(error => {
          console.error('error. ', error)
          if (item.error) {
            this.$notify({
              message: item.error,
              type: 'error',
              duration: 2000
            })
          }
        })
      } else if (item.view) {
        this.currentRow = row
        this.currentRowIndex = index
        this.currentRowData = this.generateCurrentRowData(this.filteredTableLeafColumns, row)
        this.dialogVisible = true
      } else if (item.dialog) {
        this.currentRow = row
        this.currentRowIndex = index
        this.customKeys = item.params
        this.customParams = this.generateParams(this.customKeys, row)
        this.customVisible = true
        // dialog open 事件触发
        // this.handleEvent('custom-dialog-change', { params: this.customParams })
      } else if (item.onClick) {
        if (item.doubleCheck) {
          this.$confirm(item.doubleCheckMsg || '是否继续操作?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            item.onClick(row, index)
          }).catch(() => {
          })
        } else {
          item.onClick(row, index)
        }
      }
    },
    toggleRowSelection(row, selected) {
      this.$refs.table.toggleRowSelection(row, selected)
    },
    clearSelection(selection) {
      this.$refs.table.clearSelection(selection)
    },
    download({ filename, success, local = true, format = true }) {
      if (local) {
        this.export(filename, success, this.tableData, format)
      } else {
        this.fetchList({}, (list, total) => {
          this.export(filename, success, list, format)
        })
      }
    },
    export(filename, success, exportData, format = true, showMsg = true) {
      if ((!exportData || !exportData.length) && showMsg) {
        this.$message('暂无数据')
        return false
      }
      if (!filename) {
        filename = formatDate(Date.now(), 'yyyyMMdd HHmmsss')
      }
      import('@/vendor/Export2Excel').then(excel => {
        const filterVal = this.exportFilter.filter(v => this.checkedTableLeafColumnNames.indexOf(v) > -1)
        const data = this.formatJson(filterVal, exportData, format)
        if (this.exportGroupHeader) {
          excel.exportJsonToExcelWithGroupHeader({
            groupHeader: this.tableColumns,
            data: data,
            filename: filename,
            headerStyle: this.exportHeaderStyle
          })
        } else {
          const exportHeader = this.tableLeafColumns.filter(c => filterVal.indexOf(c.name) > -1).map(c => c.label)
          excel.exportJsonToExcel({
            header: exportHeader,
            data: data,
            filename: filename,
            headerStyle: this.exportHeaderStyle
          })
        }
        if (isFunction(success)) {
          success()
        }
      })
    },
    formatJson(filterVal, jsonData, format = true) {
      const columnMap = {}
      this.tableLeafColumns.forEach(v => {
        columnMap[v.name] = v
      })
      return jsonData.map((row, index) => filterVal.map(j => {
        const val = row[j]
        const col = columnMap[j]
        if (col && col.type === 'index') {
          return index + 1
        }
        if (val && typeof val === 'object') {
          return JSON.stringify(val)
        } else if (format && col) {
          return this.$refs.columnItems.formatter(col, row)
        } else {
          return val
        }
      }))
    },
    handleClickPrev() {
      if (this.currentRowIndex > -1) {
        this.currentRowIndex--
        this.currentRow = this.tableData[this.currentRowIndex]
        if (this.dialogVisible && this.currentRow) {
          this.currentRowData = this.generateCurrentRowData(this.filteredTableLeafColumns, this.currentRow)
        }
        if (this.customVisible && this.currentRow) {
          this.customParams = this.generateParams(this.customKeys, this.currentRow)
          this.handleEvent('custom-dialog-change', { params: this.customParams })
        }
      }
    },
    handleClickNext() {
      if (this.currentRowIndex < this.tableData.length) {
        this.currentRowIndex++
        this.currentRow = this.tableData[this.currentRowIndex]
        if (this.dialogVisible && this.currentRow) {
          this.currentRowData = this.generateCurrentRowData(this.filteredTableLeafColumns, this.currentRow)
        }
        if (this.customVisible && this.currentRow) {
          this.customParams = this.generateParams(this.customKeys, this.currentRow)
          this.handleEvent('custom-dialog-change', { params: this.customParams })
        }
      }
    },
    generateCurrentRowData(columns, row) {
      return columns.filter(c => !c.actions).map(c => {
        const fieldValue = this.$refs.columnItems.formatter(c, row)
        const html = c.viewHtml || c.html
        if (html) {
          return {
            field: c.label,
            html: renderTemplate(html, row),
            value: fieldValue || ''
          }
        }
        return {
          field: c.label,
          value: fieldValue || ''
        }
      })
    },
    handleToolboxExport(exportTool) {
      const exportHandler = exportTool.handler
      if (isFunction(exportHandler)) {
        exportHandler()
      } else {
        this.download(exportTool)
      }
    },
    handleTreeCheck(dataItem, { checkedKeys, halfCheckedKeys }) {
      this.checkedTableLeafColumnNames = this.$refs.columnTree.getCheckedKeys(true)
      this.checkedTableColumnNames = [...checkedKeys, ...halfCheckedKeys]
    },
    handleDialogOpened() {
      console.debug('dialog opened')
      this.handleEvent('custom-dialog-opened', { params: this.customParams })
    },
    generateParams(keys, row) {
      let dynamicParams
      if (isFunction(this.dynamicParams)) {
        dynamicParams = this.dynamicParams()
      } else {
        dynamicParams = this.dynamicParams
      }
      const params = {}
      if (keys) {
        for (let i = 0; i < keys.length; i++) {
          const key = keys[i]
          let val
          if (key && (val = row[key])) {
            params[key] = val
          } else if (key && dynamicParams && (val = dynamicParams[key])) {
            params[key] = val
          }
        }
      }
      return params
    },
    getLeafColumns() {
      return this.tableLeafColumns
    },
    handleCellClick(row, column, cell, event) {
      if (!this.imageColumns.length) {
        return false
      }
      const property = column.property
      if (this.imageColumns.indexOf(property) < 0) {
        return false
      }
      const rowIndex = this.tableData.indexOf(row)
      let imageIndex = rowIndex
      this.imageUrls = this.tableData.filter((data, index) => {
        const val = data[property]
        const hasValue = !!val
        if (!hasValue && rowIndex >= index) {
          imageIndex--
        }
        return hasValue
      }).map(data => data[property])
      if (this.imageUrls.length) {
        this.showImageViewer = true
        this.initialIndex = imageIndex < 0 ? 0 : imageIndex
      }
    },
    closeImageViewer() {
      this.showImageViewer = false
    }
  }
}
</script>
<style lang="scss" scoped>
.toolbox-container {
  float: right;
  margin-bottom: 6px;
}
.column-tree-wrapper {
  height: 300px;
  overflow: scroll;
}
.column-tree {
  padding: 5px 15px;
}
.dialog-default-form {
  font-size: 0;
}
.dialog-default-form label {
  width: 90px;
  color: #99a9bf;
}
.dialog-default-form .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
