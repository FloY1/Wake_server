<template>
    <div>
        <form>
            <h1>Sign in</h1>
            <label>User name</label>
            <input required v-model="phone" type="text" placeholder="Snoopy"/>
            <label>Password</label>
            <input required v-model="password" type="password" placeholder="Password"/>
            <hr/>
            <v-btn @click="log"  >Login</v-btn>
            <v-btn @click="test"  >test</v-btn>
        </form>
    </div>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        data() {
            return {
                phone: '',
                password: '',
            }
        },
        methods: {
            ...mapActions(['login','addBookingAction']),
            log(){
                const user = {
                    phone: this.phone,
                    password: this.password
                }
                this.login(user)
            },
            test(){
                const booking = {
                    bookingDate: '12',
                    location: 'LOGOISK',
                    reversNumber: '1',
                    startTime: '23',
                    endTime: '34',

                }
                console.log(booking)
                this.addBookingAction(booking)
            }
        }
    }
</script>

<style scoped>

</style>