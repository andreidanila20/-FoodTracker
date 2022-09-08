
<template>
  <v-container v-if="isAuthenticated" class="grey lighten-5">
    <v-row><h1>Choose what you like</h1></v-row>
    
            <v-row justify="center"><v-text-field v-model="menu.name" label="Menu name" outlined></v-text-field></v-row>
    
    <h1>BREAKFASTS</h1>
    <v-col>
    <v-row>
    
          <v-col
          cols="8"
            lg="3"
            md="8"
            xs="12"
            v-for="b in breakfasts"
            :key="b.id"
          >
            <v-card class="mx-auto" @click="selectBreakfast(b)">
              <v-card-title> {{ b.name }} </v-card-title>

              <v-row> <v-card-subtitle> Ingredients: </v-card-subtitle></v-row>

              <v-card-subtitle>
                <v-row v-for="f in b.foods" :key="f.id">
                  {{ f.name }}
                </v-row>
              </v-card-subtitle>
              <v-row>
                <v-card-subtitle> Info:{{ b.info }} </v-card-subtitle></v-row
              >

               Calories: {{ b.calories }} Proteins:{{
                b.proteins
              }}
              Carbohydrates:{{ b.carbohydrates }} Lipid:{{ b.lipid }}

            </v-card>
          </v-col>
        
    </v-row>
    <h1>LUNCHES</h1>
    <v-row>
          <v-col
            cols="8"
            lg="3"
            md="6"
            xs="12"
            v-for="l in lunches"
            :key="l.id"
          >
            <v-card class="mx-auto" @click="selectLunch(l)">
              <v-card-title> {{ l.name }} </v-card-title>

              <v-row> <v-card-subtitle> Ingredients: </v-card-subtitle></v-row>
             
                <v-card-subtitle >
                   <v-row v-for="f in l.foods" :key="f.id">
                  {{ f.name }}
                  </v-row
              >
                </v-card-subtitle>
              <v-row>
                <v-card-subtitle> Info:{{ l.info }} </v-card-subtitle></v-row
              >
              Calories: {{ l.calories }} Proteins:{{
                l.proteins
              }}
              Carbohydrates:{{ l.carbohydrates }} Lipid:{{ l.lipid }}
            </v-card>
          </v-col>
    </v-row>
    <h1>DINNERS</h1>
    <v-row>
          <v-col
            cols="8"
            lg="3"
            md="6"
            xs="12"
            v-for="d in dinners"
            :key="d.id"
          >
            <v-card class="mx-auto" @click="selectDinner(d)">
              <v-card-title> {{ d.name }} </v-card-title>

              <v-row> <v-card-subtitle> Ingredients: </v-card-subtitle></v-row>

              <v-card-subtitle>
                <v-row v-for="d in d.foods" :key="d.id">
                  {{ d.name }}
                </v-row>
              </v-card-subtitle>
              <v-row>
                <v-card-subtitle> Info:{{ d.info }} </v-card-subtitle></v-row
              >
              Calories: {{ d.calories }} Proteins:{{
                d.proteins
              }}
              Carbohydrates:{{ d.carbohydrates }} Lipid:{{ d.lipid }}
            </v-card>
          </v-col>
    </v-row>
    </v-col>
    <v-row><v-col>
      <v-card>
        <v-card-text>
          <v-card-title>Your choose</v-card-title>
      <v-text-field v-if="selectedBreakfast!=null" v-model="selectedBreakfast.name" label="Breakfast" outlined readonly></v-text-field>
          <v-text-field v-if="selectedLunch!=null" v-model="selectedLunch.name" label="Lunch" outlined readonly></v-text-field>
          <v-text-field v-if="selectedDinner!=null" v-model="selectedDinner.name" label="Dinner" outlined readonly></v-text-field>
          </v-card-text>
          </v-card>
      </v-col></v-row>
    <v-row>
    <v-col cols="4">
        <v-card>
        <v-card-title>Your choose</v-card-title>
        <v-card-text>
          <v-text-field  v-model="calories" label="Calories" outlined readonly></v-text-field>
          <v-text-field v-model="proteins" label="Proteins" outlined readonly></v-text-field>
          <v-text-field v-model="carbohydrates" label="Carbohydrates" outlined readonly></v-text-field>
          <v-text-field v-model="lipid" label="Lipid" outlined readonly></v-text-field>
        </v-card-text>
      </v-card>
    </v-col>
    <v-col>
        <v-card>
        <v-card-title>Your minimum values for nutrition</v-card-title>
        <v-card-text>
          <v-text-field  v-model="minCalories" label="Calories" outlined readonly></v-text-field>
          <v-text-field v-model="userInfo.nutrition.proteins" label="Proteins" outlined readonly></v-text-field>
          <v-text-field v-model="userInfo.nutrition.carbohydrates" label="Carbohydrates" outlined readonly></v-text-field>
          <v-text-field v-model="userInfo.nutrition.lipid" label="Lipid" outlined readonly></v-text-field>
        </v-card-text>
      </v-card>
    </v-col>
    <v-col>
        <v-card>
        <v-card-title>Your maximum values for nutrition</v-card-title>
        <v-card-text>
          <v-text-field  v-model="maxCalories" label="Calories" outlined readonly></v-text-field>
          <v-text-field v-model="maxProteins" label="Proteins" outlined readonly></v-text-field>
          <v-text-field v-model="maxCarbohydrates" label="Carbohydrates" outlined readonly></v-text-field>
          <v-text-field v-model="maxLipid" label="Lipid" outlined readonly></v-text-field>
        </v-card-text>
      </v-card>
    </v-col>
    </v-row>
    <div class="my-6"></div>
    <v-row justify="center"><v-btn rounded color="success" dark @click="createMenu()">
              Create your menu
            </v-btn></v-row>
    <div class="my-8"></div>
  </v-container>
