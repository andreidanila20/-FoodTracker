<template>
<v-row>
    <v-col colos="4">
    
      <v-card>
        <v-card-title>Details</v-card-title>
        <v-card-text>
          <v-text-field v-model="userDetails.name" label="Name" outlined readonly></v-text-field>
          <v-text-field v-model="userDetails.email" label="Email" outlined readonly></v-text-field>
          <v-text-field v-model="userDetails.age" label="Age" outlined readonly></v-text-field>
          <v-text-field v-model="userDetails.height" label="Height" outlined readonly></v-text-field>
          <v-text-field v-model="userDetails.pounds" label="Pounds" outlined readonly></v-text-field>
          <v-text-field v-if="userDetails.nutrition!=null" v-model="userDetails.nutrition.calories" label="Calories per day" outlined readonly></v-text-field>
          <v-text-field v-if="userDetails.nutrition!=null" v-model="userDetails.nutrition.proteins" label="Minimum proteins per day" outlined readonly></v-text-field>
          <v-text-field v-if="userDetails.nutrition!=null" v-model="userDetails.nutrition.carbohydrates" label="Minimum carbohydrates per day" outlined readonly></v-text-field>
          <v-text-field v-if="userDetails.nutrition!=null" v-model="userDetails.nutrition.lipid" label="Minimum lipid per day" outlined readonly></v-text-field>
        </v-card-text>
      </v-card>
    </v-col>
    <v-col colos="4">
    
      <v-card>
        <v-card-title>Update profile</v-card-title>
        <v-card-text>
          <v-text-field v-model="userDetails.name" label="Name" outlined></v-text-field>
          <v-text-field v-model="userDetails.email" label="Email" outlined></v-text-field>
          <v-text-field v-model="userDetails.age" label="Age" outlined></v-text-field>
          <v-text-field v-model="userDetails.height" label="Height" outlined></v-text-field>
          <v-text-field v-model="userDetails.pounds" label="Pounds" outlined ></v-text-field>
          <v-select
                          v-model="userDetails.activity"
                          :items="activityType"
                          label="Activity Level"
                          dense
                          solo
                          required
                        ></v-select>
        </v-card-text>
        <v-card-actions>
          <v-btn outlined rounded text @click="updateProfile()"> SAVE CHANGES </v-btn>
        </v-card-actions>
      </v-card>

    </v-col>
</v-row>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  name: "Home",
  data() {
    return {
      userDetails: null,
      activityType:["Sedentary","Lightly active","Moderately active","Very active","Extra active"],
      activity:"",
    };
  },
  created() {
    this.loadUser();
  },
  methods: {
    loadUser(){
        
        axios.get(`http://localhost:8080/api/user/${this.userState.id}`).then((response) => {
          this.userDetails = response.data;
        })
        .catch((err) => {});
    },
    updateProfile() {
      this.userDetails.menu=[];
      axios
        .put(
          `http://localhost:8080/api/user/${this.userDetails.id}`,
          this.userDetails
        )
        .then((response) => {
            this.loadUser();
        });
    },
    
  },
  computed: {
    ...mapGetters(["isAuthenticated","userState"]),
  },
};
</script>