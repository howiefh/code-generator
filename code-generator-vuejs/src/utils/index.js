/**
 * Created by PanJiaChen on 16/11/18.
 */

import Vue from 'vue'

export const formatDate = parseTime
export const formatDateFriendly = formatTime

export function parseDate(str, fmt) {
  if (typeof str !== 'string') {
    return null
  }

  const format = typeof fmt === 'string' ? fmt : 'yyyy-MM-dd HH:mm:ss'
  const obj = { y: 0, M: 0, d: 0, H: 0, h: 0, m: 0, s: 0, S: 0 }
  format.replace(/([^yMdHmsS]*?)(([yMdHmsS])\3*)([^yMdHmsS]*?)/g, function(m, $1, $2, $3, $4, idx, old) {
    str = str.replace(new RegExp($1 + '(\\d{' + $2.length + '})' + $4), function(_m, _$1) {
      obj[$3] = parseInt(_$1)
      return ''
    })
    return ''
  })
  // 月份是从0开始的，所以要减去1
  if (obj.M > 0) {
    obj.M--
  }
  if (obj.d === 0) {
    obj.d = 1
  }
  const date = new Date(obj.y, obj.M, obj.d, obj.H, obj.m, obj.s)
  // 如果设置了毫秒
  if (obj.S !== 0) date.setMilliseconds(obj.S)
  return date
}

export function parseTime(time, fmt) {
  if (arguments.length === 0 || !time) {
    return null
  }
  let format = typeof fmt === 'string' ? fmt : 'yyyy-MM-dd HH:mm:ss'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if (/^\d+$/.test(time)) {
      if (('' + time).length === 10) {
        time = parseInt(time) * 1000
      } else {
        time = parseInt(time)
      }
    }
    date = new Date(time)
  }

  var o = {
    // 月份
    'M+': date.getMonth() + 1,
    // 日
    'd+': date.getDate(),
    // 小时
    'H+': date.getHours(),
    'W+': getWeekNumber(date),
    'h+': date.getHours() > 12 ? date.getHours() - 12 : date.getHours(),
    // 分
    'm+': date.getMinutes(),
    // 秒
    's+': date.getSeconds(),
    // 季度
    'q+': Math.floor((date.getMonth() + 3) / 3),
    // 毫秒
    'S': date.getMilliseconds()
  }
  if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
  for (const k in o) {
    if (new RegExp('(' + k + ')').test(format)) format = format.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
  }
  return format
}

function getWeekNumber(src) {
  const date = new Date(src.getTime())
  date.setHours(0, 0, 0, 0)
  // Thursday in current week decides the year.
  date.setDate(date.getDate() + 3 - (date.getDay() + 6) % 7)
  // January 4 is always in week 1.
  const week1 = new Date(date.getFullYear(), 0, 4)
  // Adjust to Thursday in week 1 and count number of weeks from date to week 1.
  // Rounding should be fine for Daylight Saving Time. Its shift should never be more than 12 hours.
  return 1 + Math.round(((date.getTime() - week1.getTime()) / 86400000 - 3 + (week1.getDay() + 6) % 7) / 7)
}

export function formatTime(time, option) {
  time = +time * 1000
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 30) {
    return '刚刚'
  } else if (diff < 3600) {
    // less 1 hour
    return Math.ceil(diff / 60) + '分钟前'
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + '小时前'
  } else if (diff < 3600 * 24 * 2) {
    return '1天前'
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return (
      d.getMonth() +
      1 +
      '月' +
      d.getDate() +
      '日' +
      d.getHours() +
      '时' +
      d.getMinutes() +
      '分'
    )
  }
}

// 格式化时间
export function getQueryObject(url) {
  url = url == null ? window.location.href : url
  const search = url.substring(url.lastIndexOf('?') + 1)
  const obj = {}
  const reg = /([^?&=]+)=([^?&=]*)/g
  search.replace(reg, (rs, $1, $2) => {
    const name = decodeURIComponent($1)
    let val = decodeURIComponent($2)
    val = String(val)
    obj[name] = val
    return rs
  })
  return obj
}

/**
 *get getByteLen
 * @param {Sting} val input value
 * @returns {number} output value
 */
export function getByteLen(val) {
  let len = 0
  for (let i = 0; i < val.length; i++) {
    if (val[i].match(/[^\x00-\xff]/ig) != null) {
      len += 1
    } else { len += 0.5 }
  }
  return Math.floor(len)
}

