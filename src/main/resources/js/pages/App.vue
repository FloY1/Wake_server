<template>
    <login-page></login-page>
</template>

<script>
    import BookingList from 'components/BookingList.vue'
    import {addHandler} from "util/ws"



    import LoginPage from 'pages/LoginPage.vue'
    export default {
        components:{
            BookingList,
            LoginPage
        },
        created() {
            addHandler(data => {
                    console.info(data)

            })
        }
    }
</script>


<style scoped>

</style>