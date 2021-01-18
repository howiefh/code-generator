<template>
  <div>
    <!--选择框-->
    <el-table-column v-if="firstColumnType" :type="firstColumnType" width="55" />

    <template v-for="(column, index) in columns">
      <el-table-column v-if="column.type === 'index' || column.type === 'selection'" :key="column.name || column.label" :type="column.type" :prop="column.name" :label="column.label || '#'" :align="column.align || 'center'" :width="column.width || columnWidth || 130" :fixed="column.fixed" :sortable="column.sortable" />
      <!--数据列-->
      <el-table-column
        v-else-if="!column.children || !column.children.length"
        :key="column.name || column.label"
        :type="column.type"
        :prop="column.name"
        :label="column.label || column.actions && '操作'"
        :align="column.align || 'center'"
        :width="column.width || columnWidth || 130"
        :fixed="column.fixed"
        :show-overflow-tooltip="!column.actions"
        :sortable="column.sortable"
        :filters="column.filters"
        :filter-method="column.filterMethod"
      >
        <template slot-scope="scope">
          <template v-if="column.actions">
            <!--按钮操作组-->
            <template v-for="(item, btnIndex) in column.items">
              <el-tooltip :key="btnIndex" :content="item.tooltip || item.text" :open-delay="column.openDelay || 2000" placement="bottom" effect="dark">
                <el-button v-if="item.text" :disabled="item.disabled(scope.row, scope.$index)" :size="item.size || 'mini'" :type="item.type" :icon="item.icon" round @click.native.prevent="handleClick({item, row: scope.row, index: scope.$index})">{{ item.text }}</el-button>
                <el-button v-else :size="item.size || 'mini'" :disabled="item.disabled(scope.row, scope.$index)" :type="item.type" :icon="item.icon" circle @click.native.prevent="handleClick({item, row: scope.row, index: scope.$index})" />
              </el-tooltip>
            </template>
          </template>
          <template v-else-if="!column.render && column.html">
            <div v-html="renderTemplate(column, scope.row)" />
          </template>
          <template v-else-if="!column.render">
            <span>{{ formatter(column, scope.row) }}</span>
          </template>
          <template v-else>
            <expand-dom :column="column" :row="scope.row" :index="index" />
          </template>
        </template>
      </el-table-column>

      <el-table-column v-else :key="column.name || column.label" :label="column.label" :align="column.align || 'center'">
        <table-column-item :columns="column.children" :column-width="columnWidth" :first-column-type="firstColumnType" @click="handleClick" />
      </el-table-column>

    </template>
  </div>
</template>

<script>
import { isFunction, isObject, renderTemplate } from '@/utils'
import jsonpath from '@/utils/jsonpath'

export default {
  name: 'TableColumnItem',
  components: {
    expandDom: {
      props: {
        row: {
          type: Object,
          required: true
        },
        index: Number,
        column: {
          type: Object,
          required: true
        }
      },
      render() {
        return this.column.render(this.row, this.column, this.index)
      }
    }
  },
  props: {
    firstColumnType: {
      type: String,
      default: null
    },
    columnWidth: {
      type: Number,
      default: 130
    },
    columns: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
    }
  },
  methods: {
    renderTemplate(column, row) {
      return renderTemplate(column.html, row)
    },
    formatter(column, row) {
      let val = row[column.name]
      if (column.jsonpath) {
        val = jsonpath.getValue(row, column.jsonpath)
      } else {
        val = row[column.name]
      }
      if (!column.formatter) {
        return val
      }
      const formatter = column.formatter
      if (isFunction(formatter)) {
        return formatter(val, row, column)
      } else if (isObject(formatter)) {
        return formatter[val] || val
      }
      return val
    },
    handleClick({ item, row, index }) {
      this.$emit('click', { item, row, index })
    }
  }
}
</script>