export function cleanArray(actual) {
  const newArray = []
  for (let i = 0; i < actual.length; i++) {
    if (actual[i]) {
      newArray.push(actual[i])
    }
  }
  return newArray
}

export function obj2Param(json) {
  if (!json) return ''
  return cleanArray(Object.keys(json).map(key => {
    if (json[key] === undefined) return ''
    return encodeURIComponent(key) + '=' +
            encodeURIComponent(json[key])
  })).join('&')
}

export function param2Obj(url) {
  const search = url.split('?')[1]
  if (!search) {
    return {}
  }
  return JSON.parse('{"' + decodeURIComponent(search).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g, '":"') + '"}')
}

export function html2Text(val) {
  const div = document.createElement('div')
  div.innerHTML = val
  return div.textContent || div.innerText
}

export function scrollTo(element, to, duration) {
  if (duration <= 0) return
  const difference = to - element.scrollTop
  const perTick = difference / duration * 10
  setTimeout(() => {
    element.scrollTop = element.scrollTop + perTick
    if (element.scrollTop === to) return
    scrollTo(element, to, duration - 10)
  }, 10)
}

export function toggleClass(element, className) {
  if (!element || !className) {
    return
  }
  let classString = element.className
  const nameIndex = classString.indexOf(className)
  if (nameIndex === -1) {
    classString += '' + className
  } else {
    classString = classString.substr(0, nameIndex) + classString.substr(nameIndex + className.length)
  }
  element.className = classString
}

