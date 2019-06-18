import Vue from 'vue';
import Vuex from 'vuex';
import AuthApi from 'api/Auth'
import BookingApi from 'api/booking'
Vue.use(Vuex);


Vue.http.interceptors.push(function(request) {
    var to = localStorage.getItem('user-token')
    if(to) {
        request.headers.set('Authorization', 'Bearer_' + to )
    }
});


export default new Vuex.Store({
    state: {
        allBooking: bookigList
    },
    mutations: {


        
    },
    actions: {
        async login({commit, state}, user) {
            const result = await AuthApi.add(user)
            const data = await result.json()
            localStorage.setItem('user-token', data.token)

        },
        async addBookingAction({commit, state}, booking) {
            const result = await BookingApi.add(booking)
            const data = await result.json()
            console.log(data);
        }
    }
})