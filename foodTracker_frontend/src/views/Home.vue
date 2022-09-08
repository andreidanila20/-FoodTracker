<template>
<div id="app">
  <v-app id="inspire">
    <v-parallax  height="1100" src="https://online.ahu.edu/wp-content/uploads/2019/03/Healthy-Food-Doesnt-Have-To-Be-Expensive.jpg">
    <div id="scissors" style="font-size:80px; color:gray;"   >
    A good food is a food that gives you all the necessary nutrients
    </div>
    <div class="my-2">
              <v-btn
                x-large
                color="success"
                dark
                @click="$router.push({ name: 'ChooseMenu' })"
              >
                Choose for yourself
              </v-btn>
            </div>
    </v-parallax>
    
  </v-app>
</div>



</template>

<script>
import axios from "axios"
import lodash from "lodash"
import { mapGetters } from 'vuex'

export default {
  name: "Home",
  data() {
    return {
      food: [],
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
    selectFood(f) {
      this.selectedFood = lodash.cloneDeep(f);
    },
    updateFood() {
      axios
        .put(
          `http://localhost:8080/api/food/${this.selectedFood.id}`,
          this.selectedFood
        )
        .then((response) => {
          this.loadFood();
        });
    },
    deleteFood(f){
      axios
        .delete(
          `http://localhost:8080/api/food/${f.id}`)
        .then((response) => {
          this.loadFood();
          this.selectedFood=null;
        });
    }
  },
  computed: {
   
    ...mapGetters(["isAuthenticated",
     
    ])
  }
};
</script>
