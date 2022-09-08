<template>
  <v-container class="grey lighten-5">
    <v-row>
      <v-col colos="8">
        <v-card outlined>
          <v-card-title>Create MENU</v-card-title>
          <v-card-text>
            <v-text-field
              v-model="menu.name"
              label="Name"
              outlined
            ></v-text-field>

            <v-autocomplete
              v-model="menu.breakfast"
              :items="breakfasts"
              item-text="name"
              outlined
              dense
              chips
              small-chips
              label="Select Breakfast"
              return-object
            ></v-autocomplete>

            <v-autocomplete
              v-model="menu.lunch"
              :items="lunches"
              item-text="name"
              outlined
              dense
              chips
              small-chips
              label="Select Lunch"
              return-object
            ></v-autocomplete>

            <v-autocomplete
              v-model="menu.dinner"
              :items="dinners"
              item-text="name"
              outlined
              dense
              chips
              small-chips
              label="Select Dinner"
              return-object
            ></v-autocomplete>
          </v-card-text>
          <v-card-actions>
            <v-btn outlined rounded text @click="createMenu()"> CREATE </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
      <v-col cols="8">
        <v-row>
          <v-col cols="12" lg="4" md="6" xs="12">
            <v-card class="mx-auto" max-width="344" >
              <v-card-title> {{ menu.name }} </v-card-title>

              <v-row>
                <v-card-subtitle>
                  Breakfast: {{ menu.breakfast.name }}
                </v-card-subtitle>
              </v-row>
              <v-row>
                <v-card-subtitle>
                  Lunch: {{ menu.lunch.name }}
                </v-card-subtitle>
              </v-row>
              <v-row>
                <v-card-subtitle>
                  Dinner: {{ menu.dinner.name }}
                </v-card-subtitle>
              </v-row>
              <v-card-actions>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  name: "Home",
  data() {
    return {
      breakfasts: [],
      lunches:[],
      dinners:[],
      menu: {
        name: "",
        breakfast: [],
        lunch: [],
        dinner: [],
        info: "",
      },
    };
  },
  created() {
    this.loadFood();
  },
  computed: {
    ...mapGetters(["isAuthenticated","userState"]),
  },
  methods: {
    loadFood() {
      axios
        .get("http://localhost:8080/api/breakfast")
        .then((response) => {
          this.breakfasts = response.data;
        })
        .catch((err) => {});

        axios
        .get("http://localhost:8080/api/lunch")
        .then((response) => {
          this.lunches = response.data;
        })
        .catch((err) => {});
        axios
        .get("http://localhost:8080/api/dinner")
        .then((response) => {
          this.dinners = response.data;
        })
        .catch((err) => {});
    },

    addUnseen(){

      axios
        .put(`http://localhost:8080/api/user/notifyObservers/${this.userState.id}`, this.menu)
        .then(() => {
          console.log(this.menu);
        }).catch((err) => {console.log("UPS ERRO2");});

    },
    createMenu() {
      axios.post("http://localhost:8080/api/menu", this.menu).then((response) => {
        this.menu.id=response.data;
        console.log(this.menu);
        this.addUnseen();
        this.$router.push({ name: "Home" });
      }).catch((err) => {console.log("UPS ERROR");});;
    },
  },
};
</script>