</template>


<script>
import axios from "axios";
import lodash from "lodash";
import { mapGetters } from "vuex";

export default {
  name: "Home",
  data() {
    return {
      userInfo:null,
      breakfasts: [],
      selectedBreakfast: null,
      lunches: [],
      selectedLunch: null,
      dinners: [],
      selectedDinner: null,
      menu:  {
        name: "",
        breakfast: null,
        lunch: null,
        dinner: null,
      },
      calories:0,
      proteins:0,
      carbohydrates:0,
      lipid:0,
      maxCalories:0,
      minCalories:0,
      maxProteins:0,
      maxCarbohydrates:0,
      maxLipid:0,
    };
  },
  created() {
    this.loadUser();
    this.loadBreakfasts();
    this.loadLunches();
    this.loadDinners();
    
  },
  methods: {
    loadUser() {
      axios
        .get(`http://localhost:8080/api/user/${this.userState.id}`)
        .then((response) => {
          this.userInfo = response.data;
          this.minCalories=this.userInfo.nutrition.calories-this.userInfo.nutrition.calories*0.05;
          this.maxCalories=this.userInfo.nutrition.calories+this.userInfo.nutrition.calories*0.05;
          this.maxProteins=(this.userInfo.nutrition.calories*0.2)/4;
          this.maxCarbohydrates=(this.userInfo.nutrition.calories*0.6)/4;
          this.maxLipid=(this.userInfo.nutrition.calories*0.3)/9;
        })
        .catch((err) => {});
    },
    loadBreakfasts() {
      axios
        .get("http://localhost:8080/api/breakfast")
        .then((response) => {
          this.breakfasts = response.data;
        })
        .catch((err) => {});
    },
    loadLunches() {
      axios
        .get("http://localhost:8080/api/lunch")
        .then((response) => {
          this.lunches = response.data;
        })
        .catch((err) => {});
    },
    loadDinners() {
      axios
        .get("http://localhost:8080/api/dinner")
        .then((response) => {
          this.dinners = response.data;
        })
        .catch((err) => {});
    },
    selectBreakfast(b) {
        if(this.selectedBreakfast!=null)
        {
            this.subNutrients(this.selectedBreakfast.calories,this.selectedBreakfast.carbohydrates,this.selectedBreakfast.proteins,this.selectedBreakfast.lipid);
        }
        this.selectedBreakfast = lodash.cloneDeep(b);
        
        this.addNutrients(this.selectedBreakfast.calories,this.selectedBreakfast.carbohydrates,this.selectedBreakfast.proteins,this.selectedBreakfast.lipid);
      
    },
    selectLunch(l) {
        if(this.selectedLunch!=null)
        {
            this.subNutrients(this.selectedLunch.calories,this.selectedLunch.carbohydrates,this.selectedLunch.proteins,this.selectedLunch.lipid);
        }
      this.selectedLunch = lodash.cloneDeep(l);
      this.addNutrients(this.selectedLunch.calories,this.selectedLunch.carbohydrates,this.selectedLunch.proteins,this.selectedLunch.lipid);
    },
    selectDinner(d) {
        if(this.selectedDinner!=null)
        {
            this.subNutrients(this.selectedDinner.calories,this.selectedDinner.carbohydrates,this.selectedDinner.proteins,this.selectedDinner.lipid);
        }
      this.selectedDinner = lodash.cloneDeep(d);
      
      this.addNutrients(this.selectedDinner.calories,this.selectedDinner.carbohydrates,this.selectedDinner.proteins,this.selectedDinner.lipid);
    },
    addNutrients(cal,carb,prot,l){
        this.calories+=cal;
        this.carbohydrates+=carb;
        this.proteins+=prot;
        this.lipid+=l;
    },
    subNutrients(cal,carb,prot,l){
        this.calories-=cal;
        this.carbohydrates-=carb;
        this.proteins-=prot;
        this.lipid-=l;
    },
    addMenuToUser() {
      axios
        .put(`http://localhost:8080/api/user/addMenu/${this.userState.id}`, this.menu)
        .then((response) => {
          
        }).catch((err) => {console.log(this.menu.breakfast);
                        console.log(this.menu.lunch);
                        console.log(this.menu.dinner);});;
    },
    createMenu() {
      this.menu.breakfast=this.selectedBreakfast;
      this.menu.lunch=this.selectedLunch;
      this.menu.dinner=this.selectedDinner;
      axios.post("http://localhost:8080/api/menu", this.menu).then((response) => {
        this.menu.id=response.data;
        this.addMenuToUser();
        this.$router.push({ name: "ChooseMenu" });
        
      }).catch((err) => {console.log(this.menu.breakfast);
                        console.log(this.menu.lunch);
                        console.log(this.menu.dinner);});
      
      
    },

    
  },
  computed: {
    ...mapGetters(["isAuthenticated","userState"]),
  },
};
</script>