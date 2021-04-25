<template>
  <v-data-table
    :headers="headers"
    :items="infoDetails"
    item-key="detail"
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>History</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>

        <!-- Add Reservation -->
        <v-dialog v-model="dialogAddReserve" max-width="500px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
              Add
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">Comming Soon!</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="12" md="6">
                    <v-text-field
                      readonly
                      v-model="editedItem.firstName"
                      label="firstname*"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="12" md="6">
                    <v-text-field
                      readonly
                      v-model="editedItem.time"
                      label="time*"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close"> Cancel </v-btn>
              <v-btn color="blue darken-1" text @click="save"> Save </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>

    <!-- Contents -->
    <template v-slot:body="{ items }">
      <tbody>
        <tr v-for="(item, i) in items" :key="i">
          <td>{{ item.time }}</td>
          <td>{{ nameUser(item) }}</td>

          <!-- More info -->
          <td>
            <template>
              <v-btn color="primary" dark @click="dialogMore = i"> More </v-btn>
            </template>

            <!-- Dialog More -->
            <v-dialog :value="dialogMore == i" persistent max-width="600px">
              <v-card>
                <v-card-title>
                  <span class="headline">More details</span>
                </v-card-title>
                <v-card-text>
                  <v-container>
                    <v-row>
                      <v-col cols="12" sm="6" md="6">
                        <v-text-field
                          label="firstname*"
                          required
                          v-model="item.detail.firstName"
                        ></v-text-field>
                      </v-col>
                      <!-- hint="example of helper text only on focus" -->
                      <v-col cols="12" sm="6" md="6">
                        <v-text-field
                          label="lastname"
                          v-model="item.detail.lastName"
                          required
                        ></v-text-field>
                      </v-col>
                      <!-- ข้อมูลของv-modelต้องตรงกับitem -->
                      <v-col cols="12" sm="6" md="6">
                        <v-text-field
                          label="phone number*"
                          required
                          v-model="item.detail.phoneNumber"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="6">
                        <v-select
                          :items="[1, 2, 3, 4, 5, 6]"
                          label="party size*"
                          required
                          v-model="item.detail.partySize"
                        ></v-select>
                      </v-col>

                      <v-col cols="12" sm="12" md="12">
                        <v-text-field
                          label="your email*"
                          required
                          v-model="item.detail.email"
                        ></v-text-field>
                      </v-col>

                      <v-col cols="12" sm="12" md="12">
                        <v-text-field
                          label="special requests"
                          required
                          v-model="item.detail.specialRequests"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                  </v-container>
                  <small style="color: #9f4c38"
                    >*please, carefully check your details</small
                  >
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <div>
                    <v-btn
                      color="blue darken-1"
                      text
                      @click="dialogMore = null"
                    >
                      Edit
                    </v-btn>
                    <v-btn
                      color="blue darken-1"
                      text
                      @click="dialogMore = null"
                    >
                      Close
                    </v-btn>
                  </div>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </td>

          <td>
            <template>
              <!-- default -->
              <div
                v-if="
                  item.statusComplete == false && item.statusCancel == false
                "
                class="pa-0"
              >
                <v-icon small class="mr-6 pa-0" @click="changeToComplete(item)">
                  mdi-check-bold
                </v-icon>
                <v-icon small @click="changeToCancel(item)" class="pa-0">
                  mdi-close-thick
                </v-icon>
              </div>
              <!-- Check complete -->
              <div
                v-else-if="
                  item.statusComplete == true && item.statusCancel == false
                "
                class="pa-0"
              >
                <p class="mb-0" style="color: green">complete</p>
              </div>
              <!-- Check cancel -->
              <div
                v-else-if="
                  item.statusCancel == true && item.statusComplete == false
                "
                class="pa-0"
              >
                <p class="mb-0" style="color: red">cancel</p>
              </div>
            </template>
          </td>
        </tr>
      </tbody>
    </template>
  </v-data-table>
</template>

<script>
export default {
  data() {
    return {
      dialogMore: null,
      dialogAddReserve: false,
      dialogDelete: false,
      editedIndex: -1,
      editedItem: {
        detail: {
          firstName: "",
          lastName: "",
          partySize: 0,
          tableType: "",
          email: "",
          phoneNumber: "",
          specialRequests: "",
        },
        date: {
          year: 0,
          month: 0,
          day: 0,
        },
        time: "",
        status: "",
      },
      defaultItem: {
        detail: {
          firstName: "",
          lastName: "",
          partySize: 0,
          tableType: "",
          email: "",
          phoneNumber: "",
          specialRequests: "",
        },
        date: {
          year: 0,
          month: 0,
          day: 0,
        },
        time: "",
        status: "",
      },

      headers: [
        { text: "Times", value: "time" },
        {
          text: "Details",
          align: "start",
          sortable: false,
          value: "infoDetails",
        },
        { text: "", value: "more" },
        { text: "Status", value: "actions", sortable: false },
      ],
      infoDetails: [
        {
          detail: {
            firstName: "Patiphon",
            lastName: "Klangpraphan",
            partySize: 2,
            tableType: "small (1-2 persons)",
            email: "chancreamz@gmail.com",
            phoneNumber: "0956600463",
            specialRequests: "Hungry so much!",
          },
          date: {
            year: 2002,
            month: 5,
            day: 28,
          },
          time: "11:00",
          status: "incoming",
          statusComplete: false,
          statusCancel: false,
        },
        {
          detail: {
            firstName: "Jirasin",
            lastName: "Kuaysus",
            partySize: 4,
            tableType: "medium",
            email: "Jirasin@mail.kmutt.ac.th",
            phoneNumber: "0888888888",
            specialRequests: "asdasdadadwdwadad",
          },
          date: {
            year: 2003,
            month: 7,
            day: 12,
          },
          time: "10:00",
          status: "complete",
          statusComplete: false,
          statusCancel: false,
        },
        {
          detail: {
            firstName: "Apisit",
            lastName: "Nhahee",
            partySize: 6,
            tableType: "big",
            email: "Apisit@mail.kmutt.ac.th",
            phoneNumber: "0987654321",
            specialRequests: "dadsdsdsdsadsadsdadss",
          },
          date: {
            year: 2001,
            month: 2,
            day: 9,
          },
          time: "09:00",
          status: "cancel",
          statusComplete: false,
          statusCancel: false,
        },
      ],
    };
  },

  methods: {
    nameUser(item) {
      return item.detail.firstName + " " + item.detail.lastName;
    },
    changeToComplete(item) {
      item.statusComplete = !item.statusComplete;
    },
    changeToCancel(item) {
      item.statusCancel = !item.statusCancel;
    },

    close() {
      this.dialogAddReserve = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.infoDetails[this.editedIndex], this.editedItem);
      } else {
        this.infoDetails.push(this.editedItem);
      }
      this.close();
    },
  },
};
</script>

<style lang="scss" scoped>
// div {
//   width: 259px;
// }
</style>