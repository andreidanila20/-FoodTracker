<template>
  <v-app>
    <v-app-bar color="success" dense dark app>
      <v-app-bar-nav-icon
        ><v-menu left bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon v-bind="attrs" v-on="on">
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>

          <v-list>
            <v-list-item @click="$router.push({ name: 'ProfileDetails' })">
              <v-list-item-title>Profile</v-list-item-title>
            </v-list-item>
            <v-list-item @click="$router.push({ name: 'ViewMenus' })">
              <v-list-item-title>View your menus</v-list-item-title>
            </v-list-item>
            <v-list-item @click="$router.push({ name: 'ChooseMenu' })">
              <v-list-item-title>Choose a menu for yourself</v-list-item-title>
            </v-list-item>
            <v-list-item @click="$router.push({ name: 'OwnMenu' })">
              <v-list-item-title>Create your own menu</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu></v-app-bar-nav-icon
      >

      <v-toolbar-title>Food Tracker</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon @click="home()">
        <v-icon>mdi-home</v-icon>
      </v-btn>

      <v-menu v-if="userState != null && userState.admin && isAuthenticated">
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on">
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </template>

        <v-list>
          <v-list-item @click="$router.push({ name: 'AddMenu' })">
            <v-list-item-title>Add new menu</v-list-item-title>
          </v-list-item>
          <v-list-item @click="$router.push({ name: 'AddFoodAdmin' })">
            <v-list-item-title>Add new food</v-list-item-title>
          </v-list-item>
          <v-list-item @click="$router.push({ name: 'AddBreakfast' })">
            <v-list-item-title>Add new breakfast</v-list-item-title>
          </v-list-item>
          <v-list-item @click="$router.push({ name: 'AddLunch' })">
            <v-list-item-title>Add new lunch</v-list-item-title>
          </v-list-item>
          <v-list-item @click="$router.push({ name: 'AddDinner' })">
            <v-list-item-title>Add new dinner</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>

      <v-menu v-if="isAuthenticated">
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on">
            <v-badge bottom transition="slide-x-transition">
              <span slot="badge">{{ unseenMenus.length }}</span>
              <v-icon>mdi-bell</v-icon>
            </v-badge>
          </v-btn>
        </template>
        <v-card class="mx-auto" max-width="430">
          <v-virtual-scroll
            :items="unseenMenus"
            :item-height="300"
            height="300"
            width="410"
          >
            <template v-slot:default="{ item }">
              <v-card
                max-width="375"
                class="mx-auto"
                @click="pullOffUnseenMenus(item)"
              >
                <v-list>
                  <v-list-item>
                    <v-list-item-icon>
                      <v-icon color="indigo"> mdi-food </v-icon>
                    </v-list-item-icon>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>{{ item.name }}</v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                  <v-divider></v-divider>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>{{
                        item.breakfast.name
                      }}</v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>{{
                        item.lunch.name
                      }}</v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>{{
                        item.dinner.name
                      }}</v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                </v-list>
                <v-row justify="center">
                  <v-dialog v-model="dialog" persistent max-width="500">
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                        color="primary"
                        dark
                        v-bind="attrs"
                        v-on="on"
                        @click.stop="menuDet = item"
                      >
                        Details
                      </v-btn>
                    </template>
                    <v-card class="mx-auto">
                      <v-card-title v-if="item != null">
                        {{ item.name }}
                      </v-card-title>
                      <v-card-title v-else> No Menu </v-card-title>
                      <v-card-text v-if="item != null">
                        <v-row> Breakfast:{{ item.breakfast.name }} </v-row>
                        <v-row> Breakfast info:{{ item.breakfast.info }}</v-row>
                        <v-row> Lunch:{{ item.lunch.name }} </v-row>
                        <v-row> Lunch info:{{ item.lunch.info }} </v-row>
                        <v-row> Dinner:{{ item.dinner.name }} </v-row>
                        <v-row> Dinner info:{{ item.dinner.info }} </v-row>
                      </v-card-text>

                      <v-card-text v-else>
                        <v-row> We can't find a Menu for you </v-row>
                      </v-card-text>

                      <v-card-subtitle v-if="item != null">
                        <v-row> Calories: {{ item.nutrition.calories }} </v-row>
                        <v-row> Proteins:{{ item.nutrition.proteins }} </v-row>
                        <v-row> Lipid:{{ item.nutrition.lipid }} </v-row>
                        <v-row>
                          Carbohydrates:{{ item.nutrition.carbohydrates }}
                        </v-row>
                      </v-card-subtitle>
                      <v-card-subtitle v-else>
                        <v-row> Sorry for that! </v-row>
                      </v-card-subtitle>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="green darken-1"
                          text
                          @click="putInMyList(item)"
                        >
                          Put into my list
                        </v-btn>
                        <v-btn
                          color="green darken-1"
                          text
                          @click="closeDialog(item)"
                        >
                          Close
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-row>
              </v-card>
            </template>
          </v-virtual-scroll>
        </v-card>
        
      </v-menu>

      <v-btn
        v-if="isAuthenticated == false"
        icon
        @click="$router.push({ name: 'Login' })"
      >
        <v-icon>mdi-login</v-icon>
      </v-btn>

      <v-btn
        v-if="isAuthenticated"
        icon
        @click="
          $store.dispatch('logout');
          $router.push({ name: 'Login' });
        "
      >
        <v-icon>mdi-logout</v-icon>
      </v-btn>
    </v-app-bar>
    <div v-if="aler" class="my-10">
      
      <v-alert type="info">
        {{info}}
      </v-alert>
    </div>

    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>

