<template>
   <div class="app-container">
      <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
         <el-form-item label="系统模块" prop="title">
            <el-input v-model="queryParams.title" placeholder="请输入系统模块" clearable style="width: 240px;"
               @keyup.enter="handleQuery" />
         </el-form-item>
         <el-form-item label="操作人员" prop="createBy">
            <el-input v-model="queryParams.createBy" placeholder="请输入操作人员" clearable style="width: 240px;"
               @keyup.enter="handleQuery" />
         </el-form-item>
         <el-form-item label="状态" prop="responseStatus">
            <el-select v-model="queryParams.responseStatus" placeholder="操作状态" clearable style="width: 240px">
               <el-option v-for="dict in sys_common_status" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
         </el-form-item>
         <el-form-item label="操作时间" style="width: 308px">
            <el-date-picker v-model="dateRange" value-format="YYYY-MM-DD" type="daterange" range-separator="-"
               start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
         </el-form-item>
         <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
         </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
         <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport"
               v-hasPermi="['system:operlog:export']">导出</el-button>
         </el-col>
         <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table ref="operlogRef" v-loading="loading" :data="operlogList" @selection-change="handleSelectionChange"
         :default-sort="defaultSort" @sort-change="handleSortChange">
         <el-table-column type="selection" width="55" align="center" />
         <el-table-column label="日志编号" align="center" prop="id" width="180" :show-overflow-tooltip="true" />
         <el-table-column label="系统模块" align="center" prop="title" width="180" :show-overflow-tooltip="true" />
         <el-table-column label="请求URL" align="center" prop="requestUri" />
         <el-table-column label="请求方式" align="center" prop="method" />
         <el-table-column label="请求参数" align="left" prop="params" :show-overflow-tooltip="true" />
         <el-table-column label="请求结果" align="left" prop="result" width="250" :show-overflow-tooltip="true" />
         <el-table-column label="操作人员" align="center" prop="createBy" :show-overflow-tooltip="true" sortable="custom"
            :sort-orders="['descending', 'ascending']" width="100" />
         <el-table-column label="主机" align="center" prop="remoteAddr" width="130" :show-overflow-tooltip="true" />
         <el-table-column label="执行时间/ms" align="center" prop="time" width="100" />
         <el-table-column label="操作状态" align="center" prop="responseStatus">
            <template #default="scope">
               <dict-tag :options="sys_common_status" :value="scope.row.responseStatus" />
            </template>
         </el-table-column>
         <el-table-column label="操作日期" align="center" prop="createTime" sortable="custom"
            :sort-orders="['descending', 'ascending']" width="180">
            <template #default="scope">
               <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
         </el-table-column>
         <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template #default="scope">
               <el-button type="text" icon="View" @click="handleView(scope.row, scope.index)"
                  v-hasPermi="['system:operlog:query']">详细</el-button>
            </template>
         </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
         v-model:limit="queryParams.pageSize" @pagination="getList" />

      <!-- 操作日志详细 -->
      <el-dialog title="操作日志详细" v-model="open" width="700px" append-to-body>
         <el-form :model="form" label-width="100px">
            <el-row>
               <el-col :span="12">
                  <el-form-item label="操作模块：">{{ form.title }}</el-form-item>
                  <el-form-item label="登录信息：">{{ form.createBy }} / {{ form.remoteAddr }}
                  </el-form-item>
               </el-col>
               <el-col :span="12">
                  <el-form-item label="请求地址：">{{ form.requestUri }}</el-form-item>
                  <el-form-item label="请求代理：">{{ form.userAgent }}</el-form-item>
               </el-col>
               <el-col :span="24">
                  <el-form-item label="操作方法：">{{ form.method }}</el-form-item>
                  <el-form-item label="执行时间：">{{ form.time }} ms</el-form-item>
               </el-col>
               <el-col :span="24">
                  <el-form-item label="请求参数：">{{ form.params }}</el-form-item>
               </el-col>
               <el-col :span="24">
                  <el-form-item label="返回参数：">{{ form.result }}</el-form-item>
               </el-col>
               <el-col :span="12">
                  <el-form-item label="操作状态：">
                     {{ statesFormat(form) }}
                  </el-form-item>
               </el-col>
               <el-col :span="12">
                  <el-form-item label="操作时间：">{{ parseTime(form.createTime) }}</el-form-item>
               </el-col>
               <el-col :span="24">
                  <el-form-item label="异常信息：" v-if="form.status === 500">{{ form.exception }}</el-form-item>
               </el-col>
            </el-row>
         </el-form>
         <template #footer>
            <div class="dialog-footer">
               <el-button @click="open = false">关 闭</el-button>
            </div>
         </template>
      </el-dialog>
   </div>
