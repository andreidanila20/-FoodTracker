<template>
  <v-container v-if="isAuthenticated" class="grey lighten-5">
    <h1>MENUS</h1>
    <div class="my-6">
        
        </div>
    <v-row justify="center" v-if="!userState.admin && !subscribe">
          <v-btn
            color="success"
            dark
            @click="subscribeUser()"
          >
            Subscribe
          </v-btn>
    </v-row>
    <v-row justify="center" v-if="!userState.admin && subscribe">
          <v-btn
            color="success"
            dark
            @click="unSubscribeUser()"
          >
            Unsubscribe
          </v-btn>
    </v-row>
    <div class="my-6">
        
        </div>
    <v-row justify="center">
      <v-dialog v-model="dialog" persistent max-width="500">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="success"
            dark
            v-bind="attrs"
            v-on="on"
            @click="getRandomMenu()"
          >
            Let us to choose for you
          </v-btn>
        </template>
        <v-card class="mx-auto">
        <v-card-title v-if="randomMenu!=null"> {{ randomMenu.name }} </v-card-title>
        <v-card-title v-else> No Menu </v-card-title>
          <v-card-text v-if="randomMenu!=null">
            <v-card-text>
        
         
  
        <div class="my-4 subtitle-1">
          • Breakfast: {{ randomMenu.breakfast.name }}
        </div>
  
        <div>Breakfast info: {{ randomMenu.breakfast.info }}</div>
      </v-card-text>
      <v-divider class="mx-4"></v-divider>
      <v-card-text>
        
         
  
        <div class="my-4 subtitle-1">
          • Lunch: {{ randomMenu.lunch.name }} 
        </div>
  
        <div>Lunch info: {{ randomMenu.lunch.info }}</div>
      </v-card-text>
      <v-card-text>
      <v-divider class="mx-4"></v-divider>
       <div class="my-4 subtitle-1">
          • Dinner: {{ randomMenu.dinner.name }} 
        </div>
  
        <div>Dinner info: {{ randomMenu.dinner.info }} </div>
      </v-card-text>
      <v-divider class="mx-4"></v-divider>
          </v-card-text>

          <v-card-text v-else>
            <v-row> We can't find a Menu for you </v-row>
          </v-card-text>

          <v-card-subtitle v-if="randomMenu!=null">
            <v-row> Calories: {{ randomMenu.nutrition.calories }} </v-row>
            <v-row> Proteins:{{ randomMenu.nutrition.proteins }} </v-row>
            <v-row> Lipid:{{ randomMenu.nutrition.lipid }} </v-row>
            <v-row> Carbohydrates:{{ randomMenu.nutrition.carbohydrates }} </v-row>
          </v-card-subtitle>
          <v-card-subtitle v-else>
            <v-row> Sorry for that! </v-row>
          </v-card-subtitle>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="randomMenu==null" color="green darken-1" text @click="dialog = false">
              Ok
            </v-btn>
            <v-btn v-if="randomMenu!=null" color="green darken-1" text @click="dialog = false">
              Disagree
            </v-btn>
            <v-btn v-if="randomMenu!=null" color="green darken-1" text @click="agreeMenu(randomMenu)">
              Agree
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
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
        
         
  
        <div class="my-4 subtitle-1">
          • Lunch: {{ m.lunch.name }} 
        </div>
  
        <div>Lunch info: {{ m.lunch.info }}</div>
      </v-card-text>
      <v-card-text>
      <v-divider class="mx-4"></v-divider>
       <div class="my-4 subtitle-1">
          • Dinner: {{ m.dinner.name }} 
        </div>
  
        <div>Dinner info: {{ m.dinner.info }} </div>
      </v-card-text>
      <v-divider class="mx-4"></v-divider>
          
          

          <v-card-subtitle>
            <v-row
              v-if="
                m.nutrition.calories < (userInfo.nutrition.calories-userInfo.nutrition.calories*0.05) ||
                m.nutrition.calories >
                  (userInfo.nutrition.calories+userInfo.nutrition.calories*0.05)
              "
            >
              <span class="red--text">
                Calories: {{ m.nutrition.calories }}
              </span></v-row
            >
            <v-row v-else> Calories: {{ m.nutrition.calories }} </v-row>
            <v-row v-if="(m.nutrition.proteins < userInfo.nutrition.proteins) || (m.nutrition.proteins > (userInfo.nutrition.calories*0.2)/4)">
              <span class="red--text">Proteins:{{ m.nutrition.proteins }}</span>
            </v-row>
            <v-row v-else> Proteins:{{ m.nutrition.proteins }} </v-row>
            <v-row
              v-if="
                m.nutrition.lipid < userInfo.nutrition.lipid ||
                m.nutrition.lipid >
                 (userInfo.nutrition.calories*0.3)/9
              "
            >
              <span class="red--text">Lipid:{{ m.nutrition.lipid }}</span>
            </v-row>
            <v-row v-else> Lipid:{{ m.nutrition.lipid }} </v-row>
            <v-row
              v-if="
                m.nutrition.carbohydrates < userInfo.nutrition.carbohydrates ||
                m.nutrition.carbohydrates >
                  (userInfo.nutrition.calories*0.6)/4
              "
              ><span class="red--text">
                Carbohydrates:{{ m.nutrition.carbohydrates }}
              </span></v-row
            >
            <v-row v-else>
              Carbohydrates:{{ m.nutrition.carbohydrates }}
            </v-row>
          </v-card-subtitle>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn @click.stop="addMenuToUser(m)" rounded color="success" dark>
              Add to your list
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
      randomMenu: null,
      userInfo:null,
      dialog: false,
      subscribe:true,
    };
  },
  created() {
    this.loadMenus();
    this.loadUser();
    this.getSubscribe();
  },
  methods: {
    loadMenus() {
      axios
        .get("http://localhost:8080/api/menu")
        .then((response) => {
          this.menus = response.data;
        })
        .catch((err) => {});
    },
    loadUser() {
      axios
        .get(`http://localhost:8080/api/user/${this.userState.id}`)
        .then((response) => {
          this.userInfo = response.data;
        })
        .catch((err) => {});
    },
    getRandomMenu() {
      axios
        .post(
          `http://localhost:8080/api/user/randomMenu/${this.userState.id}`,
          this.menus
        )
        .then((response) => {
          this.randomMenu = response.data;
        })
        .catch((err) => {});
    },
    addMenuToUser(m) {
      axios
        .put(`http://localhost:8080/api/user/addMenu/${this.userState.id}`, m)
        .then((response) => {
          this.loadMenus();
          this.$router.push({name:'ViewMenus'});
        });
    },
    agreeMenu(m){
      this.dialog = false;
      this.addMenuToUser(m);
      
    },
    subscribeUser(){
      this.subscribe= !this.subscribe;
      axios
        .get(`http://localhost:8080/api/user/subscribe/${this.userState.id}`)
        .then(() => {
          console.log("Abonat");
        })
        .catch((err) => { console.log("Caca");});
      
    },
    unSubscribeUser(){
      this.subscribe= !this.subscribe;
      axios
        .get(`http://localhost:8080/api/user/unSubscribe/${this.userState.id}`)
        .then(() => {
          console.log("Dezabonat");
        })
        .catch((err) => { console.log("Caca");});
      
    },
    getSubscribe() {
      axios
        .get(`http://localhost:8080/api/user/getSubscribe/${this.userState.id}`)
        .then((response) => {
          this.subscribe = response.data;
        })
        .catch((err) => {});
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