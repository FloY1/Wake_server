import Vue from 'vue'

const  booking = Vue.resource('user/booking')

export default {
    get:() => booking.get(),
    add: message => booking.save({},message),
    update: message => booking.update({id: message.id},message),
    remove: id => booking.remove({id})

}