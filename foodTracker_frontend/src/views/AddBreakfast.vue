<template>
  <v-container class="grey lighten-5">
    <v-row>
      <v-col colos="8">
        <v-card outlined>
          <v-card-title>Create Breakfast Recipes</v-card-title>
          <v-card-text>
            <v-text-field
              v-model="breakfast.name"
              label="Name"
              outlined
            ></v-text-field>

            <v-autocomplete
              v-model="breakfast.foods"
              :items="food"
              item-text="name"
              outlined
              dense
              chips
              small-chips
              label="Select ingredients"
              multiple
              return-object
            ></v-autocomplete>
            <!-- De intrebat aici daca pot face cumva sa am eticheta dinamic -->
            <v-text-field
              v-for="(item, index) in breakfast.foods"
              :item="item"
              :index="index"
              :key="item.id"
              v-model="grams[index]"
              label="Grame"
              outlined
            ></v-text-field>
            <v-text-field
              v-model="breakfast.info"
              label="Info"
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
            <v-card class="mx-auto" max-width="344">
              <v-card-title> {{ breakfast.name }} </v-card-title>

              <v-row>
                <v-card-subtitle>
                  Ingredients:{{ breakfast.foods }}
                </v-card-subtitle>
              </v-row>
              <v-row>
                <v-card-subtitle> Info:{{ breakfast.info }} </v-card-subtitle>
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

export default {
  name: "Home",
  data() {
    return {
      food: [],
      grams:[],
      breakfast: {
        name: "",
        foods: [],
        calories: 0,
        proteins: 0.0,
        lipid: 0.0,
        carbohydrates: 0.0,
        info: "",
      },
      i:0,
      selectedFood: null,
    };
  },
  created() {
    this.loadFood();
  },
  methods: {
    loadFood() {
      axios
        .get("http://localhost:8080/api/food")
        .then((response) => {
          this.food = response.data;
        })
        .catch((err) => {});
    },
    getNutrients() {
      
      for (var prop in this.breakfast.foods) {
        if (this.breakfast.foods.hasOwnProperty(prop)) {
          this.breakfast.calories+=(this.grams[this.i]*this.breakfast.foods[prop].calories)/100.0;
          this.breakfast.proteins+=(this.grams[this.i]*this.breakfast.foods[prop].proteins)/100.0;
          this.breakfast.carbohydrates+=(this.grams[this.i]*this.breakfast.foods[prop].carbohydrates)/100.0;
          this.breakfast.lipid+=(this.grams[this.i]*this.breakfast.foods[prop].lipid)/100.0;
          this.i=this.i+1;
        }
        
      }
      console.log(this.breakfast.calories+" "+this.breakfast.proteins+" "+this.breakfast.carbohydrates+" "+this.breakfast.lipid);
      
    },

    createFood() {
      this.getNutrients();
      axios
        .post("http://localhost:8080/api/breakfast", this.breakfast)
        .then(() => {
          this.$router.push({ name: "FoodAdmin" });
        })
        .catch((err) => {
          console.log("UPS ERROR");
        });
    },
  },
};
</script>