</template>

<script setup name="Operlog">
import { list, delOperlog, cleanOperlog } from "@/api/monitor/operlog";

const { proxy } = getCurrentInstance();
const { sys_oper_type, sys_common_status } = proxy.useDict("sys_oper_type", "sys_common_status");

const operlogList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);
const defaultSort = ref({ prop: "operTime", order: "descending" });

const data = reactive({
   form: {},
   queryParams: {
      pageNum: 1,
      pageSize: 10,
      title: undefined,
      operName: undefined,
      businessType: undefined,
      status: undefined
   }
});

const { queryParams, form } = toRefs(data);

/** 查询登录日志 */
function getList() {
   loading.value = true;

   if (dateRange.value[0] !== 'undefined' && dateRange.value[1] !== 'undefined') {
      queryParams.value.beginTime = dateRange.value[0];
      queryParams.value.endTime = dateRange.value[1];
   }

   list(queryParams.value).then(response => {
      operlogList.value = response.data.records;
      total.value = response.data.total;
      loading.value = false;
   });

   // list(proxy.addDateRange(queryParams.value, dateRange.value)).then(response => {
   //    operlogList.value = response.data.records;
   //    total.value = response.data.total;
   //    loading.value = false;
   // });
}
/** 操作日志类型字典翻译 */
function typeFormat(row, column) {
   return proxy.selectDictLabel(sys_oper_type.value, row.businessType);
}

function statesFormat(row, column) {
   return proxy.selectDictLabel(sys_common_status.value, row.responseStatus);
}
/** 搜索按钮操作 */
function handleQuery() {
   queryParams.value.pageNum = 1;
   getList();
}
/** 重置按钮操作 */
function resetQuery() {
   dateRange.value = [];
   proxy.resetForm("queryRef");
   proxy.$refs["operlogRef"].sort(defaultSort.value.prop, defaultSort.value.order);
   handleQuery();
}
/** 多选框选中数据 */
function handleSelectionChange(selection) {
   ids.value = selection.map(item => item.operId);
   multiple.value = !selection.length;
}
/** 排序触发事件 */
function handleSortChange(column, prop, order) {
   queryParams.value.orderByColumn = column.prop;
   queryParams.value.isAsc = column.order;
   getList();
}
/** 详细按钮操作 */
function handleView(row) {
   open.value = true;
   form.value = row;
}
/** 删除按钮操作 */
function handleDelete(row) {
   const operIds = row.operId || ids.value;
   proxy.$modal.confirm('是否确认删除日志编号为"' + operIds + '"的数据项?').then(function () {
      return delOperlog(operIds);
   }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
   }).catch(() => { });
}
/** 清空按钮操作 */
function handleClean() {
   proxy.$modal.confirm("是否确认清空所有操作日志数据项?").then(function () {
      return cleanOperlog();
   }).then(() => {
      getList();
      proxy.$modal.msgSuccess("清空成功");
   }).catch(() => { });
}
/** 导出按钮操作 */
function handleExport() {
   proxy.download("monitor/operlog/export", {
      ...queryParams.value,
   }, `config_${new Date().getTime()}.xlsx`);
}

getList();
</script>
