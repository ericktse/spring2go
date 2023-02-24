// 添加日期范围
export function addDateRange(params, dateRange, propName) {
  params =
    typeof params === 'object' && params !== null && !Array.isArray(params)
      ? params
      : {};

  dateRange = Array.isArray(dateRange) ? dateRange : [];
  if (typeof propName === 'undefined') {
    params['timeBegin'] = dateRange[0];
    params['timeEnd'] = dateRange[1];
  } else {
    params[propName + 'Begin'] = dateRange[0];
    params[propName + 'End'] = dateRange[1];
  }
  return params;
}
