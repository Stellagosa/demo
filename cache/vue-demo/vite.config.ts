import vue from '@vitejs/plugin-vue'
import styleImport from 'vite-plugin-style-import'
import { resolve } from 'path'

export default () => {
  process.cwd();
  return {
    resolve: {
      alias: {
        "@": resolve(__dirname, '/src'),
        "#": resolve(__dirname, '/types')
      }
    },
    plugins: [
      vue(),
      styleImport({
        libs: [ {
          libraryName: 'element-plus',
          resolveStyle: (name) => {
            name = name.slice(3)
            return `element-plus/packages/theme-chalk/src/${ name }.scss`
          },
          resolveComponent: (name) => {
            return `element-plus/lib/${ name }`
          }
        } ]
      })
    ]
  }
}
