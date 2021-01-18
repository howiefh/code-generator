/* eslint-disable */
require('script-loader!file-saver');
require('script-loader!@/vendor/Blob');
import XLSX from 'xlsx'
import { formatDate, isNullOrUndefined } from '@/utils'

function generateHeaderRows(headerTree) {
  const queue = [...headerTree]
  const merges = []
  const headerRows = []
  const result = {}
  while (queue.length > 0) {
    const headerItem = queue.shift()
    if (headerItem.children && headerItem.children.length) {
      queue.push(...headerItem.children)
    }

    const startRow = headerItem.startRow
    const startCol = headerItem.startCol
    const rowSpan = headerItem.rowSpan
    const colSpan = headerItem.colSpan

    for (let i = 0; i < rowSpan; i++) {
      const row = startRow + i
      let headerRow = headerRows[row]
      if (!headerRow) {
        headerRow = []
        headerRows[row] = headerRow
      }

      for (let j = 0; j < colSpan; j++) {
        const col = startCol + j
        if (row === startRow && col === startCol) {
          headerRow[col] = headerItem.label
        } else {
          headerRow[col] = null
        }
      }
    }
    if (rowSpan > 1 || colSpan > 1) {
      merges.push({s: {r: startRow, c: startCol}, e: {r: startRow + rowSpan - 1, c: startCol + colSpan - 1}})
    }
  }
  result['merges'] = merges
  result['rows'] = headerRows
  return result
}

function generateArray(table) {
    var out = [];
    var rows = table.querySelectorAll('tr');
    var ranges = [];
    for (var R = 0; R < rows.length; ++R) {
        var outRow = [];
        var row = rows[R];
        var columns = row.querySelectorAll('td');
        for (var C = 0; C < columns.length; ++C) {
            var cell = columns[C];
            var colspan = cell.getAttribute('colspan');
            var rowspan = cell.getAttribute('rowspan');
            var cellValue = cell.innerText;
            if (cellValue !== "" && cellValue == +cellValue) cellValue = +cellValue;

            //Skip ranges
            ranges.forEach(function (range) {
                if (R >= range.s.r && R <= range.e.r && outRow.length >= range.s.c && outRow.length <= range.e.c) {
                    for (var i = 0; i <= range.e.c - range.s.c; ++i) outRow.push(null);
                }
            });

            //Handle Row Span
            if (rowspan || colspan) {
                rowspan = rowspan || 1;
                colspan = colspan || 1;
                ranges.push({s: {r: R, c: outRow.length}, e: {r: R + rowspan - 1, c: outRow.length + colspan - 1}});
            }

            //Handle Value
            outRow.push(cellValue !== "" ? cellValue : null);

            //Handle Colspan
            if (colspan) for (var k = 0; k < colspan - 1; ++k) outRow.push(null);
        }
        out.push(outRow);
    }
    return [out, ranges];
};

function datenum(v, date1904) {
    if (date1904) v += 1462;
    var epoch = Date.parse(v);
    return (epoch - new Date(Date.UTC(1899, 11, 30))) / (24 * 60 * 60 * 1000);
}

function sheet_from_array_of_arrays(data, opts) {
    var ws = {};
    opts.headerRows = isNullOrUndefined(opts.headerRows) ? 1 : opts.headerRows

    var range = {s: {c: 10000000, r: 10000000}, e: {c: 0, r: 0}};
    for (var R = 0; R != data.length; ++R) {
        for (var C = 0; C != data[R].length; ++C) {
            if (range.s.r > R) range.s.r = R;
            if (range.s.c > C) range.s.c = C;
            if (range.e.r < R) range.e.r = R;
            if (range.e.c < C) range.e.c = C;
            var val = data[R][C]
            if (opts.nullToEmpty && isNullOrUndefined(val)) {
              val = ''
            }
            var cell = {v: val};
            if (cell.v == null) continue;
            var cell_ref = XLSX.utils.encode_cell({c: C, r: R});

            if (typeof cell.v === 'number') cell.t = 'n';
            else if (typeof cell.v === 'boolean') cell.t = 'b';
            else if (cell.v instanceof Date) {
                cell.t = 'n';
                cell.z = XLSX.SSF._table[14];
                cell.v = datenum(cell.v);
            }
            else cell.t = 's';

            if (opts.headerStyle && R < opts.headerRows) {
              cell.s = opts.headerStyle
            }

            ws[cell_ref] = cell;
        }
    }
    if (range.s.c < 10000000) ws['!ref'] = XLSX.utils.encode_range(range);
    return ws;
}

function Workbook() {
    if (!(this instanceof Workbook)) return new Workbook();
    this.SheetNames = [];
    this.Sheets = {};
}

function s2ab(s) {
    var buf = new ArrayBuffer(s.length);
    var view = new Uint8Array(buf);
    for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
    return buf;
}

