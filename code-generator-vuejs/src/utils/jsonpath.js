import { isArray, isNullOrUndefined } from './index'

const jsonpath = {
  copy(srcObj, destObj, mapper) {
    const mapperKeys = Object.keys(mapper)
    mapperKeys.forEach(destKey => {
      const srcKey = mapper[destKey]
      const srcValue = this.getValue(srcObj, srcKey)
      if (typeof srcValue !== 'undefined') {
        this.setValue(destObj, destKey, srcValue)
      }
    })
  },
  getValue(obj, key) {
    if (isNullOrUndefined(obj)) {
      return null
    }
    let keys
    if (isArray(key)) {
      keys = key
    } else if (typeof key === 'string') {
      keys = key.split(/[\.\[\]]/g).filter(v => v)
    }
    if (!keys) {
      return null
    }
    let currentValue = obj
    for (let index = 0; index < keys.length; index++) {
      const currentKey = keys[index]
      currentValue = obj[currentKey]
      if (isNullOrUndefined(currentValue)) {
        break
      }
      obj = currentValue
    }
    return currentValue
  },
  setValue(obj, key, value) {
    let keys
    if (isArray(key)) {
      keys = key
    } else if (typeof key === 'string') {
      keys = key.split(/[\.\[\]]/g).filter(v => v)
    }
    if (!keys) {
      return false
    }
    const lastObj = this.getValue(obj, keys.slice(0, keys.length - 1))
    if (!lastObj) {
      return false
    } else {
      lastObj[keys[keys.length - 1]] = value
      return true
    }
  }
}

export default jsonpath
