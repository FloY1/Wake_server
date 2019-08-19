import Vue from 'vue';
import Vuex from 'vuex';
import AuthApi from 'api/Auth'
import BookingApi from 'api/booking'
import DownoladFile from 'api/downoladFile'
Vue.use(Vuex);

var token = localStorage.getItem('user-token')

Vue.http.interceptors.push(function(request) {
    if(token) {
        request.headers.set('Authorization', 'Bearer_' + to )
    }
});


export default new Vuex.Store({
    state: {
        allBooking: null,
        user: null,
        isUser: token ? true:false
    },
    getters:{

    },
    mutations: {
        initalizationBookingMutation(state, booking) {
            state.allBooking = booking
        }

        
    },
    actions: {
        async loginAction({commit, state}, user) {
            const result = await AuthApi.add(user)
            if(result.status == HTTP_STATUS_OK) {
                const data = await result.json()
                localStorage.setItem('user-token', data.token)
                this.isUser = true;
            }

        },
        async addBookingAction({commit, state}, booking) {
            const result = await BookingApi.add(booking)
            const data = await result.json()
            console.log(data);
        },
        async getBookingAction({commit, state}){
            const result = await BookingApi.get()
            const data = await result.json()
            commit('initalizationBookingMutation',data)
        },
        async printAction({commit, state},inform){
            const result = await DownoladFile.add(inform)
            console.log(inform+ result);
            window.open("print")

        }

    }
})