function internalExportJsonToExcel({ws, wsName, data, filename='excel-list', autoWidth=true, nullToEmpty=true}={}) {
    var wb = new Workbook()

    if(autoWidth){
      /*设置worksheet每列的最大宽度*/
      const colWidth = data.map(row => row.map(val => {
        /*先判断是否为null/undefined*/
        if (val == null) {
          return {'wch': 10};
        }
        /*再判断是否为中文*/
        else if (val.toString().charCodeAt(0) > 255) {
          return {'wch': val.toString().length * 2};
        } else {
          return {'wch': val.toString().length};
        }
      }))
      /*以第一行为初始值*/
      let result = colWidth[0];
      for (let i = 1; i < colWidth.length; i++) {
        for (let j = 0; j < colWidth[i].length; j++) {
          if (result[j]['wch'] < colWidth[i][j]['wch']) {
            result[j]['wch'] = colWidth[i][j]['wch'];
          }
        }
      }
      ws['!cols'] = result;
    }

    /* add worksheet to workbook */
    wb.SheetNames.push(wsName);
    wb.Sheets[wsName] = ws;

    var wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: false, type: 'binary'});
    saveAs(new Blob([s2ab(wbout)], {type: "application/octet-stream"}), filename + ".xlsx");
  }

  function parseStyle(style) {
    if (!style) {
      return style
    }
    const newStyle = {}
    Object.keys(style).forEach(key => {
      const value = style[key]
      switch (key) {
        case 'fgColor': {
          if (!newStyle.fill) {
            newStyle.fill = {}
          }
          newStyle.fill.fgColor = { rgb: value }
          break
        }
        case 'bgColor': {
          if (!newStyle.fill) {
            newStyle.fill = {}
          }
          newStyle.fill.bgColor = { rgb: value }
          break
        }
        case 'fontColor': {
          if (!newStyle.font) {
            newStyle.font = {}
          }
          newStyle.font.color = { rgb: value }
          break
        }
        case 'fontSize': {
          if (!newStyle.font) {
            newStyle.font = {}
          }
          newStyle.font.sz = value
          break
        }
        case 'fontBold': {
          if (!newStyle.font) {
            newStyle.font = {}
          }
          newStyle.font.bold = value
          break
        }
        case 'align': {
          if (!newStyle.alignment) {
            newStyle.alignment = {}
          }
          newStyle.alignment.vertical = value
          newStyle.alignment.horizontal = value
          break
        }
        case 'horizontalAlign': {
          if (!newStyle.alignment) {
            newStyle.alignment = {}
          }
          newStyle.alignment.horizontal = value
          break
        }
        case 'verticalAlign': {
          if (!newStyle.alignment) {
            newStyle.alignment = {}
          }
          newStyle.alignment.vertical = value
          break
        }
        default:
          newStyle[key] = value
      }
    })
    return newStyle
  }

export function exportTableToExcel(id, nullToEmpty = true) {
    var theTable = document.getElementById(id);
    var oo = generateArray(theTable);
    var ranges = oo[1];

    /* original data */
    var data = oo[0];
    var ws_name = formatDate(new Date(), 'yyyyMMddHHmmss') + '-' + data.length;

    var wb = new Workbook(), ws = sheet_from_array_of_arrays(data, { nullToEmpty: nullToEmpty });

    /* add ranges to worksheet */
    // ws['!cols'] = ['apple', 'banan'];
    ws['!merges'] = ranges;

    /* add worksheet to workbook */
    wb.SheetNames.push(ws_name);
    wb.Sheets[ws_name] = ws;

    var wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: false, type: 'binary'});

    saveAs(new Blob([s2ab(wbout)], {type: "application/octet-stream"}), "test.xlsx")
}

export function exportJsonToExcelWithGroupHeader({ groupHeader, data, filename='excel-list', autoWidth=true, nullToEmpty=true, headerStyle }={}) {
    headerStyle = parseStyle(headerStyle)
    const wsName = formatDate(new Date(), 'yyyyMMddHHmmss') + '-' + data.length
    const headerRows = generateHeaderRows(groupHeader)
    const header = headerRows.rows
    const merges = headerRows.merges
    data = [...data]
    data.unshift(...header)
    const ws = sheet_from_array_of_arrays(data, { nullToEmpty: nullToEmpty, headerRows: header.length, headerStyle: headerStyle })
    ws['!merges'] = merges

    internalExportJsonToExcel({ ws, wsName, data, filename, autoWidth, nullToEmpty })
}

export function exportJsonToExcel({ header, data, filename='excel-list', autoWidth=true, nullToEmpty=true, headerStyle }={}) {
    headerStyle = parseStyle(headerStyle)
    const wsName = formatDate(new Date(), 'yyyyMMddHHmmss') + '-' + data.length
    data=[...data]
    data.unshift(header);
    const ws = sheet_from_array_of_arrays(data, { nullToEmpty: nullToEmpty, headerRows: 1, headerStyle: headerStyle })

    internalExportJsonToExcel({ ws, wsName, data, filename, autoWidth, nullToEmpty })
}
