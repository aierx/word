<template>
  <div id="app">
    <HelloWorld />
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'

export default {
  name: 'app',
  components: {
    HelloWorld
  }
}
</script>

<style>
@media (min-width:768px) {
  .el-table__body-wrapper::-webkit-scrollbar {
    width: 6px;
    height: 8px;
  }

  .el-table__body-wrapper::-webkit-scrollbar-track {
    border-radius: 10px;
    box-shadow: inset 0 0 6px rgba(238, 238, 238, 0.3);
    background-color: #eeeeee;
  }

  .el-table__body-wrapper::-webkit-scrollbar-thumb {
    border-radius: 10px;
    box-shadow: inset 0 0 6px rgba(145, 143, 0143, 0.3);
    background-color: rgb(145, 143, 143);
  }
}
</style>
