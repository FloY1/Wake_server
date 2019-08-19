<template>
    <div>
        <form>
            <h1>Sign in</h1>
            <label>User name</label>
            <input required v-model="phone" type="text" placeholder="Snoopy"/>
            <label>Password</label>
            <input required v-model="password" type="password" placeholder="Password"/>
            <hr/>
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
            ...mapActions(['loginAction','addBookingAction','printAction']),
            log(){
                const user = {
                    phone: this.phone,
                    password: this.password
                }
                this.loginAction(user)
            },
            test(){
                const booking = {
                    bookingDate: '06.08.2019',
                    location: 'LOGOISK',
                    reversNumber: '1',
                    startTime: '1100',
                    endTime: '1110',

                }

                this.addBookingAction(booking)
            },
            print(){
                const inform = {
                    bookingDate: '07.08.2019',
                    location: 'LOGOISK',
                }


                this.printAction(inform)
            }
        }
    }
</script>

<style scoped>

</style>