<template>
  <div id="app">
    <v-app>
      <v-dialog v-model="dialog" persistent max-width="600px" min-width="360px">
        <div>
          <v-tabs
            v-model="tab"
            show-arrows
            background-color="deep-purple accent-4"
            icons-and-text
            dark
            grow
          >
            <v-tabs-slider color="purple darken-4"></v-tabs-slider>
            <v-tab v-for="i in tabs" :key="i">
              <v-icon large>{{ i.icon }}</v-icon>
              <div class="caption py-1">{{ i.name }}</div>
            </v-tab>
            <v-tab-item>
              <v-card class="px-4">
                <v-card-text>
                  <v-form ref="loginForm" v-model="valid" lazy-validation>
                    <v-row>
                      <v-col cols="12">
                        <v-text-field
                          v-model="loginEmail"
                          :rules="loginEmailRules"
                          label="E-mail"
                          required
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          v-model="loginPassword"
                          :append-icon="show1 ? 'eye' : 'eye-off'"
                          :rules="[rules.required, rules.min]"
                          :type="show1 ? 'text' : 'password'"
                          name="input-10-1"
                          label="Password"
                          hint="At least 8 characters"
                          counter
                          @click:append="show1 = !show1"
                        ></v-text-field>
                      </v-col>
                      <v-col class="d-flex" cols="12" sm="6" xsm="12">
                        <v-col v-if="wrongEmailOrPass == true">
                          <span class="red--text">
                            Wrong email or password
                          </span>
                        </v-col>
                      </v-col>

                      <v-spacer></v-spacer>
                      <v-col class="d-flex" cols="12" sm="3" xsm="12" align-end>
                        <v-btn
                          x-large
                          block
                          :disabled="!valid"
                          color="success"
                          @click="validate"
                        >
                          Login
                        </v-btn>
                      </v-col>
                    </v-row>
                  </v-form>
                </v-card-text>
              </v-card>
            </v-tab-item>
            <v-tab-item>
              <v-card class="px-4">
                <v-card-text>
                  <v-form ref="registerForm" v-model="valid" lazy-validation>
                    <v-row>
                      <v-col cols="12">
                        <v-text-field
                          v-model="user.name"
                          :rules="[rules.required]"
                          label="Name"
                          maxlength="30"
                          required
                        ></v-text-field>
                      </v-col>

                      <v-col class="d-flex" cols="12" sm="3" md="3">
                        <v-select
                          v-model="user.gender"
                          :items="gender"
                          :rules="[rules.required]"
                          label="Gender"
                          dense
                          solo
                          required
                        ></v-select>
                      </v-col>

                      <v-col cols="12" sm="3" md="3">
                        <v-text-field
                          v-model="user.age"
                          :rules="isNumberRule"
                          label="Age"
                          maxlength="3"
                          required
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="3" md="3">
                        <v-text-field
                          v-model="user.height"
                          :rules="isNumberRule"
                          label="Height"
                          maxlength="3"
                          required
                        ></v-text-field>
                      </v-col>

                      <v-col cols="12" sm="3" md="3">
                        <v-text-field
                          v-model="user.pounds"
                          :rules="isFloatRule"
                          label="Pounds"
                          maxlength="6"
                          required
                        ></v-text-field>
                      </v-col>

                      <v-col cols="12">
                        <v-text-field
                          v-model="user.email"
                          :rules="emailRules"
                          label="E-mail"
                          required
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          v-model="user.password"
                          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                          :rules="[rules.required, rules.min]"
                          :type="show1 ? 'text' : 'password'"
                          name="input-10-1"
                          label="Password"
                          hint="At least 8 characters"
                          counter
                          @click:append="show1 = !show1"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          block
                          v-model="verify"
                          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                          :rules="[rules.required, passwordMatch]"
                          :type="show1 ? 'text' : 'password'"
                          name="input-10-1"
                          label="Confirm Password"
                          counter
                          @click:append="show1 = !show1"
                        ></v-text-field>
                      </v-col>

                      <v-col class="d-flex" cols="12" sm="6" xsm="12">
                        <v-col v-if="emailExists == true">
                          <span class="black--text">
                            Successful registration, please log in
                          </span>
                        </v-col>
                      </v-col>

                      <v-spacer></v-spacer>
                      <v-col class="d-flex ml-auto" cols="12" sm="3" xsm="12">
                        <v-btn
                          x-large
                          block
                          :disabled="!valid"
                          color="success"
                          @click="validate"
                          >Register</v-btn
                        >
                      </v-col>
                    </v-row>
                  </v-form>
                </v-card-text>
              </v-card>
            </v-tab-item>
          </v-tabs>
        </div>
      </v-dialog>
    </v-app>
  </div>
</template>


 <script>
import axios from "axios";
var ws;
export default {
  name: "Home",

  created() {
    ws = new WebSocket("ws://localhost:8080/loginWS");
   
  },
  data: () => ({
    emailExists: false,
    wrongEmailOrPass: false,
    dialog: true,
    tab: 0,
    gender: ["M", "F"],
    tabs: [
      { name: "Login", icon: "mdi-account" },
      { name: "Register", icon: "mdi-account-outline" },
    ],
    valid: true,

    userVerify: {
      admin: false,
      name: "",
      email: "",
      password: "",
      gender: "",
      age: 0,
      height: 0,
      pounds: 0.0,
    },

    user: {
      name: "",
      email: "",
      password: "",
      gender: "",
      age: 0,
      height: 0,
      pounds: 0,
    },

    verify: "",
    loginPassword: "",
    loginEmail: "",
    loginEmailRules: [
      (v) => !!v || "Required",
      (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
    ],
    emailRules: [
      (v) => !!v || "Required",
      (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
    ],

    isNumberRule: [
      (v) => !!v || "Required",
      (v) => /[0-9]+/.test(v) || "Must be a number",
    ],

    isFloatRule: [
      (v) => !!v || "Required",
      (v) => /[0-9]+.[0-9]+/.test(v) || "Must be a float number",
    ],

    show1: false,
    rules: {
      required: (value) => !!value || "Required.",
      min: (v) => (v && v.length >= 8) || "Min 8 characters",
    },
  }),
  computed: {
    passwordMatch() {
      return () => this.user.password === this.verify || "Password must match";
    },
  },
  methods: {
    validate() {
      if (this.$refs.loginForm.validate()) {
        this.userVerify.email = this.loginEmail;
        this.userVerify.password = this.loginPassword;
        axios
          .post("http://localhost:8080/api/user/login", this.userVerify)
          .then((response) => {
            if (response.data != null) {
              this.wrongEmailOrPass = false;
              this.userVerify = response.data;
              this.$store.dispatch("login", response.data);
              this.$store.dispatch(
                "getUnseenMenus",
                this.userVerify.unseenMenus
              );
              var data = JSON.stringify({
                user: response.data.name,
              });
              ws.send(data);
              this.$router.push({ name: "Home" });
            } else {
              console.log("Not so good");
              this.wrongEmailOrPass = true;
            }
          })
          .catch((error) => {
            console.log("Not so good");
          });
      }

      if (this.$refs.registerForm.validate()) {
        axios.post("http://localhost:8080/api/user", this.user).then(() => {
          this.emailExists = true;
        });
      }
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
  },
};
</script>