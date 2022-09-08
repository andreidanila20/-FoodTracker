<template>
  <v-container class="grey lighten-5">
    <v-row>
      <v-col colos="8">
        <v-card outlined>
          <v-card-title>Create Lunch Recipes</v-card-title>
          <v-card-text>
            <v-text-field
              v-model="lunch.name"
              label="Name"
              outlined
            ></v-text-field>

            <v-autocomplete
              v-model="lunch.foods"
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

            <v-text-field
              v-for="(item, index) in lunch.foods"
              :item="item"
              :index="index"
              :key="item.id"
              v-model="grams[index]"
              label="Grame"
              outlined
            ></v-text-field>

            <v-text-field
              v-model="lunch.info"
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
              <v-card-title> {{ lunch.name }} </v-card-title>

              <v-row>
                <v-card-subtitle>
                  Ingredients:{{ lunch.foods }}
                </v-card-subtitle>
              </v-row>
              <v-row>
                <v-card-subtitle> Info:{{ lunch.info }} </v-card-subtitle>
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
      grams: [],
      lunch: {
        name: "",
        foods: [],
        calories: 0,
        proteins: 0.0,
        lipid: 0.0,
        carbohydrates: 0.0,
        info: "",
      },
      selectedFood: null,
      i:0,
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
      
      for (var prop in this.lunch.foods) {
        if (this.lunch.foods.hasOwnProperty(prop)) {
          this.lunch.calories+=(this.grams[this.i]*this.lunch.foods[prop].calories)/100;
          this.lunch.proteins+=(this.grams[this.i]*this.lunch.foods[prop].proteins)/100;
          this.lunch.carbohydrates+=(this.grams[this.i]*this.lunch.foods[prop].carbohydrates)/100;
          this.lunch.lipid+=(this.grams[this.i]*this.lunch.foods[prop].lipid)/100;
          this.i=this.i+1;
        }
        
      }
      
      
    },

    createFood() {
      this.getNutrients();
      console.log(this.lunch.calories+" "+this.lunch.proteins+" "+this.lunch.carbohydrates+" "+this.lunch.lipid);
      axios
        .post("http://localhost:8080/api/lunch", this.lunch)
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