<script>
import { mapGetters } from "vuex";
import axios from "axios";
var ws;



export default {
  name: "App",
  data: () => ({
    userInfo: null,
    dialog: false,
    menuDet: null,
    aler:false,
    info:"",
  }),
  destroyed(){
    console.log("App destroyed");
  },
  created() {
	
  
	ws = new WebSocket('ws://localhost:8080/loginWS');
  ws.onopen = function(data) {
		console.log("WEB SOCKET CONNECTED");
    
}

  const self=this;
	ws.onmessage = function(data) {
    console.log("MSG");
    self.info=data.data;
    self.aler=true;
     setTimeout(() => (self.aler = false), 4000)
}
	ws.onerror = function(data) {
    console.log("ERROR");
		console.log(data.data);
}
	ws.onclose = function(data) {
    console.log("CLOSE");
		console.log(data.data);
}

  },
  
  computed: {
    ...mapGetters(["isAuthenticated", "userState", "unseenMenus"]),
  },
  methods: {
    modifyData(d){
      this.aler = !this.aler;
      this.info=d;
    }, 
    refreshUnseenMenus() {
      axios
        .get(`http://localhost:8080/api/user/unseenMenus/${this.userState.id}`)
        .then((response) => {
          this.$store.dispatch("getUnseenMenus", response.data);
        })
        .catch((err) => {
          console.log("Not good2");
        });
    },
    pullOffUnseenMenus(m) {
      axios
        .put(
          `http://localhost:8080/api/user/cleanUnseen/${this.userState.id}`,
          m
        )
        .then(() => {
          this.refreshUnseenMenus();
        })
        .catch((err) => {
          console.log("Not good");
        });
    },
    closeDialog(m){
      this.dialog=false;
      this.pullOffUnseenMenus(m);

    },
    putInMyList(m){
      this.dialog=false;
      axios
        .put(`http://localhost:8080/api/user/addMenu/${this.userState.id}`, m)
        .then((response) => {
        
          this.$router.push({name:'ViewMenus'});
          
        }).catch((err) => {
          console.log("Not good2");
        });
      this.pullOffUnseenMenus(m);

    },
    home() {
      if (this.userState.admin) {
        this.$router.push({ name: "FoodAdmin" });
      } else {
        this.$router.push({ name: "Home" });
      }
    },
  },
};
</script>
