<template>
  <el-button icon="el-icon-back" @click="onCancel">{{ buttonText }}</el-button>
</template>

<script>
export default {
  name: 'SvgIcon',
  props: {
    text: {
      type: String,
      default: ''
    }
  },
  computed: {
    buttonText() {
      return this.text || '关闭'
    }
  },
  methods: {
    onCancel() {
      const route = this.$route
      const tagTitle = route.meta && route.meta.title || '页面'

      this.$store.dispatch('tagsView/delView', route).then(({ visitedViews }) => {
        const latestView = visitedViews.slice(-1)[0]
        if (latestView) {
          this.$router.push(latestView.path)
        } else {
          this.$router.push('/')
        }
      })
      this.$message({
        message: '关闭' + tagTitle + '!',
        type: 'warning'
      })
    }
  }
}
</script>

<style scoped>
.svg-icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
</style>