export const pickerOptions = [
  {
    text: '今天',
    onClick(picker) {
      const end = new Date()
      const start = new Date(new Date().toDateString())
      end.setTime(start.getTime())
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近一周',
    onClick(picker) {
      const end = new Date(new Date().toDateString())
      const start = new Date()
      start.setTime(end.getTime() - 3600 * 1000 * 24 * 7)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近一个月',
    onClick(picker) {
      const end = new Date(new Date().toDateString())
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近三个月',
    onClick(picker) {
      const end = new Date(new Date().toDateString())
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      picker.$emit('pick', [start, end])
    }
  }]

export function getTime(type) {
  if (type === 'start') {
    return new Date().getTime() - 3600 * 1000 * 24 * 90
  } else {
    return new Date(new Date().toDateString())
  }
}

export function addTime(date, number = 0, unit = 'd') {
  let baseDate
  if (!date) {
    baseDate = new Date()
  } else {
    baseDate = new Date(date)
  }
  if (typeof number === 'string' && number.length > 1) {
    unit = number.substring(number.length - 1)
    number = +number.substring(0, number.length - 1)
  }
  switch (unit) {
    case 'y': {
      baseDate.setFullYear(baseDate.getFullYear() + number)
      return baseDate
    }
    case 'q': {
      baseDate.setMonth(baseDate.getMonth() + number * 3)
      return baseDate
    }
    case 'M': {
      baseDate.setMonth(baseDate.getMonth() + number)
      return baseDate
    }
    case 'W': {
      baseDate.setDate(baseDate.getDate() + number * 7)
      return baseDate
    }
    case 'd': {
      baseDate.setDate(baseDate.getDate() + number)
      return baseDate
    }
    case 'H': {
      baseDate.setHours(baseDate.getHours() + number)
      return baseDate
    }
    case 'm': {
      baseDate.setMinutes(baseDate.getMinutes() + number)
      return baseDate
    }
    case 's': {
      baseDate.setSeconds(baseDate.getSeconds() + number)
      return baseDate
    }
    default: {
      return baseDate
    }
  }
}

export function debounce(func, wait, immediate) {
  let timeout, args, context, timestamp, result

  const later = function() {
    // 据上一次触发时间间隔
    const last = +new Date() - timestamp

    // 上次被包装函数被调用时间间隔last小于设定时间间隔wait
    if (last < wait && last > 0) {
      timeout = setTimeout(later, wait - last)
    } else {
      timeout = null
      // 如果设定为immediate===true，因为开始边界已经调用过了此处无需调用
      if (!immediate) {
        result = func.apply(context, args)
        if (!timeout) context = args = null
      }
    }
  }

  return function(...originArgs) {
    args = originArgs
    context = this
    timestamp = +new Date()
    const callNow = immediate && !timeout
    // 如果延时不存在，重新设定延时
    if (!timeout) timeout = setTimeout(later, wait)
    if (callNow) {
      result = func.apply(context, args)
      context = args = null
    }

    return result
  }
}

export function deepClone(source) {
  if (!source && typeof source !== 'object') {
    throw new Error('error arguments', 'shallowClone')
  }
  const targetObj = source.constructor === Array ? [] : {}
  Object.keys(source).forEach((keys) => {
    if (source[keys] && typeof source[keys] === 'object') {
      targetObj[keys] = source[keys].constructor === Array ? [] : {}
      targetObj[keys] = deepClone(source[keys])
    } else {
      targetObj[keys] = source[keys]
    }
  })
  return targetObj
}

export function deepMerge(target, ...sources) {
  if (typeof target !== 'object') {
    target = {}
  }

  if (!sources.length) return target

  for (let i = 0; i < sources.length; i++) {
    const source = sources[i]

    if (typeof source === 'object') {
      for (const key in source) {
        let clone
        let copyIsArray = false
        const src = target[key]
        const copy = source[key]

        if (target === copy) {
          continue
        }

        if (copy && isObject(copy) || (copyIsArray = isArray(copy))) {
          if (copyIsArray) {
            copyIsArray = false
            clone = src && isArray(src) ? src : []
          } else {
            clone = src && isObject(src) ? src : {}
          }

          target[key] = deepMerge(clone, copy)
        } else if (copy !== undefined) {
          target[key] = copy
        }
      }
    }
  }

  return target
}

export function isNullOrUndefined(val) {
  return typeof val === 'undefined' || val === null
}

export function isFunction(fn) {
  return Object.prototype.toString.call(fn) === '[object Function]'
}

export function isObject(item) {
  return (item && typeof item === 'object' && !Array.isArray(item))
}

export function isArray(item) {
  return (item && Array.isArray(item))
}

export function isNumber(value) {
  value = value === null ? NaN : +value
  return typeof value === 'number' && !isNaN(value)
}

export function isInteger(value) {
  return isFinite(value) && value === Math.round(value)
}

export function guid() { // 获取随机ID，组件拖到预览视图后就会被设置个ID
  return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
    (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
  )
}

export function random(min, max) {
  return Math.floor((Math.random() * (max - min + 1)) + min)
}

export function randomItem(arr) {
  if (!arr || !arr.length) return null
  return arr[Math.floor(Math.random() * arr.length)]
}

export function randomCode(length) {
  let str = ''
  const arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']

  if (!length || length < 0) {
    length = 12
  }

  for (let i = 0; i < length; i++) {
    const pos = Math.floor(Math.random() * arr.length)
    str += arr[pos]
  }

  return str
}
export function stringifyJson(json) {
  if (typeof json === 'string') {
    return `'${json.replace(/('|")/g, '\\\'')}'`
  } else if (isObject(json)) {
    const props = Object
      .keys(json)
      .map(key => `${key}:${stringifyJson(json[key])}`)
      .join(',')
    return `{${props}}`
  } else if (isArray(json)) {
    const props = json
      .map(value => `${stringifyJson(value)}`)
      .join(',')
    return `[${props}]`
  } else {
    return json
  }
}

export function getQueryParam(attr) {
  const match = RegExp(`[?&]${attr}=([^&]*)`).exec(window.location.search) // 分组运算符是为了把结果存到exec函数返回的结果里
  return match && decodeURIComponent(match[1].replace(/\+/g, ' ')) // url中+号表示空格,要替换掉
}

export function removeUrlParam(url, attr) {
  const reg = RegExp(`[?&]${attr}=([^&]*)`)
  return url.replace(reg, '')
}

export function loadScript(src, objName) {
  return new Promise(function(resolve, reject) {
    if (window[objName]) {
      resolve(window[objName])
    } else {
      var script = document.createElement('script')
      script.type = 'text/javascript'
      script.src = src
      script.onerror = reject
      script.onload = () => {
        resolve(window[objName])
      }
      document.head.appendChild(script)
    }
  })
}

export function buildUrlParams(json, baseUrl = '', separator = '&', pathSeparator = '?') {
  if (!baseUrl) {
    baseUrl = ''
  }

  if (!json || Object.keys(json).length === 0) {
    return baseUrl
  }

  if (baseUrl && baseUrl.indexOf(pathSeparator) === -1) {
    baseUrl = baseUrl + pathSeparator
  } else {
    baseUrl = baseUrl + separator
  }

  return baseUrl + cleanArray(Object.keys(json).map(key => {
    const value = json[key]
    if (value === undefined) return ''
    if (typeof value === 'object') {
      return encodeURIComponent(key) + '=' +
              encodeURIComponent(JSON.stringify(value))
    }
    return encodeURIComponent(key) + '=' +
            encodeURIComponent(value)
  })).join(separator)
}

export function parseUrlParams(url, separator = '&', pathSeparator = '?') {
  const map = {}
  if (!url) {
    return map
  }

  const pathSeparatorIndex = url.indexOf(pathSeparator)
  if (pathSeparatorIndex >= 0) {
    url = url.substring(pathSeparatorIndex + 1)
  }

  const pairs = url.split(separator)
  for (let i = 0; i < pairs.length; i++) {
    const pair = pairs[i]
    const index = pair.indexOf('=')
    let key
    let value

    if (index === -1) {
      key = pair
      value = null
    } else {
      key = pair.substring(0, index)
      value = pair.substring(index + 1)
    }

    key = decodeURIComponent(key)
    if (value) {
      value = decodeURIComponent(value)
    }

    map[key] = value
  }

  return map
}

export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

export function getDate(val) {
  if (/-?\d+[a-zA-Z]/.test(val)) {
    return addTime(new Date(), val)
  } else {
    return new Date(val)
  }
}

export function addOrParseTime(date, val) {
  if (/-?\d+[a-zA-Z]/.test(val)) {
    return addTime(date, val)
  } else {
    return parseTime(date, val)
  }
}

export function isInRange(val, min, max, notInRange) {
  const minType = typeof min
  const maxType = typeof max
  let inRange = false
  if (minType === 'number' && maxType === 'number') {
    inRange = val >= min && val < max
  } else if (minType === 'number') {
    inRange = val >= min
  } else if (maxType === 'number') {
    inRange = val < max
  }

  return !notInRange && inRange || notInRange && !inRange
}

export function parseFilters(filters) {
  if (typeof filters !== 'string') {
    return []
  }
  const filterArray = filters.split('|')
  return filterArray.map((cur) => {
    const args = cur.trim().split(/\s+/)
    const filter = Vue.filter(args[0])
    if (!filter) {
      return null
    }
    return { handler: filter, args: args.slice(1) }
  }).filter(v => !!v)
}

export function parseFilter(filters) {
  const filterFunctions = parseFilters(filters)
  return (val) => filterFunctions.reduce((pre, filter) => {
    const { handler, args } = filter
    return handler(pre, ...args)
  }, val)
}

export function formatter(formatter, val) {
  if (!formatter) {
    return val
  }
  if (isFunction(formatter)) {
    return formatter(val)
  } else if (typeof formatter === 'object') {
    return formatter[val] || val
  } else if (typeof formatter === 'string') {
    const filter = parseFilter(formatter)
    return filter(val)
  }
  return val
}

export function renderTemplate(template, context) {
  return template.replace(/\{\{(.*?)\}\}/g, (match, key) => {
    const val = context[key.trim()]
    return isNullOrUndefined(val) ? '' : val
  })
}

/**
 * Check if an element has a class
 * @param {HTMLElement} elm
 * @param {string} cls
 * @returns {boolean}
 */
export function hasClass(ele, cls) {
  return !!ele.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'))
}

/**
 * Add class to element
 * @param {HTMLElement} elm
 * @param {string} cls
 */
export function addClass(ele, cls) {
  if (!hasClass(ele, cls)) ele.className += ' ' + cls
}

/**
 * Remove class from element
 * @param {HTMLElement} elm
 * @param {string} cls
 */
export function removeClass(ele, cls) {
  if (hasClass(ele, cls)) {
    const reg = new RegExp('(\\s|^)' + cls + '(\\s|$)')
    ele.className = ele.className.replace(reg, ' ')
  }
}

/**
 * 驼峰命名
 * @param {string} s
 */
export function toCapitalizeCamelCase(s) {
  if (s == null) {
    return ''
  }

  s = s.toLowerCase()

  let upperCase = false
  let result = ''
  for (let i = 0; i < s.length; i++) {
    const c = s[i]

    if (c === '_') {
      upperCase = true
    } else if (upperCase) {
      result = result + c.toUpperCase()
      upperCase = false
    } else if (i === 0) {
      result = result + c.toUpperCase()
    } else {
      result = result + c
    }
  }

  return result
}
