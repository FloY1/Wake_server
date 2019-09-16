<template>
    <div>

        <v-content>
            <v-container fluid fill-height>
                <v-layout  align-center justify-center>
                    <v-flex
                            xs12
                            sm6
                            md2>
        <form justify="center">
             <v-text-field label="Номер Телефона" v-model="phone">
            </v-text-field>
ы
            <v-text-field
                    v-model="password"
                    :append-icon="show1 ? 'visibility' : 'visibility_off'"
                    :rules="[rules.required, rules.min]"
                    :type="show1 ? 'text' : 'password'"

                    label="Normal with hint text"
                    hint="At least 8 characters"
                    counter
                    @click:append="show1 = !show1"
            ></v-text-field>

            <v-btn @click="log"  >Login</v-btn>

        </form>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-content>

    </div>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        data() {
            return {
                phone: '',
                password: '',
                rules: {
                    required: value => !!value || 'Required.',
                    min: v => v.length >= 8 || 'Min 8 characters',
                    emailMatch: () => ('The email and password you entered don\'t match'),
                }
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