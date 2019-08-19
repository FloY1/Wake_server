import Vue from 'vue'

const  booking = Vue.resource('user/print')

export default {
    get: inform => booking.get({},inform),
    add: inform => booking.save({},inform)

}