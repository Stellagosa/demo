<template>
  <div class="nav">
    <router-link to="/user">用户管理</router-link>
    <router-link to="/upload">上传</router-link>
  </div>
</template>

<script>
import {defineComponent} from "vue";

export default defineComponent({
  name: "index",
  setup() {

  }
})
</script>

<style lang="scss" scoped>
.nav {
  width: 200px;
  height: 400px;
  border: 1px solid #e5dfdf;
  margin: 100px auto;
  padding: 40px;
  a {
    display: block;
    margin-bottom: 10px;
  }
}

</style>