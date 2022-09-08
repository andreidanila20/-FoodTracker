<template>
  <v-container class="grey lighten-5">
    <v-row>
      <v-col colos="8">
        <v-card outlined>
          <v-card-title>Create food</v-card-title>
          <v-card-text>
            <v-text-field v-model="f.name" label="Name" outlined></v-text-field>
            <v-text-field
              v-model="f.calories"
              label="Calories"
              outlined
            ></v-text-field>
            <v-text-field
              v-model="f.proteins"
              label="Proteins"
              outlined
            ></v-text-field>
            <v-text-field
              v-model="f.carbohydrates"
              label="Carbohydrates"
              outlined
            ></v-text-field>
            <v-text-field
              v-model="f.lipid"
              label="Lipid"
              outlined
            ></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn outlined rounded text @click="createFood()"> CREATE </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
      <v-col cols="8">
        <v-row>
          <v-col cols="12" lg="4" md="6" xs="12">
            <v-card class="mx-auto" max-width="344" @click="selectFood(f)">
              <v-card-title> {{ f.name }} </v-card-title>

              <v-card-subtitle>
                Calories:{{ f.calories }} Carbohydrates:{{
                  f.carbohydrates
                }}
                Proteins:{{ f.proteins }} Lipid:{{ f.lipid }}
              </v-card-subtitle>

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
import lodash from "lodash";
export default {
  name: "Home",
  data() {
    return {
      f: {
        name: "",
        calories: 0,
        proteins: 0,
        carbohydrates: 0,
        lipid: 0,
      },
      selectedFood: null,
    };
  },
  methods: {
    createFood() {
      axios
        .post(
          "http://localhost:8080/api/food",
          this.f
        )
        .then(() => {
          this.$router.push({name:'Home'});
        });
    },
  },
};
</script>