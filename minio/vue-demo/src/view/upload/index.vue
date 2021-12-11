<template>
  <div class="upload-container">
    <el-upload
        ref="upload"
        :auto-upload="false"
        :before-upload="beforeAvatarUpload"
        :file-list="state.fileList"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        multiple
        show-file-list
        action="http://localhost:1001/upload"
        class="upload-demo">
      <template #trigger>
        <el-button size="small" type="primary">选取文件</el-button>
      </template>
      <el-button size="small" style="margin-left: 10px;" type="success" @click="submitUpload">上传到服务器</el-button>
      <template #tip>
        <div class="el-upload__tip">
          上传文件不能超过 200M
        </div>
      </template>
    </el-upload>
  </div>
</template>

<script lang="ts">
import {defineComponent, reactive, ref} from "vue";
import {ElMessage} from "element-plus";

export default defineComponent({
  name: "index",
  setup() {
    const state = reactive({
      fileList: []
    })
    const upload = ref()

    const beforeAvatarUpload = (file: any) => {
      const isLt2M = file.size / 1024 / 1024 < 200;

      if (!isLt2M) {
        ElMessage.error('上传头像图片大小不能超过 200MB!');
      }

      return isLt2M;
    }

    const handlePreview = (file: any) => {
      console.log(file)
    }

    const submitUpload = () => {
      upload.value.submit()
    }
    const handleRemove = (file: any, fileList: any) => {
      console.log(file, fileList);
    }

    return {
      state,
      upload,
      handlePreview,
      submitUpload,
      handleRemove,
      beforeAvatarUpload
    }

  }
})
</script>

<style lang="scss" scoped>
.upload-container {
  height: 200px;
  width: 350px;
  border: 1px solid #e5dfdf;
  margin: 100px auto;
  padding: 70px;
}
</style>