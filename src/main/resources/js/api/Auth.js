import Vue from 'vue'

const  login = Vue.resource('login')

export default {
    add: users => login.save({},users),
}