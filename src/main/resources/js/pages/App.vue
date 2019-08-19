<template>
    <div>

        <login-page v-if="!isUser"></login-page>
        <v-btn @click="log"  >Login</v-btn>
        <v-btn @click="test"  >test</v-btn>
        <v-btn @click="print"  download="" >Скачать файл</v-btn>

    </div>
</template>

<script>
    import BookingList from 'components/BookingList.vue'
    import {addHandler} from "util/ws"
    import { mapActions } from 'vuex'



    import LoginPage from 'pages/LoginPage.vue'
    export default {

        components:{
            BookingList,
            LoginPage
        },
        methods:{
            ...mapActions(['getBookingAction']),
        },
        created() {
            addHandler(data => {
                    console.info(data)

            }),
            this.getBookingAction()
        }
    }
</script>


<style scoped>

</style>