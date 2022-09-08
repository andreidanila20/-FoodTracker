<template>
  <v-container v-if="isAuthenticated" class="grey lighten-5">
    <h1>MY MENUS</h1>

    <v-row>
      <v-col cols="8" lg="4" md="6" xs="12" v-for="m in menus" :key="m.id">
        <v-card class="mx-auto my-5">
          <template slot="progress">
            <v-progress-linear
              color="deep-purple"
              height="10"
              indeterminate
            ></v-progress-linear>
          </template>
          <v-card-title> {{ m.name }} </v-card-title>

          <v-card-text>
            <div class="my-4 subtitle-1">
              • Breakfast: {{ m.breakfast.name }}
            </div>

            <div>Breakfast info: {{ m.breakfast.info }}</div>
          </v-card-text>
          <v-divider class="mx-4"></v-divider>
          <v-card-text>
            <div class="my-4 subtitle-1">• Lunch: {{ m.lunch.name }}</div>

            <div>Lunch info: {{ m.lunch.info }}</div>
          </v-card-text>
          <v-card-text>
            <v-divider class="mx-4"></v-divider>
            <div class="my-4 subtitle-1">• Dinner: {{ m.dinner.name }}</div>

            <div>Dinner info: {{ m.dinner.info }}</div>
          </v-card-text>
          <v-divider class="mx-4"></v-divider>

          <v-card-subtitle>
          
            <v-row> Calories: {{ m.nutrition.calories }} </v-row>
            
            <v-row > Proteins:{{ m.nutrition.proteins }} </v-row>
            
            <v-row > Lipid:{{ m.nutrition.lipid }} </v-row>
          
            <v-row >
              Carbohydrates:{{ m.nutrition.carbohydrates }}
            </v-row>
          </v-card-subtitle>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon @click.stop="deleteMenu(m)">
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
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
      menus: [],
    };
  },
  created() {
    this.loadMenus();
  },
  methods: {
    loadMenus() {
      axios
        .get(`http://localhost:8080/api/user/${this.userState.id}`)
        .then((response) => {
          this.menus = response.data.menu;
        })
        .catch((err) => {});
    },
    deleteMenu(m) {
      axios
        .put(
          `http://localhost:8080/api/user/deleteMenu/${this.userState.id}`,
          m
        )
        .then((response) => {
          this.loadMenus();
        });
    },
    
  },
  computed: {
    ...mapGetters(["isAuthenticated", "userState"]),
  },
};
</script>

<style>
h1 {
  text-align: left;
}
</style>