
<template>
  <v-container v-if="isAuthenticated" class="grey lighten-5">
    <h1>INGREDIENTS</h1>
    <v-row>
      <v-col cols="8">
        <v-row>
          <v-col cols="12" lg="4" md="6" xs="12" v-for="f in food" :key="f.id">
            <v-card class="mx-auto" @click="selectFood(f)">
              <v-card-title> {{ f.name }} </v-card-title>

              <v-card-subtitle>
                Calories:{{ f.calories }} Carbohydrates:{{
                  f.carbohydrates
                }}
                Proteins:{{ f.proteins }} Lipid:{{ f.lipid }}
              </v-card-subtitle>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn icon @click.stop="deleteFood(f)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col colos="4">
        <v-card outlined v-if="selectedFood !== null">
          <v-card-title>Update food</v-card-title>
          <v-card-text>
            <v-text-field
              v-model="selectedFood.name"
              label="Name"
              outlined
            ></v-text-field>
            <v-text-field
              v-model="selectedFood.calories"
              label="Calories"
              outlined
            ></v-text-field>
            <v-text-field
              v-model="selectedFood.proteins"
              label="Proteins"
              outlined
            ></v-text-field>
            <v-text-field
              v-model="selectedFood.carbohydrates"
              label="Carbohydrates"
              outlined
            ></v-text-field>
            <v-text-field
              v-model="selectedFood.lipid"
              label="Lipid"
              outlined
            ></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn outlined rounded text @click="updateFood()">
              SAVE CHANGES
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <h1>BREAKFASTS</h1>
    <v-row>
      <v-col cols="8">
        <v-row>
          <v-col
            cols="12"
            lg="4"
            md="6"
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

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn icon @click.stop="deleteBreakfast(b)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col colos="4">
        <v-card outlined v-if="selectedBreakfast !== null">
          <v-card-title>Update breakfast</v-card-title>
          <v-card-text>
            <v-text-field
              v-model="selectedBreakfast.name"
              label="Name"
              outlined
            ></v-text-field>
            <v-autocomplete
              v-model="selectedBreakfast.foods"
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
              v-model="selectedBreakfast.info"
              label="Info"
              outlined
            ></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn outlined rounded text @click="updateBreakfast()">
              SAVE CHANGES
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <h1>LUNCHES</h1>
    <v-row>
      <v-col cols="8">
        <v-row>
          <v-col
            cols="12"
            lg="4"
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

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn icon @click.stop="deleteLunch(l)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col colos="4">
        <v-card outlined v-if="selectedLunch !== null">
          <v-card-title>Update lunch</v-card-title>
          <v-card-text>
            <v-text-field
              v-model="selectedLunch.name"
              label="Name"
              outlined
            ></v-text-field>
            <v-autocomplete
              v-model="selectedLunch.foods"
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
              v-model="selectedLunch.info"
              label="Info"
              outlined
            ></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn outlined rounded text @click="updateLunch()">
              SAVE CHANGES
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <h1>DINNERS</h1>
    <v-row>
      <v-col cols="8">
        <v-row>
          <v-col
            cols="12"
            lg="4"
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
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn icon @click.stop="deleteDinner(d)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col colos="4">
        <v-card outlined v-if="selectedDinner !== null">
          <v-card-title>Update dinner</v-card-title>
          <v-card-text>
            <v-text-field
              v-model="selectedDinner.name"
              label="Name"
              outlined
            ></v-text-field>
            <v-autocomplete
              v-model="selectedDinner.foods"
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
              v-model="selectedDinner.info"
              label="Info"
              outlined
            ></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn outlined rounded text @click="updateDinner()">
              SAVE CHANGES
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>

    <h1>MENUS</h1>
    <v-row>
      <v-col cols="8">
        <v-row>
          <v-col cols="12" lg="4" md="6" xs="12" v-for="m in menus" :key="m.id">
            <v-card class="mx-auto" @click="selectMenu(m)">
              <v-card-title> {{ m.name }} </v-card-title>
              <v-card-subtitle>
                <v-row> Breakfast: {{ m.breakfast.name }} </v-row>
                <v-row> Lunch: {{ m.lunch.name }}</v-row>
                <v-row> Dinner: {{ m.dinner.name }}</v-row>
              </v-card-subtitle>

              Calories: {{ m.nutrition.calories }} Proteins:{{
                m.nutrition.proteins
              }}
              Carbohydrates:{{ m.nutrition.carbohydrates }} Lipid:{{
                m.nutrition.lipid
              }}

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn icon @click.stop="deleteMenu(m)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col colos="4">
        <v-card outlined v-if="selectedMenu !== null">
          <v-card-title>Update Menu</v-card-title>
          <v-card-text>
            <v-text-field
              v-model="selectedMenu.name"
              label="Name"
              outlined
            ></v-text-field>
            <v-autocomplete
              v-model="selectedMenu.breakfast"
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
              v-model="selectedMenu.lunch"
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
              v-model="selectedMenu.dinner"
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
            <v-btn outlined rounded text @click="updateMenu()">
              SAVE CHANGES
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
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
      food: [],
      selectedFood: null,
      breakfasts: [],
      selectedBreakfast: null,
      lunches: [],
      selectedLunch: null,
      dinners: [],
      selectedDinner: null,
      menus: [],
      selectedMenu: null,
    };
  },
  created() {
    this.loadFood();
    this.loadBreakfasts();
    this.loadLunches();
    this.loadDinners();
    this.loadMenus();
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
    loadMenus() {
      axios
        .get("http://localhost:8080/api/menu")
        .then((response) => {
          this.menus = response.data;
        })
        .catch((err) => {});
    },
    selectFood(f) {
      this.selectedFood = lodash.cloneDeep(f);
    },
    selectBreakfast(b) {
      this.selectedBreakfast = lodash.cloneDeep(b);
    },
    selectLunch(l) {
      this.selectedLunch = lodash.cloneDeep(l);
    },
    selectDinner(d) {
      this.selectedDinner = lodash.cloneDeep(d);
    },
    selectMenu(m) {
      this.selectedMenu = lodash.cloneDeep(m);
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
    updateBreakfast() {
      axios
        .put(
          `http://localhost:8080/api/breakfast/${this.selectedBreakfast.id}`,
          this.selectedBreakfast
        )
        .then((response) => {
          this.loadBreakfasts();
        });
    },
    updateLunch() {
      axios
        .put(
          `http://localhost:8080/api/lunch/${this.selectedLunch.id}`,
          this.selectedLunch
        )
        .then((response) => {
          this.loadLunches();
        });
    },
    updateDinner() {
      axios
        .put(
          `http://localhost:8080/api/dinner/${this.selectedDinner.id}`,
          this.selectedDinner
        )
        .then((response) => {
          this.loadDinners();
        });
    },
    updateMenu() {
      axios
        .put(
          `http://localhost:8080/api/menu/${this.selectedMenu.id}`,
          this.selectedMenu
        )
        .then((response) => {
          this.loadMenus();
        });
    },
    deleteFood(f) {
      axios
        .delete(`http://localhost:8080/api/food/${f.id}`)
        .then((response) => {
          this.loadFood();
          this.selectedFood = null;
        });
    },
    deleteBreakfast(b) {
      axios
        .delete(`http://localhost:8080/api/breakfast/${b.id}`)
        .then((response) => {
          this.loadBreakfasts();
          this.selectedBreakfast = null;
        });
    },
    deleteLunch(l) {
      axios
        .delete(`http://localhost:8080/api/lunch/${l.id}`)
        .then((response) => {
          this.loadLunches();
          this.selectedLunch = null;
        });
    },
    deleteDinner(d) {
      axios
        .delete(`http://localhost:8080/api/dinner/${d.id}`)
        .then((response) => {
          this.loadDinners();
          this.selectedDinner = null;
        });
    },
    deleteMenu(m) {
       axios
        .put(
          `http://localhost:8080/api/user/deleteUnseenForAll/${this.userState.id}`,
          m
        )
        .then((response) => {
           axios
        .delete(`http://localhost:8080/api/menu/${m.id}`)
        .then((response) => {
          this.loadMenus();
          this.selectedMenu = null;
        });
        });
     
    },
  },
  computed: {
    ...mapGetters(["isAuthenticated","userState"]),
  },
};
</script>

<style>
h1 {
  text-align: left;
}
</style>