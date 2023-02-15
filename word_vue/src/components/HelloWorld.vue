<template>
  <div class="hello">
    <el-row style="margin-top: 5px;" type="flex" align="middle">
      <el-col align="left" :xs="9" :sm="13">
        <el-select v-model="workbook" placeholder="请选择" size="small" direction="vertical" mode="vertical"
          @change="workbookChange">
          <el-option v-for="item in workLookList" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
        <el-divider class="hidden-md-and-down" direction="vertical"></el-divider>
        <el-link class="hidden-md-and-down" type="primary" :underline="false">单词本共：{{ bookCount }}个单词</el-link>
      </el-col>
      <el-col align="right" :xs="15" :sm="11">
        <div>
          <el-divider direction="vertical"></el-divider>
          <el-switch v-model="prefix" active-text="前缀">
          </el-switch>
          <el-divider direction="vertical"></el-divider>
          <el-switch v-model="suffix" active-text="后缀">
          </el-switch>
          <el-divider class="hidden-md-and-down" direction="vertical"></el-divider>
          <el-button type="primary" icon="el-icon-upload" class="hidden-md-and-down" circle size="mini" @click="toggle">
          </el-button>
        </div>
      </el-col>
    </el-row>
    <el-row style="margin-top: 5px;" align="middle" class="panel" id="panel" ref="panel">
      <el-upload class="upload-demo" :auto-upload="false" action="" align="middle" :http-request="handleUploadForm"
        :limit="1" drag ref="upload1">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip " slot="tip">
          <el-input prefix-icon="el-icon-notebook-1" v-model="dicName" placeholder="请输入单词本名称" style="width: 360px;">
          </el-input>
        </div>
      </el-upload>
      <el-col align="middle" style="padding-top: 5px;">
        <el-button @click="download">下载模板</el-button>
        <el-button @click="uploadFile" type="primary">开始上传</el-button>
      </el-col>
    </el-row>
    <el-row style="margin-top: 5px;" type="flex" align="middle" :gutter="10" justify="space-between">
      <el-col>
        <el-input placeholder="请输入单词或中文" v-model="keyword" clearable @input="searchWord(1)" ref="keySearch">
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </el-col>
    </el-row>
    <el-table :data="tableData" class="tableData" height="calc(var(--vh, 1vh) * 100 - 170px)" stripe>
      <el-table-column prop="word" label="单词" :width="col1Width">
      </el-table-column>
      <el-table-column prop="accent" label="音标/词性" :width="col2Width">
      </el-table-column>
      <el-table-column prop="meanCN" label="中文">
      </el-table-column>
    </el-table>
    <!-- PC -->
    <el-pagination id="page" v-if="show" align="right" style="padding-top: 10px;" background :key="compnentKey"
      layout="total,sizes, pager,prev, next" :total="total" :pagerCount="pagerCount" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" :page-sizes="[15, 50, 80, 100]" :page-size="20">
    </el-pagination>
    <!-- Mobile -->
    <el-pagination v-if="!show" align="right" style="padding-top: 10px;" background layout="pager,prev, next" :key="compnentKey"
      :total="total" :pagerCount="pagerCount" @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :page-sizes="[15, 50, 80, 100]" :page-size="20">
    </el-pagination>
    <el-row v-if="show" style="margin-top: 5px;" type="flex" align="middle" :gutter="10" justify="space-between">
      <el-col>
        <el-link href="http://www.aierx.icu">Copyright © 2022 正月点灯笼</el-link>
      </el-col>
      <el-col align="right">
        <el-link href="https://beian.miit.gov.cn/">湘ICP备2022002942号-1</el-link>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data: function () {
    return {
      keyword: '',
      tableData: [],
      prefix: true,
      suffix: true,
      bookCount: 0,
      curPage: 1,
      pageSize: 15,
      total: 0,
      col1Width: 150,
      col2Width: 150,
      workbook: "",
      dicName: "",
      workLookList: [],
      fileList: [],
      show: true,
      pagerCount: 5,
      compnentKey:0
    }
  },
  methods: {
    forceRender(){
      this.compnentKey += 1;
    },
    toggle() {
      var panel = this.$refs.panel.$el
      if (panel.style.maxHeight) {
        panel.style.maxHeight = null;
      } else {
        panel.style.maxHeight = (panel.scrollHeight + 40) + "px";
      }
    },
    _isMobile() {
      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      return flag;
    },
    handleUploadForm(e) {
      if (this.dicName === "") {
        this.$message.error('单词本名称不能为空');
        return
      }
      var form = new FormData();
      form.append("file", e.file);
      form.append("name", this.dicName)
      this.axios({
        "url": "http://aierx.icu:8080/upload",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      }).then((e) => {
        if (e.status != 200) {
          this.$message.error('上传失败' + e.data.msg);
        }
        this.$message({ message: '上传成功，请刷新页面！', type: 'success' });
      });
    },
    uploadFile() {
      this.$refs.upload1.submit();
    },
    workbookChange() {
      localStorage.setItem("wookBook", this.workbook)
      this.axios.get("http://aierx.icu:8080/count?category=" + this.workbook).then((e) => {
        let data = e.data
        this.bookCount = data.t
      })
    },
    handleSizeChange(e) {
      this.pageSize = e
      this.searchWord(1)
    },
    handleCurrentChange(e) {
      this.curPage = e
      this.searchWord(e)
    },
    getWordBookList() {
      this.axios.get("http://aierx.icu:8080/name").then((e) => {
        this.workLookList = []
        let data = e.data
        data.forEach(element => {
          if (this.workbook === "") {
            this.workbook = element.name
            this.axios.get("http://aierx.icu:8080/count?category=" + this.workbook).then((e) => {
              let data = e.data
              this.bookCount = data.t
            })
          }
          this.workLookList.push({ label: element.name, value: element.name })
        });
      });
    },
    searchWord(curPage) {
      if (this.keyword == "") {
        this.total = 0
        this.tableData = []
        return
      }
      let queryUrl = `http://aierx.icu:8080/query?keyword=${this.suffix ? "*" : ""}${this.keyword}${this.prefix ? "*" : ""}&category=${this.workbook}&pageSize=${this.pageSize}&curpage=${(curPage - 1) * this.pageSize + 1}`
      let that = this
      this.axios.get(queryUrl).then((e) => {
        let data = e.data
        that.tableData = data.data
        that.total = data.totalSize
      })
    },
    // 节流和防抖
    debounce(callback) {
      let timer = null
      return function () {
        clearTimeout(timer)
        timer = setTimeout(() => {
          callback.apply(this)
        }, 200)
      }
    },
    // 下载文件
    download() {
      window.open("http://word.aierx.icu/upload/template.xlsx")
    }
  },
  mounted() {
    let h = 0;
    if (this._isMobile()) {
      this.show = false
      h = 30
    }
    this.getWordBookList();
    // 获取页面高度
    let vh = (window.innerHeight + h) * 0.01
    document.documentElement.style.setProperty('--vh', `${vh}px`)
    window.addEventListener('resize', () => {
      let vh = (window.innerHeight + h) * 0.01
      document.documentElement.style.setProperty('--vh', `${vh}px`)
    })
    //禁止页面滑动
    document.body.style.overflow = 'hidden';
    document.addEventListener('touchmove', {
      passive: false
    }, false);
    if (localStorage.getItem("wookBook")) {
      this.workbook = localStorage.getItem("wookBook")
      this.axios.get("http://aierx.icu:8080/count?category=" + this.workbook).then((e) => {
        let data = e.data
        this.bookCount = data.t
      })
    }
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import 'element-ui/lib/theme-chalk/display.css';

@media (min-width:768px) {
  .hello {
    width: 80%;
    margin: auto
  }


}

.panel {
  padding: 0 18px;
  background-color: white;
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.3s ease-out;
}
</style>
