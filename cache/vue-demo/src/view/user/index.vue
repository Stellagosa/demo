<template>
  <div class="user-container">
    <div>
      <el-button type="primary" @click="showAddUserDialog">添加用户</el-button>
      <div>
        <el-input v-model="condition" placeholder="输入用户名查找用户"/>
        <el-button type="primary" @click="getData">查找</el-button>
      </div>
    </div>
    <el-table
        :data="state.tableData"
        stripe
        :header-cell-style="{'text-align': 'center'}"
        :cell-style="{'text-align': 'center'}"
        style="width: 100%">
      <el-table-column
          fixed="true"
          label="ID"
          prop="id"
          width="150">
      </el-table-column>
      <el-table-column
          label="姓名"
          prop="username"
          width="120">
      </el-table-column>
      <el-table-column
          label="手机号"
          prop="phone"
          width="120">
      </el-table-column>
      <el-table-column
          label="简介"
          prop="description"
          width="120">
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <el-dialog title="添加用户" v-model="addUserDialogFormVisible">
    <el-form :model="addUserForm">
      <el-form-item label="用户名" label-width="120px">
        <el-input v-model="addUserForm.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="手机号" label-width="120px">
        <el-input v-model="addUserForm.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="简介" label-width="120px">
        <el-input v-model="addUserForm.description" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
    <span class="dialog-footer">
      <el-button @click="addUserDialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="addUserCommit">确 定</el-button>
    </span>
    </template>
  </el-dialog>

  <el-dialog title="编辑用户" v-model="editUserDialogFormVisible">
    <el-form :model="editUserForm">
      <el-form-item label="用户名" label-width="120px">
        <el-input v-model="editUserForm.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="手机号" label-width="120px">
        <el-input v-model="editUserForm.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="简介" label-width="120px">
        <el-input v-model="editUserForm.description" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
    <span class="dialog-footer">
      <el-button @click="editUserDialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="editUserCommit">确 定</el-button>
    </span>
    </template>
  </el-dialog>

</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref} from "vue";
import {addUser, deleteUser, search, updateUser} from "../../api/user";
import {ElMessage, ElMessageBox} from "element-plus";

export default defineComponent({
  name: "index",
  setup() {
    const state = reactive({
      tableData: []
    })
    const addUserDialogFormVisible = ref(false)
    const addUserForm = reactive({
      username: '',
      phone: '',
      description: ''
    })
    const editUserDialogFormVisible = ref(false)
    const editUserForm = reactive({
      id: '',
      username: '',
      phone: '',
      description: ''
    })
    const condition = ref('')
    const getData = () => {
      search(condition.value).then((data) => {
        state.tableData = data.data
      }).catch(() => {
        ElMessage({
          type: 'error',
          message: '获取失败！'
        });
      })
    }
    onMounted(async () => {
      await getData()
    })
    const handleEdit = (index: number, row: any) => {
      editUserForm.id = row.id
      editUserForm.username = row.username
      editUserForm.phone = row.phone
      editUserForm.description = row.description
      editUserDialogFormVisible.value = true
    }
    const handleDelete = (index: number, row: any) => {
      ElMessageBox.confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUser(row.id).then(() => {
          ElMessage({
            type: 'success',
            message: '删除成功!'
          });
        }).then(() => {
          getData()
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: '已取消删除'
        });
      });
    }

    const showAddUserDialog = () => {
      addUserForm.username = ''
      addUserForm.phone = ''
      addUserForm.description = ''
      addUserDialogFormVisible.value = true
    }
    const addUserCommit = () => {
      addUser(addUserForm).then(() => {
        ElMessage({
          type: 'success',
          message: '删除成功!'
        });
      }).then(() => {
        addUserForm.username = ''
        addUserForm.phone = ''
        addUserForm.description = ''
        addUserDialogFormVisible.value = false
        getData()
      }).catch((error) => {
        ElMessage({
          type: 'error',
          message: '添加失败！'
        });
      })
    }
    const editUserCommit = () => {
      updateUser(editUserForm).then(() => {
        ElMessage({
          type: 'success',
          message: '修改成功!'
        });
      }).then(() => {
        editUserForm.id = ''
        editUserForm.username = ''
        editUserForm.phone = ''
        editUserForm.description = ''
        editUserDialogFormVisible.value = false
        getData()
      }).catch((error) => {
        ElMessage({
          type: 'error',
          message: '修改失败！'
        });
      })
    }

    return {
      state,
      addUserDialogFormVisible,
      editUserDialogFormVisible,
      addUserForm,
      editUserForm,
      condition,
      handleEdit,
      handleDelete,
      showAddUserDialog,
      addUserCommit,
      editUserCommit,
      getData
    }
  }
})
</script>

<style lang="scss" scoped>
.user-container {
  margin: 100px auto;
  padding: 40px;
  width: 700px;
  border: 1px solid #e5dfdf;
}

</style>