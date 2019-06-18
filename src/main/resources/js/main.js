
import Vue from 'vue'
import '@babel/polyfill'
import 'api/resource'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import  store from 'store/store'
import router from 'route/router'
import App from 'pages/App.vue'
import {connect } from './util/ws'


connect()

Vue.use(Vuetify)



new Vue({
    el: '#app',
    store,
    router,
    render: a => a(App)
})


